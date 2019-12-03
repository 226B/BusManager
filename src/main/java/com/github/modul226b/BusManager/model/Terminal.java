package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Terminal {
    Integer id;
    String typeName;
    List<Integer> trips;

    public Terminal(int id, TerminalType type) {
        assert type != null : "gatetype can not be null";

        this.id = id;
        this.typeName = type.getName();
        this.trips = new ArrayList<>();
    }

    public TerminalType getType() {
        return null; //todo
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Terminal)) {
            return false;
        }
        Terminal o2 = (Terminal) obj;
        return this.id == o2.id && this.getType().equals(o2.getType()) && this.trips.equals(o2.trips);
    }
}
