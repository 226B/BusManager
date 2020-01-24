package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Trip implements IValidatable {
    private Integer id;
    private long startTime;
    private long arrivalTime;
    private String busName;
    private Integer startId;
    private Integer endId;
    private DataManager dataManager;

    public Trip(DataManager dataManager, Integer id, LocalDateTime start, LocalDateTime arrivalTime, Bus bus, BusStation startStation, BusStation endStation) {
        this(
                dataManager,
                id,
                TimeHelper.toLong(start),
                TimeHelper.toLong(arrivalTime),
                bus,
                startStation.getLocation(),
                endStation.getLocation()
        );
    }
    public Trip(DataManager dataManager, LocalDateTime start, LocalDateTime arrivalTime, Bus bus, BusStation startStation, BusStation endStation) {
        this(dataManager, dataManager.getDataHandler().getNextTripId(), start, arrivalTime, bus, startStation, endStation);
    }

    private Trip(DataManager dataManager, Integer id, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        this.dataManager = dataManager;
        assert bus != null : "bus can not be null";
        assert start != null : "start can not be null";
        assert end != null : "end can not be null";

        assert dataManager.getDataHandler().getBus(bus.getName()) != null : "bus must be registered.";
        assert dataManager.getDataHandler().getLocation(start.getId()) != null : "start Location must be registered.";
        assert dataManager.getDataHandler().getLocation(end.getId()) != null : "end Location must be registered.";
        assert dataManager.getDataHandler().getStation(start.getId()) != null : "start must be a BusStation";
        assert dataManager.getDataHandler().getStation(end.getId()) != null : "end must be a BusStation";

        assert startTime < arrivalTime : "start must be before end.";

        this.id = id;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.busName = bus.getName();
        this.startId = start.getId();
        this.endId = end.getId();
    }

    private Trip(DataManager dataManager, long startTime, long arrivalTime, Bus bus, Location start, Location end) {
        this(dataManager, dataManager.getDataHandler().getNextTripId(), startTime, arrivalTime, bus, start, end);
    }


    public Bus getBus() {
        return dataManager.getDataHandler().getBus(this.busName);
    }

    public Location getStart() {
        return dataManager.getDataHandler().getLocation(this.startId);
    }

    public Location getEnd() {
        return dataManager.getDataHandler().getLocation(this.endId);
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
