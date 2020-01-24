package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusStation implements IValidatable {
    private String name;
    private Integer locationId;
    private String depotName;
    private List<Integer> terminalIds;

    public BusStation(String name, Location location, Depot depot) {
        assert name != null : "name can not be null.";
        assert location != null : "location can not be null.";
        assert depot != null : "depot can not be null";

        this.name = name;
        this.locationId = location.getId();
        this.depotName = depot.getName();
        this.terminalIds = new ArrayList<>();
    }

    public void addTerminal(int id) {
        terminalIds.add(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BusStation)) {
            return false;
        }
        BusStation o2 = (BusStation) obj;

        return this.name.equalsIgnoreCase(o2.name)
                && this.getLocationId().equals(o2.getLocationId())
                && this.getDepotName().equals(o2.getDepotName())
                && this.terminalIds.equals(o2.terminalIds);
    }
}
