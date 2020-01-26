package com.github.modul226b.BusManager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StationList {
    List<String> stations;
}
