package com.github.modul226b.BusManager.model;

import lombok.Getter;

/**
 * Class Representing a TerminalType.
 */
@Getter
public class TerminalType implements IValidatable {
    private String name;
    private Integer capacity;

    /**
     * Can can not be Null.
     * @param name not null, the Name if the Type.
     * @param capacity the Capacity of the Terminal.
     */
    public TerminalType(String name, Integer capacity) {
        assert name != null : "name can not be null";
        assert capacity != null : "capacity can not be null";

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
