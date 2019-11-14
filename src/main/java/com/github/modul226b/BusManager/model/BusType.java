package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusType {
    public BusType() {
    }

    String name;
    int capacity;
    int recoveryTime;
    int maxRange;
    int distancePer100;
}
