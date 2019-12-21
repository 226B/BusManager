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
@RequestMapping("/api/v1/trip/")
@CrossOrigin(origins = {"*"})
public class TripController {
    @GetMapping("get")
    public List<TripDto> getTripList () {
        List<TripDto> dtoList = new ArrayList<TripDto>();
        DataManager.getInstance().getAllTrips().forEach(trip -> {
            dtoList.add(TripDto.toDto(trip));
        });
        return dtoList;
    }
}
