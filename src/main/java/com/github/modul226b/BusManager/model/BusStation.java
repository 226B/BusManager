package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusStation {
    private String name;
    private int locationId;
    private String depotName;
    private List<Integer> terminals;

    public BusStation(String name, Location location, Depot depot) {
        assert name != null : "name can not be null.";
        assert location != null : "location can not be null.";
        assert depot != null : "depot can not be null";

        this.name = name;
        this.locationId = location.getId();
        this.depotName = depot.getName();
        this.terminals = new ArrayList<>();
    }

    public BusStation(String name, Depot depot, int x, int y) {
        this(name, new Location(0,x, y), depot);
    } //todo

    public Location getLocation() {
        return null; // todo
    }

    public Depot getDepot() {
        return null; //todo
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BusStation)) {
            return false;
        }
        BusStation o2 = (BusStation) obj;

        return this.name.equalsIgnoreCase(o2.name)
                && this.getLocation().equals(o2.getLocation())
                && this.getDepot().equals(o2.getDepot())
                && this.terminals.equals(o2.terminals);
    }
}
