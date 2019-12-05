package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.controller.daos.BusDao;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bus/")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class BusController {
    @GetMapping("get/")
    public List<BusDao> getAllBuses() {
        List<BusDao> daos = new ArrayList<>();
        for (Bus bus : DataManager.getInstance().getBuses()) {
            daos.add(BusDao.ToDao(bus));
        }

        Comparator<BusDao> comparing = Comparator.comparing(o -> o.getType().getName());
        comparing = comparing.thenComparing(BusDao::getName);

        daos.sort(
                comparing
        );
        return daos;
    }

    @GetMapping("get/{busName}")
    public BusDao getBus(@PathVariable String busName) {
        BusDao bus = BusDao.ToDao(DataManager.getInstance().getBus(busName.toLowerCase()));

        if (bus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus mit dem namen " + busName + " nicht gefunden.");
        }

        return bus;
    }

    @PostMapping("add")
    public void addBus(@RequestBody BusDao bus, @RequestParam(required = false) Boolean override) {
        if (override == null) {
            override = false;
        }

        if (DataManager.getInstance().getBus(bus.getName()) != null && !override) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser Bus existiert bereits, das überschreiben muss explizit angegeben sein.");
        }

        if (DataManager.getInstance().getBusType(bus.getType().getName()) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser BusType existiert nicht.");
        }

        DataManager.getInstance().addBus(new Bus(bus.getName(), bus.getType()));
    }

    @GetMapping("type/get/")
    public List<BusType> getAllBusTypes() {
        return DataManager.getInstance().getBusTypes();
    }

    @GetMapping("type/get/{typeName}")
    public BusType getBusType(@PathVariable String typeName) {
        BusType type = DataManager.getInstance().getBusType(typeName.toLowerCase());

        if (type == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BusType mit dem namen " + typeName + " nicht gefunden.");
        }

        return type;
    }

    @PostMapping("type/add")
    public void addBusType(@RequestBody BusType busType, @RequestParam(required = false) Boolean override) {
        if (override == null) {
            override = false;
        }

        if (DataManager.getInstance().getBusType(busType.getName()) != null && !override) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser BusType existiert bereits, das überschreiben muss explizit angegeben sein.");
        }

        DataManager.getInstance().addBusType(busType);
    }
}
