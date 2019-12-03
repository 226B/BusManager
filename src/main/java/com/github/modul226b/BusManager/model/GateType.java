package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class GateType {
    String name;
    int capacity;

    public GateType(String name, int capacity) {
        assert name != null : "name can not be null";

        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GateType)) {
            return false;
        }
        GateType o2 = (GateType) obj;
        return this.name.equalsIgnoreCase(o2.name) && this.capacity == o2.capacity;
    }
}
