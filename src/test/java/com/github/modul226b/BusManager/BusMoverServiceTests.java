package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.helpers.TimeHelper;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import com.github.modul226b.BusManager.service.services.BusMoverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class BusMoverServiceTests {
    @BeforeEach
    public void beforeEach() {
        MockDataHandler dataHandler = new MockDataHandler();
        DataManager.setInstance(dataHandler);

        BusType klein = new BusType("klein", 100, 10, 750, 120.0);
        dataHandler.addBusType(klein);
        TerminalType normal = new TerminalType("normal", 100);
        dataHandler.addTerminalType(normal);
        Bus test1 = new Bus("Test1", klein);
        dataHandler.addBus(test1);

        Location zHLocation = new Location(100, 100);
        dataHandler.addLocation(zHLocation);

        Depot zHdepot = new Depot("ZHdepot");
        dataHandler.addDepot(zHdepot);

        BusStation zürich = new BusStation("zürich", zHLocation, zHdepot);
        dataHandler.addStation(zürich);
        Terminal zhTerminal1 = new Terminal("zh01", normal);
        dataHandler.addTerminal(zhTerminal1);
        Terminal zhTerminal2 = new Terminal("zh02", normal);
        dataHandler.addTerminal(zhTerminal2);
        zürich.addTerminal(zhTerminal1.getId());
        zürich.addTerminal(zhTerminal2.getId());

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


        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        LocalDateTime arrivalTime = TripManager.getInstance().getArrivalTime(time, klein, zürich, bern);
        Trip trip = new Trip(
                0,
                time,
                arrivalTime,
                test1,
                zürich,
                bern
        );

        DataManager.getInstance().getTerminal(zhTerminal1.getId()).getTripIds().add(trip.getId());
        DataManager.getInstance().getTerminal(beTerminal1.getId()).getTripIds().add(trip.getId());

        DataManager.getInstance().addTrip(trip);
    }

    @Test
    public void simpleTest() {
        BusMoverService service = new BusMoverService();
        service.run();
    }
}
