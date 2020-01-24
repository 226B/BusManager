package com.github.modul226b.BusManager.controller;


import com.github.modul226b.BusManager.dtos.CreateTripDto;
import com.github.modul226b.BusManager.dtos.TripDto;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trip/")
@CrossOrigin(origins = {"*"})
public class TripController {

    private DataManager manager;

    @Autowired
    public TripController(DataManager manager) {
        this.manager = manager;
    }

    @GetMapping("get")
    public List<TripDto> getTripList() {
        List<TripDto> dtoList = new ArrayList<TripDto>();
        manager.getDataHandler().getAllTrips().forEach(trip -> {
            dtoList.add(TripDto.toDto(trip));
        });
        return dtoList;
    }

    @PostMapping("add")
    public ResponseEntity<TripDto> addTrip(@RequestBody CreateTripDto createTripDto) {

        //todo check if createTripDto is valid

        Trip trip = TripManager.getInstance().addTrip(
                createTripDto.getStartStation(),
                createTripDto.getCapacity(),
                createTripDto.getEndStation(),
                createTripDto.getTime()
        );
        return new ResponseEntity<>(TripDto.toDto(trip), HttpStatus.OK);
    }
}
