package com.github.modul226b.BusManager.helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeHelper {
    public static long toLong(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static LocalDateTime toLocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }
}
