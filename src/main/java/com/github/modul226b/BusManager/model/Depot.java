package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A Depot for Buses.
 */
@Getter
public class Depot implements IValidatable {
    private String name;
    private List<String> busNames;

    public Depot(String depotName) {
        assert depotName != null : "name should not be null.";

        this.name = depotName;
        this.busNames = new ArrayList<>();
    }


    public void addBus(String bus) {
        busNames.add(bus);
    }

    public void removeBus(String bus) {
        busNames.remove(bus);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Depot)) {
            return false;
        }
        Depot o2 = (Depot) obj;
        return this.name.equalsIgnoreCase(o2.name) && this.busNames.equals(o2.busNames);
    }
}