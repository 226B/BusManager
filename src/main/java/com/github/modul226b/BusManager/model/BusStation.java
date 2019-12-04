package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusStation {
    private String name;
    private Integer locationId;
    private String depotName;
    private List<Integer> terminalIds;

    public BusStation(String name, Location location, Depot depot) {
        assert name != null : "name can not be null.";
        assert location != null : "location can not be null.";
        assert depot != null : "depot can not be null";

        DataManager instance = DataManager.getInstance();

        assert instance.getLocation(location.getId()) != null : "location must be registered.";
        assert instance.getDepot(depot.getName()) != null : "depot must be registered.";

        this.name = name;
        this.locationId = location.getId();
        this.depotName = depot.getName();
        this.terminalIds = new ArrayList<>();
    }

    public BusStation(String name, Depot depot, Integer x, Integer y) {
        this(name, new Location(DataManager.getInstance().getNextLocationId(), x, y), depot);
    }

    public void addTerminal(int id) {
        DataManager instance = DataManager.getInstance();
        assert instance.getTerminal(id) != null : "terminal must be registered.";

        terminalIds.add(id);
    }

    public Location getLocation() {
        return DataManager.getInstance().getLocation(this.locationId);
    }

    public Depot getDepot() {
        return DataManager.getInstance().getDepot(this.depotName);
    }

    public List<Terminal> getTerminals() {
        return DataManager.getInstance().getTerminals(terminalIds);
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
                && this.terminalIds.equals(o2.terminalIds);
    }
}
