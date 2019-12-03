package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Trip {
    private long startTime;
    private long arrivalTime;
    private Bus bus;
    private Location start;
    private Location end;

    public Trip(long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.bus = bus;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) {
            return false;
        }
        Trip o2 = (Trip) obj;

        return this.startTime == o2.startTime
                && this.arrivalTime == o2.arrivalTime
                && this.bus.equals(o2.bus)
                && this.start.equals(o2.start)
                && this.end.equals(o2.end);
    }
}
