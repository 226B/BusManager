package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple Manger for buses.
 */
public class BusManager {
    private DataManager dataManager;

    public BusManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * get the Station for a Bus at a specific time.
     * @param bus the Bus.
     * @param time the time.
     * @return the Station, null if none is found, should never happen.
     */
    public BusStation getStationAtTime(Bus bus, LocalDateTime time) {
        List<Trip> allTrips = dataManager.getDataHandler().getAllTrips();
        List<Trip> collect = allTrips.stream().filter(trip -> trip.getBusName().equals(bus.getName())).sorted(Comparator.comparingLong(Trip::getArrivalTime)).collect(Collectors.toList());

        if (collect.size() == 0) {
            return getCurrentStation(bus);
        }

        for (Trip trip : collect) {
            long startTime = trip.getStartTime();
            long l = TimeHelper.toLong(time);
            if (startTime > l) {
                return dataManager.getDataHandler().getStation(trip.getStartId());
            }
        }
        return dataManager.getDataHandler().getStation(collect.get(collect.size() - 1).getEndId());
    }

    /**
     * gets the current depot for a Bus.
     * @param bus the bus.
     * @return the Depot.
     */
    public Depot getDepotStation(Bus bus) {
        for (Depot depot : dataManager.getDataHandler().getAllDepots()) {
            List<Bus> buses = dataManager.getDataHandler().getBuses(depot.getBusNames());
            if (buses.contains(bus)) {
                return depot;
            }
        }
        return null;
    }

    /**
     * gets the Last Station for a Bus.
     * @param bus the bus.
     * @return the BusStation.
     */
    public BusStation getLastStation(Bus bus) {
        assert bus != null : "bus can not be null";

        List<Trip> collect = dataManager.getDataHandler().getAllTrips().stream()
                .filter(trip -> trip.getBusName().equals(bus.getName()))
                .sorted(Comparator.comparingLong(Trip::getArrivalTime))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            return getCurrentStation(bus);
        }

        return dataManager.getDataHandler().getStation(collect.get(collect.size() - 1).getEndId());
    }

    public BusStation getCurrentStation(Bus bus) {
        for (BusStation station : dataManager.getDataHandler().getAllStations()) {
            for (Bus bus1 : dataManager.getDataHandler()
                    .getBuses(dataManager.getDataHandler().getDepot(station.getDepotName()).getBusNames())) {
                if (bus1.equals(bus)) {
                    return station;
                }
            }
        }
        return null;
    }

    /**
     * gets the last Trip for a Bus.
     * @param bus the bus.
     * @return The Trip, null if the bus had no Trips.
     */
    public Trip getLastTrip(Bus bus) {
        assert bus != null : "bus can not be null";

        List<Trip> collect = dataManager.getDataHandler().getAllTrips().stream()
                .filter(trip -> trip.getBusName().equals(bus.getName()))
                .sorted(Comparator.comparingLong(Trip::getArrivalTime))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            return null;
        }

        return collect.get(collect.size() - 1);
    }

    /**
     * gets a free Bus at a time. in a Station.
     * @param startTime the Start time
     * @param capacity the Capacity for the bus.
     * @param start the Station.
     * @return the Free bus, null if none was found.
     */
    public Bus getFreeBus(LocalDateTime startTime, int capacity, BusStation start) {

        List<BusType> types = dataManager.getDataHandler().getBusTypes().stream()
                .filter(busType -> busType.getCapacity() >= capacity)
                .sorted(Comparator.comparingInt(BusType::getCapacity))
                .collect(Collectors.toList());

        if (types.size() == 0) {
            throw new RuntimeException("there is no BusType for capacity: " + capacity);
        }


        List<Bus> freeBusesAtStation = getBusesWithFinalPositionAtStation(start);
        for (BusType type : types) {
            for (Bus bus : freeBusesAtStation) {
                if (!bus.getTypeName().equals(type.getName())) {
                    continue;
                }

                Trip lastTrip = getLastTrip(bus);

                if (lastTrip == null) {
                    return bus;
                }

                if (lastTrip.getStartTime() < TimeHelper.toLong(startTime)) {
                    return bus;
                }
            }
        }

        return null;
    }

    public List<Bus> getBusesWithFinalPositionAtStation(BusStation station) {
        List<Bus> buses = new ArrayList<>();
        for (Bus bus : dataManager.getDataHandler().getAllBuses()) {
            BusStation s = getLastStation(bus);
            if (station.getName().equals(s.getName())) {
                buses.add(bus);
            }
        }
        return buses;
    }
}
