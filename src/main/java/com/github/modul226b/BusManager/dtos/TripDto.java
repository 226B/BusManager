package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.helpers.TimeHelper;
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

    public static TripDto toDto(DataManager dataManager, Trip trip) {
        if (trip == null)  {
            return null;
        }
        LocalDateTime start = TimeHelper.toLocalDateTime(trip.getStartTime());
        LocalDateTime end = TimeHelper.toLocalDateTime(trip.getArrivalTime());

        Integer id = trip.getId();
        Integer startId = trip.getStartId();
        Terminal terminalByTripId = dataManager.getDataHandler().getTerminalByTripId(id, startId);
        String displayName = terminalByTripId.getDisplayName();
        String displayName1 = dataManager.getDataHandler().getTerminalByTripId(trip.getId(), trip.getEndId()).getDisplayName();
        String typeName = dataManager.getDataHandler().getBus(trip.getBusName()).getTypeName();
        return new TripDto(
                dataManager.getDataHandler().getStation(trip.getStartId()).getName(),
                dataManager.getDataHandler().getStation(trip.getEndId()).getName(),
                start,
                end,
                displayName,
                displayName1,
                typeName
        );
    }
}
