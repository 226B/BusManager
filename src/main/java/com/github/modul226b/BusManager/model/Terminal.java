package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Terminal {
    Integer id;
    String typeName;
    List<Integer> tripIds;

    public Terminal(Integer id, TerminalType type) {
        assert type != null : "gatetype can not be null";

        this.id = id;
        this.typeName = type.getName();
        this.tripIds = new ArrayList<>();
    }

    public Terminal(TerminalType type) {
        this(DataManager.getInstance().getNextTerminalId(), type);
    }

    public TerminalType getType() {
        return DataManager.getInstance().getTerminalType(typeName);
    }

    public List<Trip> getTrips() {
        return DataManager.getInstance().getTrips(tripIds);
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
