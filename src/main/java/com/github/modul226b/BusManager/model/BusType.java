package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class BusType {
    private String name;
    private Integer capacity;
    private Integer recoveryTime;
    private Integer maxRange;
    private Double distancePerH;

    public BusType(String name, Integer capacity, Integer recoveryTime, Integer maxRange, Double distancePerH) {
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
                && this.capacity.equals(o2.capacity)
                && this.recoveryTime.equals(o2.recoveryTime)
                && this.maxRange.equals(o2.maxRange)
                && this.distancePerH == o2.distancePerH;
    }
}
