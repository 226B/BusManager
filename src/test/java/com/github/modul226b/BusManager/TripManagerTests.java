package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TripManagerTests {

    @BeforeEach
    public void beforeEach() {
        MockDataHandler dataHandler = new MockDataHandler();
        DataManager.setInstance(dataHandler);

        //generell
        BusType klein = new BusType("klein", 100, 10, 750, 120.0);
        dataHandler.addBusType(klein);
        TerminalType normal = new TerminalType("normal", 100);
        dataHandler.addTerminalType(normal);
        Bus test1 = new Bus("Test1", klein);
        dataHandler.addBus(test1);

        //zürich
        Location zHLocation = new Location(100, 100);
        dataHandler.addLocation(zHLocation);

        Depot zHdepot = new Depot("ZHdepot");
        dataHandler.addDepot(zHdepot);
        zHdepot.addBus(test1.getName());

        BusStation zürich = new BusStation("zürich", zHLocation, zHdepot);
        dataHandler.addStation(zürich);
        Terminal zhTerminal1 = new Terminal("zh01", normal);
        dataHandler.addTerminal(zhTerminal1);
        Terminal zhTerminal2 = new Terminal("zh02", normal);
        dataHandler.addTerminal(zhTerminal2);
        zürich.addTerminal(zhTerminal1.getId());
        zürich.addTerminal(zhTerminal2.getId());

        //bern
        Location beLocation = new Location(1000, 0);
        dataHandler.addLocation(beLocation);

        Depot beDepot = new Depot("BEdepot");
        dataHandler.addDepot(beDepot);

        BusStation bern = new BusStation("bern", beLocation, beDepot);
        dataHandler.addStation(bern);

        Terminal beTerminal1 = new Terminal("be01", normal);
        dataHandler.addTerminal(beTerminal1);
        Terminal beTerminal2 = new Terminal("be02", normal);
        dataHandler.addTerminal(beTerminal2);
        bern.addTerminal(beTerminal1.getId());
        bern.addTerminal(beTerminal2.getId());

        //trips
        LocalDateTime trip1Time = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        LocalDateTime trip1ArrivalTime = TripManager.getInstance().getArrivalTime(trip1Time, klein, zürich, bern);
        Trip trip1 = new Trip(
                0,
                trip1Time,
                trip1ArrivalTime,
                test1,
                zürich,
                bern
        );
        DataManager.getInstance().getTerminal(zhTerminal1.getId()).getTripIds().add(trip1.getId());
        DataManager.getInstance().getTerminal(beTerminal1.getId()).getTripIds().add(trip1.getId());

        DataManager.getInstance().addTrip(trip1);
    }

    @Test
    public void test_add_trip() {
        //arrange
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 20, 0);

        //act
        Trip trip = TripManager.getInstance().addTrip("bern", 100, "zürich", start);

        //assert
        Assertions.assertEquals("Test1", trip.getBus().getName());
    }
}
