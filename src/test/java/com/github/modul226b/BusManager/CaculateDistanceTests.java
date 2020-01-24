package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.BusType;
import com.github.modul226b.BusManager.model.Depot;
import com.github.modul226b.BusManager.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CaculateDistanceTests {
    @Test
    public void test() {
        //ARRANGE
        DataManager dataManager = new DataManager(new MockDataHandler());
        BusManager busManager = new BusManager(dataManager);
        TripManager tripManager = new TripManager(dataManager, busManager);

        Location location = new Location(1, 100, 100);
        dataManager.getDataHandler().addLocation(location);
        Location location1 = new Location(2, 200, 200);
        dataManager.getDataHandler().addLocation(location1);
        Depot zd = new Depot( "Zd");
        dataManager.getDataHandler().addDepot(zd);
        Depot bd = new Depot( "Bd");
        dataManager.getDataHandler().addDepot(bd);

        BusStation z = new BusStation( "z", location, zd);
        BusStation b = new BusStation( "b", location1, bd);
        BusType busType = new BusType("bus", 300, 10, 10, 100.0);

        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0,0,0);

        //ACT
        LocalDateTime time = tripManager.getArrivalTime(
                now, busType,
                z,
                b);

        //ASSERT
        Assertions.assertEquals(2020, time.getYear());
        Assertions.assertEquals(1, time.getMonth().getValue());
        Assertions.assertEquals(1, time.getDayOfMonth());
        Assertions.assertEquals(1, time.getHour());
        Assertions.assertEquals(24, time.getMinute());
        Assertions.assertEquals(52, time.getSecond());
    }
}
