package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.model.*;

import java.util.Arrays;
import java.util.List;

public interface IDataHandler {
    Bus getBus(String name);

    default List<Bus> getBuses(String... buses) {
        return getBuses(Arrays.asList(buses));
    }

    List<Bus> getBuses(List<String> terminals);

    List<Bus> getBuses();

    BusType getBusType(String name);

    List<BusType> getBusTypes();

    Terminal getTerminal(Integer id);

    Integer getNextTerminalId();

    default List<Terminal> getTerminals(Integer... terminals) {
        return getTerminals(Arrays.asList(terminals));
    }

    List<Terminal> getTerminals(List<Integer> terminals);

    TerminalType getTerminalType(String name);

    Location getLocation(Integer id);

    Integer getNextLocationId();

    Trip getTrip(Integer id);

    Integer getNextTripId();

    default List<Trip> getTrips(Integer... trips) {
        return getTrips(Arrays.asList(trips));
    }

    List<Trip> getTrips(List<Integer> trips);

    Depot getDepot(String name);

    BusStation getStation(String name);

    void addBus(Bus bus);

    void addBusType(BusType type);

    void addTerminal(Terminal terminal);

    void addTerminalType(TerminalType type);

    void addLocation(Location location);

    void addTrip(Trip trip);

    void addDepot(Depot depot);

    void addStation(BusStation station);

    void removeBusType(String typeName);

    void removeBus(String busName);
}
