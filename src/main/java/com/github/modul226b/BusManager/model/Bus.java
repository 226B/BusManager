package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class Bus implements IValidatable {
    private String name;
    private String typeName;
    private DataManager dataManager;

    public Bus(DataManager dataManager, String name, BusType type) {
        this.dataManager = dataManager;

        assert dataManager != null:  "dataManager can not be null";
        assert name != null : "name can not be null";
        assert type != null : "Type can not be null";

        assert dataManager.getDataHandler().getBusType(type.getName()) != null : "bustype must be registered.";

        this.name = name;
        this.typeName = type.getName();
    }

    public BusType getType() {
        return dataManager.getDataHandler().getBusType(this.typeName);
    }

    public void setType(BusType type) {
        assert type != null : "type can not be null";
        assert dataManager.getDataHandler().getBusType(type.getName()) != null : "type must be registered.";

        this.typeName = type.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bus)) {
            return false;
        }
        Bus o2 = (Bus) obj;
        return o2.name.equalsIgnoreCase(this.name) && this.getType().equals(o2.getType());
    }
}
