package com.github.modul226b.BusManager.model;

import com.github.modul226b.BusManager.manager.DataManager;
import lombok.Getter;

@Getter
public class Location implements IValidatable {
    private DataManager dataManager;
    private Integer id;
    private Integer x;
    private Integer y;

    public Location(DataManager dataManager, Integer id, Integer x, Integer y) {
        this.dataManager = dataManager;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Location(DataManager dataManager, Integer x, Integer y) {
        this(dataManager, dataManager.getDataHandler().getNextLocationId(), x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location o2 = (Location) obj;

        return this.x.equals(o2.x) && this.y.equals(o2.y);
    }
}
