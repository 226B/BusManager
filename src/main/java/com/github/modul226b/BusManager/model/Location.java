package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

@Getter
public class Location {
    private Integer id;
    private Integer x;
    private Integer y;

    public Location(Integer id, Integer x, Integer y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Location(Integer x, Integer y) {
        this(DataManager.getInstance().getNextLocationId(), x, y);
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
