package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BusManagerTests {
    private DataManager dataManager;
    private BusManager busManager;
    private TripManager tripManager;

    @BeforeEach
    public void beforeEach() {
        MockDataHandler dataHandler = new MockDataHandler();
        dataManager = new DataManager(dataHandler);
        busManager = new BusManager(dataManager);
        tripManager = new TripManager(dataManager, busManager);

        //generell
        BusType klein = new BusType("klein", 100, 10, 750, 120.0);
        dataHandler.addBusType(klein);
        TerminalType normal = new TerminalType("normal", 100);
        dataHandler.addTerminalType(normal);
        Bus test1 = new Bus("Test1", klein);
        dataHandler.addBus(test1);
        Bus test2 = new Bus("Test2", klein);
        dataHandler.addBus(test2);

        //zürich
        Location zHLocation = new Location(1, 100, 100);
        dataHandler.addLocation(zHLocation);

        Depot zHdepot = new Depot( "ZHdepot");
        dataHandler.addDepot(zHdepot);
        zHdepot.addBus(test1.getName());
        zHdepot.addBus(test2.getName());

        BusStation zürich = new BusStation( "zürich", zHLocation, zHdepot);
        dataHandler.addStation(zürich);
        Terminal zhTerminal1 = new Terminal(1, "zh01", normal);
        dataHandler.addTerminal(zhTerminal1);
        Terminal zhTerminal2 = new Terminal(2, "zh02", normal);
        dataHandler.addTerminal(zhTerminal2);
        zürich.addTerminal(zhTerminal1.getId());
        zürich.addTerminal(zhTerminal2.getId());

        //bern
        Location beLocation = new Location(2, 1000, 0);
        dataHandler.addLocation(beLocation);

        Depot beDepot = new Depot( "BEdepot");
        dataHandler.addDepot(beDepot);

        BusStation bern = new BusStation( "bern", beLocation, beDepot);
        dataHandler.addStation(bern);

        Terminal beTerminal1 = new Terminal(3, "be01", normal);
        dataHandler.addTerminal(beTerminal1);
        Terminal beTerminal2 = new Terminal(4, "be02", normal);
        dataHandler.addTerminal(beTerminal2);
        bern.addTerminal(beTerminal1.getId());
        bern.addTerminal(beTerminal2.getId());

        //basel
        Location bsLocation = new Location(3, 500, 800);
        dataHandler.addLocation(bsLocation);

        Depot bsDepot = new Depot( "BSdepot");
        dataHandler.addDepot(bsDepot);

        BusStation basel = new BusStation( "basel", bsLocation, bsDepot);
        dataHandler.addStation(basel);

        Terminal bsTerminal1 = new Terminal(5, "bs01", normal);
        dataHandler.addTerminal(bsTerminal1);
        Terminal bsTerminal2 = new Terminal(6, "bs02", normal);
        dataHandler.addTerminal(bsTerminal2);
        bern.addTerminal(bsTerminal1.getId());
        bern.addTerminal(bsTerminal2.getId());


        //trips
        LocalDateTime trip1Time = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        LocalDateTime trip1ArrivalTime = tripManager.getArrivalTime(trip1Time, klein, zürich, bern);
        Trip trip1 = new Trip(
                0,
                TimeHelper.toLong(trip1Time),
                TimeHelper.toLong(trip1ArrivalTime),
                test1,
                dataHandler.getLocation(zürich.getLocationId()),
                dataHandler.getLocation(bern.getLocationId())
        );
        dataHandler.getTerminal(zhTerminal1.getId()).getTripIds().add(trip1.getId());
        dataHandler.getTerminal(beTerminal1.getId()).getTripIds().add(trip1.getId());

        LocalDateTime trip2Time = LocalDateTime.of(2020, 1, 1, 20, 0, 0);
        LocalDateTime trip2ArrivalTime = tripManager.getArrivalTime(trip2Time, klein, bern, basel);
        Trip trip2 = new Trip(
                1,
                TimeHelper.toLong(trip2Time),
                TimeHelper.toLong(trip2ArrivalTime),
                test1,
                dataHandler.getLocation(bern.getLocationId()),
                dataHandler.getLocation(basel.getLocationId())
        );
        dataHandler.getTerminal(zhTerminal1.getId()).getTripIds().add(trip2.getId());
        dataHandler.getTerminal(bsTerminal1.getId()).getTripIds().add(trip2.getId());

        dataHandler.addTrip(trip1);
        dataHandler.addTrip(trip2);
    }

    @Test
    public void test_get_latest_station_after_two_trips() {
        //arrange
        Bus bus = this.dataManager.getDataHandler().getBus("Test1");

        //act
        BusStation station = this.busManager.getLastStation(bus);

        //assert
        Assertions.assertNotNull(station);
        Assertions.assertEquals("basel", station.getName());
    }

    @Test
    public void test_get_station_in_between_trips() {
        //arrange
        Bus bus = this.dataManager.getDataHandler().getBus("Test1");
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 19, 45, 0);

        //act
        BusStation station = this.busManager.getStationAtTime(bus, time);

        //assert
        Assertions.assertNotNull(station);
        Assertions.assertEquals("bern", station.getName());
    }

    @Test
    public void test_get_free_bus_no_bus_found() {
        BusStation be = this.dataManager.getDataHandler().getStation("bern");

        Bus bus = this.busManager.getFreeBus(LocalDateTime.now(), 100, be);

        Assertions.assertNull(bus);
    }

    @Test
    public void test_get_free_bus() {
        BusStation bs = this.dataManager.getDataHandler().getStation("basel");

        Bus bus = this.busManager.getFreeBus(LocalDateTime.now(), 100, bs);

        Assertions.assertNotNull(bus);
        Assertions.assertEquals("Test1", bus.getName());
    }

    @Test
    public void test_get_station_for_bus_with_no_trips() {
        //arrange
        Bus bus = this.dataManager.getDataHandler().getBus("Test2");
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 19, 45, 0);

        //act
        BusStation station = busManager.getStationAtTime(bus, time);

        //assert
        Assertions.assertNotNull(station);
        Assertions.assertEquals("zürich", station.getName());
    }
}
