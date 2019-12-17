package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class TripDto {
    private String startLocationName;
    private String arrivalLocationName;
    private LocalDate startTime;
    private LocalDate arrivalTime;
    private String startTerminal;
    private String arrivalTerminal;
    private String busType;

    public static TripDto toDto(Trip trip) {
        if (trip == null)  {
            return null;
        }

        return new TripDto(
                DataManager.getInstance().getStation(trip.getStartId()).getName(),
                DataManager.getInstance().getStation(trip.getEndId()).getName(),
                Instant.ofEpochMilli(trip.getStartTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
                Instant.ofEpochMilli(trip.getArrivalTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
                DataManager.getInstance().getTerminalByTripId(trip.getId(), trip.getStartId()).getDisplayName(),
                DataManager.getInstance().getTerminalByTripId(trip.getId(), trip.getEndId()).getDisplayName(),
                DataManager.getInstance().getBus(trip.getBusName()).getTypeName()
        );
    }
}
