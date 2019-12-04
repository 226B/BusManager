package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

@Getter
public class Trip {
    private Integer id;
    private long startTime;
    private long arrivalTime;
    private String busName;
    private Integer startId;
    private Integer endId;

    public Trip(Integer id, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        DataManager instance = DataManager.getInstance();

        assert instance.getBus(bus.getName()) != null : "bus must be registered.";
        assert instance.getLocation(start.getId()) != null : "start Location must be registered.";
        assert instance.getLocation(end.getId()) != null : "end Location must be registered.";


        this.id = id;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.busName = bus.getName();
        this.startId = start.getId();
        this.endId = end.getId();
    }

    public Trip(long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        this(DataManager.getInstance().getNextTripId(), startTime, arrivalTime, bus, start, end);
    }


    public Bus getBus() {
        return DataManager.getInstance().getBus(this.busName);
    }

    public Location getStart() {
        return DataManager.getInstance().getLocation(this.startId);
    }

    public Location getEnd() {
        return DataManager.getInstance().getLocation(this.endId);
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
