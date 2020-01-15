package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TripManager {

    private static TripManager instance;

    public static TripManager getInstance() {
        if (instance == null) {
            instance = new TripManager();
        }

        return instance;
    }

    public Bus getFreeBus(LocalDateTime startTime, BusStation start, BusStation end) {
        return null; //todo implement
    }

    public LocalDateTime getLockedUntil(LocalDateTime startTime, BusStation start, BusStation end, BusType type) {
        return null; //todo
    }

    public LocalDateTime getArrivalTime(LocalDateTime startTime, BusType busType, BusStation start, BusStation end) {
        Location location = start.getLocation();
        Location location1 = end.getLocation();
        double pow = Math.pow(location.getX() - location1.getX(), 2);
        double pow1 = Math.pow(start.getLocation().getY() - end.getLocation().getY(), 2);
        double distance = Math.sqrt(pow + pow1);

        long time = Math.round(distance / busType.getDistancePerH() * 60 * 60 + 0.5);

        return startTime.plusSeconds(time);
    }

    public List<Terminal> getFreeTerminals(BusType type, BusStation station, LocalDateTime time) {
        List<Terminal> result = new ArrayList<>();
        for (Terminal terminal : station.getTerminals()) {
            if (terminal.getType().getCapacity() < type.getCapacity()) {
                break;
            }
            boolean valid = true;
            for (Trip trip : terminal.getTrips()) {
                if (station.getLocation().equals(trip.getStart())) {
                    if (trip.getStartTime() < time.atZone(ZoneId.of("EST")).toInstant().toEpochMilli() + 10 * 60 * 1000
                            && trip.getStartTime() > time.atZone(ZoneId.of("EST")).toInstant().toEpochMilli() - 10 * 60 * 1000) {
                        valid = false;
                        break;
                    }
                } else if (station.getLocation().equals(trip.getEnd())) {
                    if (trip.getArrivalTime() < time.atZone(ZoneId.of("EST")).toInstant().toEpochMilli() + 10 * 60 * 1000
                            && trip.getArrivalTime() > time.atZone(ZoneId.of("EST")).toInstant().toEpochMilli() - 10 * 60 * 1000) {
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
