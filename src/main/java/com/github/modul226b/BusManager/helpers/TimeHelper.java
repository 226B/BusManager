package com.github.modul226b.BusManager.helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeHelper {
    public static long toLong(LocalDateTime time) {
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
