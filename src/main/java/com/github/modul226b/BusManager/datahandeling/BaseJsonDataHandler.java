package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * base class for JSON data storage, this class is the basis for {@link JsonDataHandler} and {@link MockDataHandler}.
 * this class is based on a {@link JsonDataHolder} for managing objects.
 */
public class BaseJsonDataHandler implements IDataHandler {

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private JsonDataHolder dataHolder;

    public BaseJsonDataHandler() {
        dataHolder = new JsonDataHolder();
    }

    @Override
    public List<IValidatable> getAllObjects() {
        List<IValidatable> result = new ArrayList<>();
        result.addAll(dataHolder.getTrips().values());
        result.addAll(dataHolder.getBuses().values());
        result.addAll(dataHolder.getDepots().values());
        result.addAll(dataHolder.getBusTypes().values());
        result.addAll(dataHolder.getStations().values());
        result.addAll(dataHolder.getTerminals().values());
        result.addAll(dataHolder.getLocations().values());
        result.addAll(dataHolder.getTerminalTypes().values());
        return result;
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
    public List<Bus> getAllBuses() {
        return new ArrayList<>(dataHolder.getBuses().values());
    }

    @Override
    public List<Depot> getAllDepots() {
        return new ArrayList<>(dataHolder.getDepots().values());
    }

    @Override
    public List<BusStation> getAllStations() {
        return new ArrayList<>(dataHolder.getStations().values());
    }

    @Override
    public BusType getBusType(String name) {
        return dataHolder.getBusTypes().getOrDefault(name, null);
    }

    @Override
    public List<BusType> getBusTypes() {
        return new ArrayList<>(dataHolder.getBusTypes().values());
    }

    @Override
    public Terminal getTerminal(Integer id) {
        return dataHolder.getTerminals().getOrDefault(id, null);
    }

    @Override
    public Integer getNextTerminalId() {
        return (dataHolder.getTerminals().size() > 0) ? Collections.max(dataHolder.getTerminals().keySet()) + 1 : 0;
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
        return (dataHolder.getLocations().size() > 0) ? Collections.max(dataHolder.getLocations().keySet()) + 1 : 0;
    }

    @Override
    public List<Terminal> getAllTerminals() {
        return new ArrayList<>(dataHolder.getTerminals().values());
    }

    @Override
    public List<TerminalType> getAllTerminalTypes() {
        return new ArrayList<>(dataHolder.getTerminalTypes().values());
    }

    @Override
    public BusStation getStationForTerminal(int terminalId) {
        for (BusStation station : this.getAllStations()) {
            if (station.getTerminalIds().contains(terminalId)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public Trip getTrip(Integer id) {
        return dataHolder.getTrips().getOrDefault(id, null);
    }

    @Override
    public Integer getNextTripId() {
        return (dataHolder.getTrips().size() > 0) ? Collections.max(dataHolder.getTrips().keySet()) + 1 : 0;
    }

    @Override
    public List<Trip> getAllTrips() {
        return new ArrayList<>(dataHolder.getTrips().values());
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

    @Override
    public void addBus(Bus bus) {
        dataHolder.getBuses().put(bus.getName(), bus);
    }

    @Override
    public void addBusType(BusType type) {
        dataHolder.getBusTypes().put(type.getName(), type);
    }

    @Override
    public void addTerminal(Terminal terminal) {
        dataHolder.getTerminals().put(terminal.getId(), terminal);
    }

    @Override
    public void addTerminalType(TerminalType type) {
        dataHolder.getTerminalTypes().put(type.getName(), type);
    }

    @Override
    public void addLocation(Location location) {
        dataHolder.getLocations().put(location.getId(), location);
    }

    @Override
    public void addTrip(Trip trip) {
        dataHolder.getTrips().put(trip.getId(), trip);
    }

    @Override
    public void addDepot(Depot depot) {
        dataHolder.getDepots().put(depot.getName(), depot);
    }

    @Override
    public void addStation(BusStation station) {
        dataHolder.getStations().put(station.getName(), station);
    }

    @Override
    public void removeBusType(String typeName) {
        BusType busType = this.getBusType(typeName);

        assert busType != null : "BusType can not be null.";

        dataHolder.getBusTypes().remove(typeName);
    }

    @Override
    public void removeBus(String busName) {
        Bus bus = this.getBus(busName);

        assert bus != null : "Bus can not be null.";

        dataHolder.getBusTypes().remove(busName);
    }

    @Override
    public BusStation getStation(int locationId) {
        for (BusStation value : dataHolder.getStations().values()) {
            if (value.getLocationId() == locationId) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Terminal getTerminalByTripId(int tripId, int locationId) {
        BusStation station = this.getStation(locationId);

        List<Terminal> terminals =
                dataHolder.getTerminals().values().stream()
                        .filter(terminal -> station.getTerminalIds().contains(terminal.getId()))
                        .collect(Collectors.toList());
        for (Terminal t: terminals) {
            if (t.getTripIds().contains(tripId)) {
                return t;
            }
        }
        return null;
    }
}
