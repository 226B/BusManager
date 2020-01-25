package com.github.modul226b.BusManager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateTripInformation {
    private List<Integer> capacities;
    private List<String> stations;
}
