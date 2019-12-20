package com.github.modul226b.BusManager.controller;


import com.github.modul226b.BusManager.dtos.TripDto;
import com.github.modul226b.BusManager.manager.DataManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trip")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class TripController {
    @GetMapping()
    public List<TripDto> getTripList () {
        List<TripDto> dtoList = new ArrayList<TripDto>();
        DataManager.getInstance().getTrips().forEach(trip -> {
            dtoList.add(new TripDto(trip));
        });
    }
}
