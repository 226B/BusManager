package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Terminal;
import com.github.modul226b.BusManager.model.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class TripDto {
    private String startLocationName;
    private String arrivalLocationName;
    private LocalDateTime startTime;
    private LocalDateTime arrivalTime;
    private String startTerminal;
    private String arrivalTerminal;
    private String busType;

    public static TripDto toDto(Trip trip) {
        if (trip == null)  {
            return null;
        }
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(trip.getStartTime()), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(trip.getArrivalTime()), ZoneId.systemDefault());

        Integer id = trip.getId();
        Integer startId = trip.getStartId();
        Terminal terminalByTripId = DataManager.getInstance().getTerminalByTripId(id, startId);
        String displayName = terminalByTripId.getDisplayName();
        String displayName1 = DataManager.getInstance().getTerminalByTripId(trip.getId(), trip.getEndId()).getDisplayName();
        String typeName = DataManager.getInstance().getBus(trip.getBusName()).getTypeName();
        return new TripDto(
                DataManager.getInstance().getStation(trip.getStartId()).getName(),
                DataManager.getInstance().getStation(trip.getEndId()).getName(),
                start,
                end,
                displayName,
                displayName1,
                typeName
        );
    }
}
