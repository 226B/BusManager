package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Depot {
    private String name;
    private List<String> buses;

    public Depot(String depotName) {
        assert depotName != null : "name should not be null.";

        this.name = depotName;
        this.buses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Depot)) {
            return false;
        }
        Depot o2 = (Depot) obj;
        return this.name.equalsIgnoreCase(o2.name) && this.buses.equals(o2.buses);
    }
}