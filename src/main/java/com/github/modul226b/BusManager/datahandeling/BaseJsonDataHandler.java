package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<Bus> getBuses() {
        return new ArrayList<>(dataHolder.getBuses().values());
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
        return dataHolder.getDepots().getOrDefault(name.toLowerCase(), null);
    }

    @Override
    public BusStation getStation(String name) {
        return dataHolder.getStations().getOrDefault(name, null);
    }

    @Override
    public void addBus(Bus bus) {
        dataHolder.getBuses().put(bus.getName().toLowerCase(), bus);
    }

    @Override
    public void addBusType(BusType type) {
        dataHolder.getBusTypes().put(type.getName().toLowerCase(), type);
    }

    @Override
    public void addTerminal(Terminal terminal) {
        dataHolder.getTerminals().put(terminal.getId(), terminal);
    }

    @Override
    public void addTerminalType(TerminalType type) {
        dataHolder.getTerminalTypes().put(type.getName().toLowerCase(), type);
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
        dataHolder.getDepots().put(depot.getName().toLowerCase(), depot);
    }

    @Override
    public void addStation(BusStation station) {
        dataHolder.getStations().put(station.getName().toLowerCase(), station);
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
        for (Terminal t: this.getStation(locationId).getTerminals()) {

            if (t.getTripIds().contains(tripId)) {
                return t;
            }
        }
        return null;
    }
}
