package com.github.modul226b.BusManager;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class BusManagerApplication {

    public static void main(String[] args) {
        //createMockData();
        //createMockTrips();
        SpringApplication.run(BusManagerApplication.class, args);
    }

    public static void createMockData() {
        DataManager.getInstance().addDepot(new Depot("Zürich"));
        DataManager.getInstance().addDepot(new Depot("Bern"));
        DataManager.getInstance().addDepot(new Depot("Genf"));

        DataManager.getInstance().addLocation(new Location(10, 20));
        DataManager.getInstance().addLocation(new Location(20, 100));
        DataManager.getInstance().addLocation(new Location(-10, 50));

        DataManager.getInstance().addStation(new BusStation("Zürich", DataManager.getInstance().getLocation(0), DataManager.getInstance().getDepot("Zürich")));
        DataManager.getInstance().addStation(new BusStation("Bern", DataManager.getInstance().getLocation(1), DataManager.getInstance().getDepot("Bern")));
        DataManager.getInstance().addStation(new BusStation("Genf", DataManager.getInstance().getLocation(2), DataManager.getInstance().getDepot("Genf")));

        DataManager.getInstance().addBusType(new BusType("klein", 20, 5, 100, 200.0));
        DataManager.getInstance().addBusType(new BusType("mittel", 50, 10, 200, 150.0));
        DataManager.getInstance().addBusType(new BusType("gross", 100, 20, 1000, 100.0));

        DataManager.getInstance().addBus(new Bus("ZH01", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("ZH02", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("ZH03", DataManager.getInstance().getBusType("klein")));

        DataManager.getInstance().addBus(new Bus("BE01", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("BE02", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("BE03", DataManager.getInstance().getBusType("klein")));

        DataManager.getInstance().addBus(new Bus("GE01", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("GE02", DataManager.getInstance().getBusType("klein")));
        DataManager.getInstance().addBus(new Bus("GE03", DataManager.getInstance().getBusType("klein")));

        DataManager.getInstance().addBus(new Bus("CH01", DataManager.getInstance().getBusType("mittel")));
        DataManager.getInstance().addBus(new Bus("CH02", DataManager.getInstance().getBusType("mittel")));

        DataManager.getInstance().addBus(new Bus("CH03", DataManager.getInstance().getBusType("mittel")));
        DataManager.getInstance().addBus(new Bus("CH04", DataManager.getInstance().getBusType("mittel")));

        DataManager.getInstance().addBus(new Bus("CH05", DataManager.getInstance().getBusType("mittel")));
        DataManager.getInstance().addBus(new Bus("CH06", DataManager.getInstance().getBusType("mittel")));

        DataManager.getInstance().addBus(new Bus("I01", DataManager.getInstance().getBusType("gross")));
        DataManager.getInstance().addBus(new Bus("I02", DataManager.getInstance().getBusType("gross")));
        DataManager.getInstance().addBus(new Bus("I03", DataManager.getInstance().getBusType("gross")));

        DataManager.getInstance().getDepot("Zürich").addBus("ZH01");
        DataManager.getInstance().getDepot("Zürich").addBus("ZH02");
        DataManager.getInstance().getDepot("Zürich").addBus("ZH03");
        DataManager.getInstance().getDepot("Zürich").addBus("CH01");
        DataManager.getInstance().getDepot("Zürich").addBus("CH02");
        DataManager.getInstance().getDepot("Zürich").addBus("I01");

        DataManager.getInstance().getDepot("Bern").addBus("BE01");
        DataManager.getInstance().getDepot("Bern").addBus("BE02");
        DataManager.getInstance().getDepot("Bern").addBus("BE03");
        DataManager.getInstance().getDepot("Bern").addBus("CH03");
        DataManager.getInstance().getDepot("Bern").addBus("CH04");
        DataManager.getInstance().getDepot("Bern").addBus("I02");

        DataManager.getInstance().getDepot("Genf").addBus("GE01");
        DataManager.getInstance().getDepot("Genf").addBus("GE02");
        DataManager.getInstance().getDepot("Genf").addBus("GE03");
        DataManager.getInstance().getDepot("Genf").addBus("CH05");
        DataManager.getInstance().getDepot("Genf").addBus("CH06");
        DataManager.getInstance().getDepot("Genf").addBus("I03");

        TerminalType national = new TerminalType("national", 50);
        TerminalType international = new TerminalType("international", 200);

        DataManager.getInstance().addTerminalType(national);
        DataManager.getInstance().addTerminalType(international);

        DataManager.getInstance().addTerminal(new Terminal("Z01", national));
        DataManager.getInstance().addTerminal(new Terminal("Z02", national));
        DataManager.getInstance().addTerminal(new Terminal("Z03", national));
        DataManager.getInstance().addTerminal(new Terminal("Z04", international));

        DataManager.getInstance().addTerminal(new Terminal("B01", national));
        DataManager.getInstance().addTerminal(new Terminal("B02", national));
        DataManager.getInstance().addTerminal(new Terminal("B03", national));
        DataManager.getInstance().addTerminal(new Terminal("B04", international));

        DataManager.getInstance().addTerminal(new Terminal("G01", national));
        DataManager.getInstance().addTerminal(new Terminal("G02", national));
        DataManager.getInstance().addTerminal(new Terminal("G03", national));
        DataManager.getInstance().addTerminal(new Terminal("G04", international));

        DataManager.getInstance().getStation("Zürich").addTerminal(0);
        DataManager.getInstance().getStation("Zürich").addTerminal(1);
        DataManager.getInstance().getStation("Zürich").addTerminal(2);
        DataManager.getInstance().getStation("Zürich").addTerminal(3);

        DataManager.getInstance().getStation("Bern").addTerminal(4);
        DataManager.getInstance().getStation("Bern").addTerminal(5);
        DataManager.getInstance().getStation("Bern").addTerminal(6);
        DataManager.getInstance().getStation("Bern").addTerminal(7);

        DataManager.getInstance().getStation("Genf").addTerminal(8);
        DataManager.getInstance().getStation("Genf").addTerminal(9);
        DataManager.getInstance().getStation("Genf").addTerminal(10);
        DataManager.getInstance().getStation("Genf").addTerminal(11);
    }

    public static void createMockTrips() {
        DataManager.getInstance().addTrip(
                new Trip(
                        1,
                        LocalDateTime.parse("2007-12-03T20:00:00"),
                        LocalDateTime.parse("2007-12-03T21:00:00"),
                        DataManager.getInstance().getBus("ZH01"),
                        DataManager.getInstance().getStation("Zürich"),
                        DataManager.getInstance().getStation("Bern")
                )
        );


        DataManager.getInstance().addTrip(
                new Trip(
                        2,
                        LocalDateTime.parse("2008-12-03T10:00:00"),
                        LocalDateTime.parse("2008-12-03T11:00:00"),
                        DataManager.getInstance().getBus("ZH01"),
                        DataManager.getInstance().getStation("Zürich"),
                        DataManager.getInstance().getStation("Bern")
                )
        );

        Integer zürich = DataManager.getInstance().getStation("Zürich").getTerminalIds().get(0);
        Integer bern = DataManager.getInstance().getStation("Bern").getTerminalIds().get(0);

        DataManager.getInstance().getTerminal(zürich).getTripIds().add(1);
        DataManager.getInstance().getTerminal(zürich).getTripIds().add(2);

        DataManager.getInstance().getTerminal(bern).getTripIds().add(1);
        DataManager.getInstance().getTerminal(bern).getTripIds().add(2);
    }

}
