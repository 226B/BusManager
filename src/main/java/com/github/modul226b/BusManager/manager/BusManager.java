package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.model.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BusManager {

    private static BusManager instance;

    public static BusManager getInstance() {
        if (instance == null) {
            instance = new BusManager();
        }
        return instance;
    }

    public BusStation getStationAtTime(Bus bus, LocalDateTime time) {
        List<Trip> allTrips = DataManager.getInstance().getAllTrips();
        List<Trip> collect = allTrips.stream().filter(trip -> trip.getBus() == bus).sorted(Comparator.comparingLong(Trip::getArrivalTime)).collect(Collectors.toList());

        if (collect.size() == 0) {
            return null;
        }

        for (Trip trip : collect) {
            long startTime = trip.getStartTime();
            long l = TimeHelper.toLong(time);
            if (startTime > l) {
                return DataManager.getInstance().getStation(trip.getStart().getId());
            }
        }
        return DataManager.getInstance().getStation(collect.get(collect.size() - 1).getEnd().getId());
    }

    public Depot getDepotStation(Bus bus) {
        for (Depot depot : DataManager.getInstance().getAllDepots()) {
            List<Bus> buses = depot.getBuses();
            if (buses.contains(bus)) {
                return depot;
            }
        }
        return null;
    }

    public BusStation getLastStation(@NotNull Bus bus) {
        assert bus != null : "bus can not be null";

        List<Trip> collect = DataManager.getInstance().getAllTrips().stream()
                .filter(trip -> trip.getBus() == bus)
                .sorted(Comparator.comparingLong(Trip::getArrivalTime))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            return null;
        }

        return DataManager.getInstance().getStation(collect.get(collect.size() - 1).getEnd().getId());
    }



    public Trip getLastTrip(Bus bus) {
        assert bus != null : "bus can not be null";

        List<Trip> collect = DataManager.getInstance().getAllTrips().stream()
                .filter(trip -> trip.getBus() == bus)
                .sorted(Comparator.comparingLong(Trip::getArrivalTime))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            return null;
        }

        return collect.get(collect.size() - 1);
    }
    public Bus getFreeBus(LocalDateTime startTime, int capacity, BusStation start, BusStation end) {

        List<BusType> types = DataManager.getInstance().getBusTypes().stream()
                .filter(busType -> busType.getCapacity() >= capacity)
                .sorted(Comparator.comparingInt(BusType::getCapacity))
                .collect(Collectors.toList());

        if (types.size() == 0) {
            throw new RuntimeException("there is no BusType for capacity: " + capacity);
        }


        List<Bus> freeBusesAtStation = getBusesWithFinalPositionAtStation(start);
        for (BusType type : types) {
            for (Bus bus : freeBusesAtStation) {
                if (!bus.getType().getName().equals(type.getName())) {
                    continue;
                }

                if (getLastTrip(bus).getStartTime() < TimeHelper.toLong(startTime)) {
                    return bus;
                }
            }
        }

        return null;
    }

    public List<Bus> getBusesWithFinalPositionAtStation(BusStation station) {
        List<Bus> buses = new ArrayList<>();
        for (Bus bus : DataManager.getInstance().getAllBuses()) {
            BusStation s = getLastStation(bus);
            if (station.getName().equals(s.getName())) {
                buses.add(bus);
            }
        }
        return buses;
    }
}
