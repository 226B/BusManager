package com.github.modul226b.BusManager.controller;


import com.github.modul226b.BusManager.dtos.CreateTripDto;
import com.github.modul226b.BusManager.dtos.TripDto;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Terminal;
import com.github.modul226b.BusManager.model.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @PostMapping("add")
    public ResponseEntity<TripDto> addTrip(@RequestBody CreateTripDto createTripDto) {

        BusStation start = DataManager.getInstance().getStation(createTripDto.getStartStation());
        BusStation end = DataManager.getInstance().getStation(createTripDto.getEndStation());

        if (start == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start station existiert nicht.");
        }
        if (end == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endstation existiert nicht.");
        }

        for (Trip trip : DataManager.getInstance().getAllTrips()) {
            if (trip.getStartTime() == createTripDto.getTime().atZone(ZoneId.of("EST")).toInstant().toEpochMilli()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Es exisitert bereits eine Fahrt zu dieser Zeit.");
            }
        }

        Bus bus = TripManager.getInstance().getFreeBus(createTripDto.getTime(), start, end);

        if (bus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freier Bus in "+ start.getName() +" gefunden.");
        }

        LocalDateTime arrivalTime = TripManager.getInstance().getArrivalTime(createTripDto.getTime(), bus.getType(), start, end);

        if (arrivalTime == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler beim berechnen der Ankunftszeit.");
        }

        Terminal startTerminal = TripManager.getInstance().getFreeTerminals(bus.getType(), start, createTripDto.getTime()).stream().findFirst().orElse(null);

        if (startTerminal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freies Terminal in " + start.getName()  + " gefunden.");
        }

        Terminal endTerminal = TripManager.getInstance().getFreeTerminals(bus.getType(), start, arrivalTime).stream().findFirst().orElse(null);

        if (endTerminal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein freies Terminal in " + end.getName()  + " gefunden.");
        }

        Trip trip = new Trip(
                createTripDto.getTime().atZone(ZoneId.of("EST")).toInstant().toEpochMilli(),
                arrivalTime.atZone(ZoneId.of("EST")).toInstant().toEpochMilli(),
                bus,
                start.getLocation(),
                end.getLocation()
        );

        DataManager.getInstance().addBus(bus);
        DataManager.getInstance().addTrip(trip);
        startTerminal.getTripIds().add(trip.getId());
        endTerminal.getTripIds().add(trip.getId());

        return new ResponseEntity<>(TripDto.toDto(trip), HttpStatus.OK);
    }
}
