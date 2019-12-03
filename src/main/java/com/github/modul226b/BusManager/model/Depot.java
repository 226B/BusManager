package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Depot {
    String name;
    List<Bus> buses;

    public Depot(String depotName) {
        assert depotName != null : "name should not be null.";

        this.depotName = depotName;
        this.buses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Depot)) {
            return false;
        }
        Depot o2 = (Depot) obj;
        return this.depotName.equalsIgnoreCase(o2.depotName) && this.buses.equals(o2.buses);
    }
}