package com.github.modul226b.BusManager.controller;


import com.github.modul226b.BusManager.dtos.CreateTripDto;
import com.github.modul226b.BusManager.dtos.CreateTripInformation;
import com.github.modul226b.BusManager.dtos.TripDto;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.BusType;
import com.github.modul226b.BusManager.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trip/")
@CrossOrigin(origins = {"*"})
public class TripController {

    private DataManager dataManager;
    private BusManager busManager;
    private TripManager tripManager;

    @Autowired
    public TripController(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
        this.tripManager = tripManager;
    }

    @GetMapping("get")
    public List<TripDto> getTripList() {
        List<TripDto> dtoList = new ArrayList<TripDto>();
        List<Trip> allTrips = dataManager.getDataHandler().getAllTrips();
        for (Trip trip : allTrips) {
            TripDto e = TripDto.toDto(dataManager, trip);
            dtoList.add(e);
        }
        return dtoList;
    }

    @PostMapping("add")
    public ResponseEntity<TripDto> addTrip(@RequestBody  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) CreateTripDto createTripDto) {

        //todo check if createTripDto is valid

        Trip trip = tripManager.addTrip(
                createTripDto.getStartStation(),
                createTripDto.getCapacity(),
                createTripDto.getEndStation(),
                createTripDto.getTime()
        );
        return new ResponseEntity<>(TripDto.toDto(dataManager, trip), HttpStatus.OK);
    }

    @GetMapping("tripinfo/get")
    public CreateTripInformation getInfo() {
        return new CreateTripInformation(
                dataManager.getDataHandler().getBusTypes().stream().map(BusType::getCapacity).collect(Collectors.toList()),
                dataManager.getDataHandler().getAllStations().stream().map(BusStation::getName).collect(Collectors.toList())
        );
    }
}
