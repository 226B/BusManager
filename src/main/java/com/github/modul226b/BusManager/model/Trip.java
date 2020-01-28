package com.github.modul226b.BusManager.model;

import lombok.Getter;

/**
 * The Class Representing a Trip.
 */
@Getter
public class Trip implements IValidatable {
    private Integer id;
    private long startTime;
    private long arrivalTime;
    private String busName;
    private Integer startId;
    private Integer endId;

    /**
     * @param id the ID, can be null.
     * @param startTime the Start time as a UNIX timestamp.
     * @param arrivalTime the Arrival time as a UNIX timestamp.
     * @param bus the Bus for the Trip.
     * @param start the Location for the Start Station.
     * @param end the Location for the End Station.
     */
    public Trip(Integer id, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        assert startTime < arrivalTime : "start must be before end.";

        this.id = id;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.busName = bus.getName();
        this.startId = start.getId();
        this.endId = end.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) {
            return false;
        }
        Trip o2 = (Trip) obj;

        return this.startTime == o2.startTime
                && this.arrivalTime == o2.arrivalTime
                && this.getBusName().equals(o2.getBusName())
                && this.getStartId().equals(o2.getStartId())
                && this.getEndId().equals(o2.getEndId());
    }
}
