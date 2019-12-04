package com.github.modul226b.BusManager.controller.daos;

import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BusDao {
    private String name;
    private BusType type;

    public static BusDao ToDao(Bus bus) {
        if (bus == null)
            return null;

        return new BusDao(bus.getName(), bus.getType());
    }
}
