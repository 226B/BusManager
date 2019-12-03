package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class Bus {
    private String name;
    private String typeName;

    public Bus(String name, BusType type) {
        assert name != null : "name can not be null";
        assert type != null : "Type can not be null";

        this.name = name;
        this.typeName = type.getName();
    }

    public void setType(BusType type) {
        assert type != null : "type can not be null";
        this.typeName = type.getName();
    }

    public BusType getType() {
        return null; //todo
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
