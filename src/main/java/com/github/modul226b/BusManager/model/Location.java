package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Location implements IValidatable {
    private Integer id;
    private Integer x;
    private Integer y;

    public Location(Integer id, Integer x, Integer y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location o2 = (Location) obj;

        return this.x.equals(o2.x) && this.y.equals(o2.y);
    }
}
