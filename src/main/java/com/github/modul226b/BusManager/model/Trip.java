package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Trip implements IValidatable {
    private Integer id;
    private long startTime;
    private long arrivalTime;
    private String busName;
    private Integer startId;
    private Integer endId;

    public Trip(Integer id, LocalDateTime start, LocalDateTime arrivalTime, Bus bus, BusStation startStation, BusStation endStation) {
        this(
                id,
                TimeHelper.toLong(start),
                TimeHelper.toLong(arrivalTime),
                bus,
                startStation.getLocation(),
                endStation.getLocation()
        );
    }
    public Trip(LocalDateTime start, LocalDateTime arrivalTime, Bus bus, BusStation startStation, BusStation endStation) {
        this(DataManager.getInstance().getNextTripId(), start, arrivalTime, bus, startStation, endStation);
    }

    private Trip(Integer id, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        DataManager instance = DataManager.getInstance();

        assert instance.getBus(bus.getName().toLowerCase()) != null : "bus must be registered.";
        assert instance.getLocation(start.getId()) != null : "start Location must be registered.";
        assert instance.getLocation(end.getId()) != null : "end Location must be registered.";
        assert instance.getStation(start.getId()) != null : "start must be a BusStation";
        assert instance.getStation(end.getId()) != null : "end must be a BusStation";

        assert startTime < arrivalTime : "start must be before end.";

        this.id = id;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.busName = bus.getName();
        this.startId = start.getId();
        this.endId = end.getId();
    }

    private Trip(long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        this(DataManager.getInstance().getNextTripId(), startTime, arrivalTime, bus, start, end);
    }


    public Bus getBus() {
        return DataManager.getInstance().getBus(this.busName.toLowerCase());
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
