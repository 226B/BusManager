package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/station/{station}/")
public class BusStationController {
    @GetMapping("/{bus}")
    public Bus getBus(@PathVariable(value = "station") String station, @PathVariable(value = "bus") String bus) {
        return new Bus(bus, new BusType("normal", 10, 10, 1000, 100));
    }
}