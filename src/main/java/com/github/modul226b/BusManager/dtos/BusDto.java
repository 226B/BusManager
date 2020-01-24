package com.github.modul226b.BusManager.dtos;

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

    public static BusDto ToDao(DataManager dataManager, Bus bus) {
        if (bus == null)
            return null;

        return new BusDto(bus.getName(), dataManager.getDataHandler().getBusType(bus.getTypeName()));
    }
}
