package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.manager.FileManager;
import com.github.modul226b.BusManager.model.*;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonDataHandler implements IDataHandler {
    @Getter
    private FileManager fileManager;
    private JsonDataHolder dataHolder;

    public JsonDataHandler(String jsonFileName, boolean load) {
        this.fileManager = new FileManager(jsonFileName);
        if (load) {
            try {
                dataHolder = fileManager.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JsonDataHandler(String jsonFileName) {
        this(jsonFileName, true);
    }

    @Override
    public Bus getBus(String name) {
        return dataHolder.getBuses().getOrDefault(name, null);
    }

    @Override
    public List<Bus> getBuses(List<String> buses) {
        List<Bus> result = new ArrayList<>();
        for (String bus : buses) {
            result.add(dataHolder.getBuses().getOrDefault(bus, null));
        }
        return result;
    }

    @Override
    public BusType getBusType(String name) {
        return dataHolder.getBusTypes().getOrDefault(name, null);
    }

    @Override
    public Terminal getTerminal(Integer id) {
        return dataHolder.getTerminals().getOrDefault(id, null);
    }

    @Override
    public Integer getNextTerminalId() {
        return Collections.max(dataHolder.getTerminals().keySet());
    }

    @Override
    public List<Terminal> getTerminals(List<Integer> terminals) {
        List<Terminal> result = new ArrayList<>();
        for (Integer terminal : terminals) {
            result.add(dataHolder.getTerminals().getOrDefault(terminal, null));
        }
        return result;
    }

    @Override
    public TerminalType getTerminalType(String name) {
        return dataHolder.getTerminalTypes().getOrDefault(name, null);
    }

    @Override
    public Location getLocation(Integer id) {
        return dataHolder.getLocations().getOrDefault(id, null);
    }

    @Override
    public Integer getNextLocationId() {
        return Collections.max(dataHolder.getLocations().keySet());
    }

    @Override
    public Trip getTrip(Integer id) {
        return dataHolder.getTrips().getOrDefault(id, null);
    }

    @Override
    public Integer getNextTripId() {
        return Collections.max(dataHolder.getTrips().keySet());
    }

    @Override
    public List<Trip> getTrips(List<Integer> trips) {
        List<Trip> result = new ArrayList<>();
        for (Integer trip : trips) {
            result.add(dataHolder.getTrips().getOrDefault(trip, null));
        }
        return result;
    }

    @Override
    public Depot getDepot(String name) {
        return dataHolder.getDepots().getOrDefault(name, null);
    }

    @Override
    public BusStation getStation(String name) {
        return dataHolder.getStations().getOrDefault(name, null);
    }
}
