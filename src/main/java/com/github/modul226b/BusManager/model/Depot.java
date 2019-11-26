package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class Depot {
    String name;
    List<Bus> buses;
}