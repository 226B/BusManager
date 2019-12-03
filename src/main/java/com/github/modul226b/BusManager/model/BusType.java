package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class BusType {
    private String name;
    private int capacity;
    private int recoveryTime;
    private int maxRange;
    private int distancePerH;

    public BusType(String name, int capacity, int recoveryTime, int maxRange, int distancePerH) {
        assert name != null : "name can not be null";

        this.name = name;
        this.capacity = capacity;
        this.recoveryTime = recoveryTime;
        this.maxRange = maxRange;
        this.distancePerH = distancePerH;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BusType)) {
            return false;
        }
        BusType o2 = (BusType) obj;

        return this.name.equalsIgnoreCase(o2.name)
                && this.capacity == o2.capacity
                && this.recoveryTime == o2.recoveryTime
                && this.maxRange == o2.maxRange
                && this.distancePerH == o2.distancePerH;
    }
}
