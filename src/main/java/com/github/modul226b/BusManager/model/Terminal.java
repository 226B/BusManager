package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Terminal {
    int id;
    TerminalType type;
    List<Trip> trips;

    public Terminal(int id, TerminalType type) {
        assert type != null : "gatetype can not be null";

        this.id = id;
        this.type = type;
        this.trips = new ArrayList<>();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Terminal)) {
            return false;
        }
        Terminal o2 = (Terminal) obj;
        return this.id == o2.id && this.type.equals(o2.type) && this.trips.equals(o2.trips);
    }
}
