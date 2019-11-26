package com.github.modul226b.BusManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Terminal {
    int id;
    TerminalType type;
    List<Trip> trips;
}
