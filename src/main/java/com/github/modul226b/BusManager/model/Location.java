package com.github.modul226b.BusManager.model;

import lombok.Getter;

/**
 * Class representing a Location e.g. a Station.
 */
@Getter
public class Location implements IValidatable {
    private Integer id;
    private Integer x;
    private Integer y;

    /**
     * @param id can be null, The ID.
     * @param x the x Position.
     * @param y the x Position.
     */
    public Location(Integer id, Integer x, Integer y) {
        assert x != null : "x can not be null.";
        assert y != null : "y can not be null.";

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
