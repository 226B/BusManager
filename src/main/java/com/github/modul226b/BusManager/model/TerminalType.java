package com.github.modul226b.BusManager.model;

import lombok.Getter;

@Getter
public class TerminalType {
    private String name;
    private Integer capacity;

    public TerminalType(String name, Integer capacity) {
        assert name != null : "name can not be null";

        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TerminalType)) {
            return false;
        }
        TerminalType o2 = (TerminalType) obj;
        return this.name.equalsIgnoreCase(o2.name) && this.capacity.equals(o2.capacity);
    }
}
