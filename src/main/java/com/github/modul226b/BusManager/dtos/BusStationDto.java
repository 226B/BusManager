package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BusStationDto {
    private String name;
    private Location location;
    private String depotName;
    private List<Integer> terminalIds;

    public static BusStationDto toDto(DataManager dataManager, BusStation station) {
        return new BusStationDto(
                station.getName(),
                dataManager.getDataHandler().getLocation(station.getLocationId()),
                station.getDepotName(),
                station.getTerminalIds()
        );
    }
}
