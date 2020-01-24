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

public class TripManagerTests {
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
        Bus test1 = new Bus(dataManager, "Test1", klein);
        dataHandler.addBus(test1);

        //zürich
        Location zHLocation = new Location(dataManager, 100, 100);
        dataHandler.addLocation(zHLocation);

        Depot zHdepot = new Depot(dataManager, "ZHdepot");
        dataHandler.addDepot(zHdepot);
        zHdepot.addBus(test1.getName());

        BusStation zürich = new BusStation(dataManager, "zürich", zHLocation, zHdepot);
        dataHandler.addStation(zürich);
        Terminal zhTerminal1 = new Terminal(dataManager, "zh01", normal);
        dataHandler.addTerminal(zhTerminal1);
        Terminal zhTerminal2 = new Terminal(dataManager, "zh02", normal);
        dataHandler.addTerminal(zhTerminal2);
        zürich.addTerminal(zhTerminal1.getId());
        zürich.addTerminal(zhTerminal2.getId());

        //bern
        Location beLocation = new Location(dataManager, 1000, 0);
        dataHandler.addLocation(beLocation);

        Depot beDepot = new Depot(dataManager, "BEdepot");
        dataHandler.addDepot(beDepot);

        BusStation bern = new BusStation(dataManager, "bern", beLocation, beDepot);
        dataHandler.addStation(bern);

        Terminal beTerminal1 = new Terminal(dataManager, "be01", normal);
        dataHandler.addTerminal(beTerminal1);
        Terminal beTerminal2 = new Terminal(dataManager, "be02", normal);
        dataHandler.addTerminal(beTerminal2);
        bern.addTerminal(beTerminal1.getId());
        bern.addTerminal(beTerminal2.getId());

        //trips
        LocalDateTime trip1Time = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        LocalDateTime trip1ArrivalTime =tripManager.getArrivalTime(trip1Time, klein, zürich, bern);
        Trip trip1 = new Trip(
                dataManager,
                0,
                trip1Time,
                trip1ArrivalTime,
                test1,
                zürich,
                bern
        );
        dataHandler.getTerminal(zhTerminal1.getId()).getTripIds().add(trip1.getId());
        dataHandler.getTerminal(beTerminal1.getId()).getTripIds().add(trip1.getId());

        dataHandler.addTrip(trip1);
    }

    @Test
    public void test_add_trip() {
        //arrange
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 20, 0);

        //act
        Trip trip = this.tripManager.addTrip("bern", 100, "zürich", start);

        //assert
        Assertions.assertEquals("Test1", trip.getBus().getName());
    }
}
