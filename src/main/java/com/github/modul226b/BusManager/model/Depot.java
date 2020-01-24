package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Depot implements IValidatable {
    private String name;
    private List<String> busNames;
    private DataManager dataManager;

    public Depot(DataManager dataManager, String depotName) {
        this.dataManager = dataManager;
        assert depotName != null : "name should not be null.";

        this.name = depotName;
        this.busNames = new ArrayList<>();
    }

    public List<Bus> getBuses() {
        return dataManager.getDataHandler().getBuses(busNames);
    }

    public void addBus(String bus) {
        assert dataManager.getDataHandler().getBus(bus) != null : "bus must be registered.";

        busNames.add(bus);
    }

    public void removeBus(String bus) {
        assert dataManager.getDataHandler().getBus(bus) != null : "bus must be registered.";

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