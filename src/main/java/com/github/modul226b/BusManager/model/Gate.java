package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Gate {
    int id;
    GateType type;
    List<Trip> trips;
}
