package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.BusType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class caculateDistanceTests {
    @Test
    public void test() {

        TripManager.getInstance().getArrivalTime(
                LocalDateTime.now(), new BusType(null, 300, 10, ))
    }
}
