package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Trip {
    private Integer id;
    private long startTime;
    private long arrivalTime;
    private String busName;
    private int startId;
    private int endId;

    public Trip(int id, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        this.id = id;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.busName = bus.getName();
        this.startId = start.getId();
        this.endId = end.getId();
    }

    public Bus getBus() {
        return null; //todo
    }
    public Location getStart() {
        return null; //todo
    }
    public Location getEnd() {
        return null; //todo
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) {
            return false;
        }
        Trip o2 = (Trip) obj;

        return this.startTime == o2.startTime
                && this.arrivalTime == o2.arrivalTime
                && this.getBus().equals(o2.getBus())
                && this.getStart().equals(o2.getStart())
                && this.getEnd().equals(o2.getEnd());
    }
}
