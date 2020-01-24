package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BusManagerTests {
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

        //basel
        Location bsLocation = new Location(500, 800);
        dataHandler.addLocation(bsLocation);

        Depot bsDepot = new Depot("BSdepot");
        dataHandler.addDepot(bsDepot);

        BusStation basel = new BusStation("basel", bsLocation, bsDepot);
        dataHandler.addStation(basel);

        Terminal bsTerminal1 = new Terminal("bs01", normal);
        dataHandler.addTerminal(bsTerminal1);
        Terminal bsTerminal2 = new Terminal("bs02", normal);
        dataHandler.addTerminal(bsTerminal2);
        bern.addTerminal(bsTerminal1.getId());
        bern.addTerminal(bsTerminal2.getId());


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

        LocalDateTime trip2Time = LocalDateTime.of(2020, 1, 1, 20, 0, 0);
        LocalDateTime trip2ArrivalTime = TripManager.getInstance().getArrivalTime(trip2Time, klein, bern, basel);
        Trip trip2 = new Trip(
                1,
                trip2Time,
                trip2ArrivalTime,
                test1,
                bern,
                basel
        );
        DataManager.getInstance().getTerminal(zhTerminal1.getId()).getTripIds().add(trip2.getId());
        DataManager.getInstance().getTerminal(bsTerminal1.getId()).getTripIds().add(trip2.getId());

        DataManager.getInstance().addTrip(trip1);
        DataManager.getInstance().addTrip(trip2);
    }

    @Test
    public void test_get_latest_station_after_two_trips() {
        //arrange
        Bus bus = DataManager.getInstance().getBus("Test1");

        //act
        BusStation station = BusManager.getInstance().getLastStation(bus);

        //assert
        Assertions.assertNotNull(station);
        Assertions.assertEquals("basel",station.getName());
    }

    @Test
    public void test_get_station_in_between_trips() {
        //arrange
        Bus bus = DataManager.getInstance().getBus("Test1");
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 19, 45,0);

        //act
        BusStation station = BusManager.getInstance().getStationAtTime(bus, time);

        //assert
        Assertions.assertNotNull(station);
        Assertions.assertEquals("bern",station.getName());
    }

    @Test
    public void test_get_free_bus() {
        BusStation be = DataManager.getInstance().getStation("bern");
        BusStation zh = DataManager.getInstance().getStation("zürich");

        Bus bus = BusManager.getInstance().getFreeBus(LocalDateTime.now(), 100, be, zh);

        Assertions.assertNotNull(bus);
        Assertions.assertEquals("Test1", bus.getName());
    }
}
