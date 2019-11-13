package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class BusStation {
    String name;
    Location location;
    Depot depot;
    List<Gate> gates;
}
