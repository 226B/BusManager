package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.model.*;

import java.util.Arrays;
import java.util.List;

/**
 * interface for DataHandeling, this Interface needs to be implemented of a new Handler needs to be implemented.
 *
 * This class implements all function for loading Data
 */
public interface IDataHandler {

    /**
     * @return all objects that implement {@link IValidatable}.
     */
    List<IValidatable> getAllObjects();

    /**
     * Simple get for a bus with a name.
     * @param name the name of the bus.
     * @return a {@link Bus}, null if no Bus was found.
     */
    Bus getBus(String name);

    /**
     * @param buses the names of the buses that should be returned.
     * @return a list of buses, empty if none are found.
     */
    default List<Bus> getBuses(String... buses) {
        return getBuses(Arrays.asList(buses));
    }

    /**
     * @param buses the names of the buses that should be returned.
     * @return a list of buses, empty if none are found.
     */
    List<Bus> getBuses(List<String> buses);

    /**
     * @return a list of every single bus.
     */
    List<Bus> getAllBuses();

    /**
     * @return a list of every single depot.
     */
    List<Depot> getAllDepots();

    /**
     * @return a list of every single station.
     */
    List<BusStation> getAllStations();

    /**
     * @param name the name of the type that should be found.
     * @return the {@link BusType}, null of no type with that name exists.
     */
    BusType getBusType(String name);

    /**
     * @return a list of every single {@link BusType}
     */
    List<BusType> getBusTypes();

    /**
     * @param id the ID of the Terminal.
     * @return the {@link Terminal}, null if no terminal with that id exists.
     */
    Terminal getTerminal(Integer id);

    /**
     * @return the next free Terminal ID.
     */
    Integer getNextTerminalId();

    /**
     * @param terminals the list of TerminalID
     * @return a list of Terminals, empty if the ID does not exists.
     */
    default List<Terminal> getTerminals(Integer... terminals) {
        return getTerminals(Arrays.asList(terminals));
    }

    /**
     * @param terminals the list of TerminalID
     * @return a list of Terminals, empty if the ID does not exists.
     */
    List<Terminal> getTerminals(List<Integer> terminals);

    /**
     * @param name the name of the TerminalType.
     * @return the {@link TerminalType}, null if the Type does not exists.
     */
    TerminalType getTerminalType(String name);

    /**
     *
     * @param id
     * @return
     */
    Location getLocation(Integer id);

    Integer getNextLocationId();

    List<Terminal> getAllTerminals();

    List<TerminalType> getAllTerminalTypes();

    BusStation getStationForTerminal(int terminalId);

    Trip getTrip(Integer id);

    Integer getNextTripId();

    List<Trip> getAllTrips();

    default List<Trip> getTrips(Integer... trips) {
        return getTrips(Arrays.asList(trips));
    }

    List<Trip> getTrips(List<Integer> trips);

    Depot getDepot(String name);

    BusStation getStation(String name);

    BusStation getStation(int locationId);

    Terminal getTerminalByTripId(int tripId, int locationId);

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
