package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.helpers.TimeHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class HelperTests {
    @Test
    public void longToLocalDateTime() {
        LocalDateTime x = TimeHelper.toLocalDateTime(1499070300000L);
        Assertions.assertEquals(2017, x.getYear());
        Assertions.assertEquals(7, x.getMonth().getValue());
        Assertions.assertEquals(3, x.getDayOfMonth());
        Assertions.assertEquals(10, x.getHour());
        Assertions.assertEquals(25, x.getMinute());
        Assertions.assertEquals(0, x.getSecond());
    }


    @Test
    public void localDateTimeToLong() {
        LocalDateTime localDateTime = LocalDateTime.of(2017, 7, 3, 10, 25, 0);
        long l = TimeHelper.toLong(localDateTime);

        Assertions.assertEquals(1499070300000L, l);
    }
}
