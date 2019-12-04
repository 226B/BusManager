package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

@Getter
public class Bus {
    private String name;
    private String typeName;

    public Bus(String name, BusType type) {
        assert name != null : "name can not be null";
        assert type != null : "Type can not be null";

        DataManager instance = DataManager.getInstance();
        assert instance.getBusType(type.getName()) != null : "bustype must be registered.";

        this.name = name;
        this.typeName = type.getName();
    }

    public BusType getType() {
        return DataManager.getInstance().getBusType(this.typeName);
    }

    public void setType(BusType type) {
        assert type != null : "type can not be null";
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
