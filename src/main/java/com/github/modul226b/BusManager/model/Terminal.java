package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Terminal implements IValidatable {
    private Integer id;
    private String displayName;
    private String typeName;
    private List<Integer> tripIds;
    private DataManager dataManager;

    public Terminal(DataManager dataManager, Integer id, String displayName, TerminalType type) {
        this.dataManager = dataManager;
        assert type != null : "gatetype can not be null";
        assert displayName != null : "displayName can not be null";
        assert id != null : "ID can not be null";

        assert dataManager.getDataHandler().getTerminalType(type.getName()) != null : "terminal type must be registered";

        this.id = id;
        this.displayName = displayName;
        this.typeName = type.getName();
        this.tripIds = new ArrayList<>();
    }

    public Terminal(DataManager dataManager, String displayName, TerminalType type) {
        this(dataManager, dataManager.getDataHandler().getNextTerminalId(), displayName, type);
    }

    public TerminalType getType() {
        return dataManager.getDataHandler().getTerminalType(typeName);
    }

    public List<Trip> getTrips() {
        return dataManager.getDataHandler().getTrips(tripIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Terminal)) {
            return false;
        }
        Terminal o2 = (Terminal) obj;
        return this.id.equals(o2.id) && this.getType().equals(o2.getType()) && this.tripIds.equals(o2.tripIds);
    }
}
