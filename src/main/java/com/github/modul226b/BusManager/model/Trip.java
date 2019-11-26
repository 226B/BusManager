package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    long startTime;
    long arrivalTime;
    Bus bus;
    Location start;
    Location end;
}
