package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripManager {
    private DataManager dataManager;
    private BusManager busManager;

    public TripManager(DataManager dataManager, BusManager busManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
    }

    public Trip addTrip(String startStation, int capacity, String endStation, LocalDateTime time) throws ResponseStatusException {
        BusStation start = dataManager.getDataHandler().getStation(startStation);
        BusStation end = dataManager.getDataHandler().getStation(endStation);

        if (start == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start station existiert nicht.");
        }
        if (end == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endstation existiert nicht.");
        }

        for (Trip trip : dataManager.getDataHandler().getAllTrips()) {
            if (trip.getStartTime() == TimeHelper.toLong(time)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Es exisitert bereits eine Fahrt zu dieser Zeit.");
            }
        }

        Bus bus = busManager.getFreeBus(time, capacity, start);

        if (bus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freier Bus in "+ start.getName() +" gefunden.");
        }

        BusType busType = dataManager.getDataHandler().getBusType(bus.getTypeName());
        LocalDateTime arrivalTime = this.getArrivalTime(time, busType, start, end);

        if (arrivalTime == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler beim berechnen der Ankunftszeit.");
        }

        Terminal startTerminal = this.getFreeTerminals(busType, start, time).stream().findFirst().orElse(null);

        if (startTerminal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freies Terminal in " + start.getName()  + " gefunden.");
        }

        Terminal endTerminal = this.getFreeTerminals(busType, start, arrivalTime).stream().findFirst().orElse(null);

        if (endTerminal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freies Terminal in " + end.getName()  + " gefunden.");
        }

        Trip trip = new Trip(
                dataManager.getDataHandler().getNextTripId(),
                TimeHelper.toLong(time),
                TimeHelper.toLong(arrivalTime),
                bus,
                dataManager.getDataHandler().getLocation(start.getLocationId()),
                dataManager.getDataHandler().getLocation(end.getLocationId())
        );

        dataManager.getDataHandler().addBus(bus);
        dataManager.getDataHandler().addTrip(trip);
        startTerminal.getTripIds().add(trip.getId());
        endTerminal.getTripIds().add(trip.getId());
        return trip;
    }


    public LocalDateTime getArrivalTime(LocalDateTime startTime, BusType busType, BusStation start, BusStation end) {
        Location startLocation = dataManager.getDataHandler().getLocation(start.getLocationId());
        Location endLocation = dataManager.getDataHandler().getLocation(end.getLocationId());
        double pow = Math.pow(startLocation.getX() - endLocation.getX(), 2);
        double pow1 = Math.pow(startLocation.getY() - endLocation.getY(), 2);
        double distance = Math.sqrt(pow + pow1);

        long time = Math.round(distance / busType.getDistancePerH() * 60 * 60 + 0.5);

        return startTime.plusSeconds(time);
    }

    public List<Terminal> getFreeTerminals(BusType type, BusStation station, LocalDateTime time) {
        List<Terminal> result = new ArrayList<>();
        for (Terminal terminal : dataManager.getDataHandler().getTerminals(station.getTerminalIds())) {
            TerminalType terminalType = dataManager.getDataHandler().getTerminalType(terminal.getTypeName());
            if (terminalType.getCapacity() < type.getCapacity()) {
                break;
            }
            boolean valid = true;
            for (Trip trip : dataManager.getDataHandler().getTrips(terminal.getTripIds())) {
                if (station.getLocationId().equals(trip.getStartId())) {

                    if (trip.getStartTime() < TimeHelper.toLong(time.plusSeconds(type.getRecoveryTime()*60))
                            && trip.getStartTime() > TimeHelper.toLong(time.plusSeconds(type.getRecoveryTime()*60))) {
                        valid = false;
                        break;
                    }
                } else if (station.getLocationId().equals(trip.getEndId())) {
                    if (trip.getArrivalTime() < TimeHelper.toLong(time.plusSeconds(type.getRecoveryTime()*60))
                            && trip.getArrivalTime() > TimeHelper.toLong(time.plusSeconds(type.getRecoveryTime()*60))) {
                        valid = false;
                        break;
                    }
                } else {
                    throw new IllegalArgumentException("station contains terminals that do not contain the station as the start or end location.");
                }
            }
            if (valid) {
                result.add(terminal);
            }
        }
        return result;
    }


}
