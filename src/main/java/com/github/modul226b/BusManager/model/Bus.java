package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bus {
    public Bus() {
    }

    String name;
    BusType type;
}
