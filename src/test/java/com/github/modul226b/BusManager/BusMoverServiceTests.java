package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import com.github.modul226b.BusManager.service.services.BusMoverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BusMoverServiceTests {
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
        Bus test1 = new Bus( "Test1", klein);
        dataHandler.addBus(test1);

        //zürich
        Location zHLocation = new Location(1,  100, 100);
        dataHandler.addLocation(zHLocation);

        Depot zHdepot = new Depot( "ZHdepot");
        dataHandler.addDepot(zHdepot);
        zHdepot.addBus(test1.getName());

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

        dataHandler.addTrip(trip1);
    }

    @Test
    public void test_move_after_one_trip() {
        //arrange
        BusMoverService service = new BusMoverService(dataManager, busManager, tripManager);
        BusStation zürich = this.dataManager.getDataHandler().getStation("zürich");
        BusStation bern = this.dataManager.getDataHandler().getStation("bern");

        //act
        service.run();

        //assert
        Assertions.assertEquals(0, dataManager.getDataHandler().getBuses(dataManager.getDataHandler().getDepot(zürich.getDepotName()).getBusNames()).size());
        Assertions.assertEquals(1, dataManager.getDataHandler().getBuses(dataManager.getDataHandler().getDepot(bern.getDepotName()).getBusNames()).size());
    }
}
