package com.github.modul226b.BusManager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CreateTripDto {
    String startStation;
    String endStation;
    LocalDateTime time;
}
