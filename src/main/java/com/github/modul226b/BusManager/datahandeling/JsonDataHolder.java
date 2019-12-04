package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.model.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;

@Getter(AccessLevel.PROTECTED)
public class JsonDataHolder {
    private HashMap<String, BusStation> stations;
    private HashMap<String, Bus> buses;
    private HashMap<String, BusType> busTypes;
    private HashMap<Integer, Terminal> terminals;
    private HashMap<String, TerminalType> terminalTypes;
    private HashMap<String, Depot> depots;
    private HashMap<Integer, Location> locations;
    private HashMap<Integer, Trip> trips;

    public JsonDataHolder() {
        stations = new HashMap<>();
        buses = new HashMap<>();
        busTypes = new HashMap<>();
        terminals = new HashMap<>();
        terminalTypes = new HashMap<>();
        depots = new HashMap<>();
        trips = new HashMap<>();
        locations = new HashMap<>();
    }
}
