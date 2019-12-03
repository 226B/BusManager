package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Bus {
    public Bus(String name, BusType type) {
        assert name != null : "name can not be null";
        assert type != null : "type can not be null";

        this.name = name;
        this.type = type;
    }

    private String name;
    private BusType type;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bus)) {
            return false;
        }
        Bus o2 = (Bus) obj;
        return o2.name.equalsIgnoreCase(this.name) && this.getType().equals(o2.type);
    }
}
