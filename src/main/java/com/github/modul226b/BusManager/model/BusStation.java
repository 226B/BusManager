package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusStation {
    String name;
    Location location;
    Depot depot;
    List<Gate> gates;

    public BusStation(String name, Location location, Depot depot) {
        assert name != null : "name can not be null.";
        assert location != null : "location can not be null.";
        assert depot != null : "depot can not be null";

        this.name = name;
        this.location = location;
        this.depot = depot;
        this.gates = new ArrayList<>();
    }

    public BusStation(String name, Depot depot, int x, int y) {
        this(name, new Location(x, y), depot);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BusStation)) {
            return false;
        }
        BusStation o2 = (BusStation) obj;

        return this.name.equalsIgnoreCase(o2.name)
                && this.location.equals(o2.location)
                && this.depot.equals(o2.depot)
                && this.gates.equals(o2.gates);
    }
}
