package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.dtos.BusStationDto;
import com.github.modul226b.BusManager.dtos.TerminalDto;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Depot;
import com.github.modul226b.BusManager.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/station/")
@CrossOrigin(origins = {"*"})
public class StationController {
    private DataManager dataManager;
    private BusManager busManager;

    @Autowired
    public StationController(DataManager dataManager, BusManager busManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
    }

    @GetMapping("get")
    public List<BusStationDto> getAll() {
        return dataManager.getDataHandler().getAllStations().stream()
                .map(station -> BusStationDto.toDto(dataManager, station))
                .collect(Collectors.toList());
    }

    @PostMapping("add")
    public void add(@RequestBody BusStationDto station) {
        if (dataManager.getDataHandler().getDepot(station.getDepotName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Depot mit diesem namen existiert bereits.");
        }

        Integer id = station.getLocation().getId();

        if (id == null) {
            id = dataManager.getDataHandler().getNextLocationId();
        }

        Depot depot = new Depot(station.getDepotName());

        BusStation station1 = new BusStation(
                station.getName(),
                new Location(id, station.getLocation().getX(), station.getLocation().getY()),
                depot
        );

        dataManager.getDataHandler().addDepot(depot);
        dataManager.getDataHandler().addStation(station1);
    }
}