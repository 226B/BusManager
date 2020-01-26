package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.dtos.BusDto;
import com.github.modul226b.BusManager.dtos.CreateTripInformation;
import com.github.modul226b.BusManager.dtos.StationList;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.BusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bus/")
@CrossOrigin(origins = {"*"})
public class BusController {

    private DataManager dataManager;
    private BusManager busManager;

    @Autowired
    public BusController(DataManager dataManager, BusManager busManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
    }

    @GetMapping("get/")
    public List<BusDto> getAllBuses() {
        List<BusDto> daos = new ArrayList<>();
        List<Bus> buses = dataManager.getDataHandler().getAllBuses();
        for (Bus bus : buses) {
            daos.add(BusDto.ToDao(dataManager, busManager, bus));
        }

        Comparator<BusDto> comparing = Comparator.comparing(o -> o.getType().getName());
        comparing = comparing.thenComparing(BusDto::getName);

        daos.sort(
                comparing
        );
        return daos;
    }

    @GetMapping("get/{busName}")
    public BusDto getBus(@PathVariable String busName) {
        BusDto bus = BusDto.ToDao(dataManager, busManager,dataManager.getDataHandler().getBus(busName));

        if (bus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus mit dem namen " + busName + " nicht gefunden.");
        }

        return bus;
    }

    @PostMapping("add")
    public void addBus(@RequestBody BusDto bus, @RequestParam(required = false) Boolean override) {
        if (override == null) {
            override = false;
        }

        if (dataManager.getDataHandler().getBus(bus.getName()) != null && !override) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser Bus existiert bereits, das überschreiben muss explizit angegeben sein.");
        }

        if (dataManager.getDataHandler().getBusType(bus.getType().getName()) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser BusType existiert nicht.");
        }

        dataManager.getDataHandler().addBus(new Bus(bus.getName(), bus.getType()));
        dataManager.getDataHandler().getDepot(dataManager.getDataHandler().getStation(bus.getStation()).getDepotName()).addBus(bus.getName());
    }

    @GetMapping("type/get/")
    public List<BusType> getAllBusTypes() {
        return dataManager.getDataHandler().getBusTypes();
    }

    @GetMapping("type/get/{typeName}")
    public BusType getBusType(@PathVariable String typeName) {
        BusType type = dataManager.getDataHandler().getBusType(typeName);

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

        if (dataManager.getDataHandler().getBusType(busType.getName()) != null && !override) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser BusType existiert bereits, das überschreiben muss explizit angegeben sein.");
        }

        dataManager.getDataHandler().addBusType(busType);
    }

    @DeleteMapping("type/remove/{type}")
    public void deleteBusType(@PathVariable("type") String typeName) {
        if (dataManager.getDataHandler().getBusType(typeName) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Der Type mit dem Namen " + typeName + " wurde nicht gefunden.");
        }

        dataManager.getDataHandler().removeBusType(typeName);
    }

    @DeleteMapping("remove/{bus}")
    public void deleteBus(@PathVariable("bus") String bus) {
        if (dataManager.getDataHandler().getBusType(bus) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Der Bus mit dem Namen " + bus + " wurde nicht gefunden.");
        }

        dataManager.getDataHandler().removeBusType(bus);
    }

    @GetMapping("getinfo")
    public StationList getInfo() {
        return new StationList(
                dataManager.getDataHandler().getAllStations().stream().map(BusStation::getName).collect(Collectors.toList())
        );
    }
}
