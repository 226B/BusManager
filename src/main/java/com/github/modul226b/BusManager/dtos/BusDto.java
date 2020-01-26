package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Data
public class BusDto {
    private transient String name;
    private BusType type;
    private String station;

    public static BusDto ToDao(DataManager dataManager, BusManager busManager, Bus bus) {
        if (bus == null)
            return null;

        return new BusDto(bus.getName(), dataManager.getDataHandler().getBusType(bus.getTypeName()), busManager.getCurrentStation(bus).getName());
    }
}
