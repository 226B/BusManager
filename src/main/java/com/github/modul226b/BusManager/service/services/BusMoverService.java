package com.github.modul226b.BusManager.service.services;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.Trip;
import com.github.modul226b.BusManager.service.AbstractService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BusMoverService extends AbstractService {

    @Override
    public boolean startOnStartUp() {
        return true;
    }

    @Override
    public int getInterval() {
        return 60;
    }

    @Override
    public void run() {
        HashMap<Bus, List<Trip>> map = new HashMap<>();

        List<Trip> allTrips = DataManager.getInstance().getAllTrips();
        for (Trip trip : allTrips.stream().sorted(Comparator.comparingLong(Trip::getArrivalTime)).collect(Collectors.toList())) {
            if (!map.containsKey(trip.getBus())) {
                map.put(trip.getBus(), new ArrayList<>());
            }
            map.get(trip.getBus()).add(trip);
        }

        map.forEach((bus, trips) -> {
            System.out.println(bus.getName());
            for (Trip trip : trips) {
                System.out.println(trip.getArrivalTime());
            }
        });
    }
}
