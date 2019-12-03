package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location o2 = (Location) obj;

        return this.x == o2.x && this.y == o2.y;
    }
}
