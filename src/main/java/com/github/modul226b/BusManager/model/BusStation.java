package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusStation implements IValidatable {
    private String name;
    private Integer locationId;
    private String depotName;
    private List<Integer> terminalIds;
    private DataManager dataManager;

    public BusStation(DataManager dataManager, String name, Location location, Depot depot) {
        this.dataManager = dataManager;
        assert dataManager != null : "dataManager can not be null";
        assert name != null : "name can not be null.";
        assert location != null : "location can not be null.";
        assert depot != null : "depot can not be null";

        assert dataManager.getDataHandler().getLocation(location.getId()) != null : "location must be registered.";
        assert dataManager.getDataHandler().getDepot(depot.getName()) != null : "depot must be registered.";

        this.name = name;
        this.locationId = location.getId();
        this.depotName = depot.getName();
        this.terminalIds = new ArrayList<>();
    }

    public BusStation(DataManager dataManager, String name, Depot depot, Integer x, Integer y) {
        this(dataManager, name, new Location(dataManager, dataManager.getDataHandler().getNextLocationId(), x, y), depot);
    }

    public void addTerminal(int id) {
        assert dataManager.getDataHandler().getTerminal(id) != null : "terminal must be registered.";

        terminalIds.add(id);
    }

    public Location getLocation() {
        return dataManager.getDataHandler().getLocation(this.locationId);
    }

    public Depot getDepot() {
        return dataManager.getDataHandler().getDepot(this.depotName);
    }

    public List<Terminal> getTerminals() {
        return dataManager.getDataHandler().getTerminals(terminalIds);
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
