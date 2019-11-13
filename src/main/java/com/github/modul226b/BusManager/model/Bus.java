package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bus {
    String name;
    BusType type;
}
