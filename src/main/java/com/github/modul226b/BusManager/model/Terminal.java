package com.github.modul226b.BusManager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Terminal implements IValidatable {
    private Integer id;
    private String displayName;
    private String typeName;
    private List<Integer> tripIds;

    public Terminal(Integer id, String displayName, TerminalType type) {
        assert type != null : "gatetype can not be null";
        assert displayName != null : "displayName can not be null";
        assert id != null : "ID can not be null";

        this.id = id;
        this.displayName = displayName;
        this.typeName = type.getName();
        this.tripIds = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Terminal)) {
            return false;
        }
        Terminal o2 = (Terminal) obj;
        return this.id.equals(o2.id) && this.getTypeName().equals(o2.getTypeName()) && this.tripIds.equals(o2.tripIds);
    }
}
