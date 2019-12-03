package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/station/")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class BusStationController {
    HashMap<String, BusStation> stations;
    HashMap<String, Depot> depots;
    HashMap<String, BusType> busTypes;
    HashMap<String, TerminalType> terminalTypes;

    public BusStationController() {
        depots = new HashMap<>();
        stations = new HashMap<>();
        busTypes = new HashMap<>();
        terminalTypes = new HashMap<>();
        createMockData();
    }

    private void createMockData() {
        depots.put("Zürich", new Depot("Zürich"));
        depots.put("Bern", new Depot("Bern"));
        depots.put("Genf", new Depot("Genf"));
        depots.put("Luzern", new Depot("Luzern"));

        stations.put("Zürich", new BusStation("Zürich", new Location(10, 20), depots.get("Zürich")));
        stations.put("Bern", new BusStation("Bern", new Location(20, 100), depots.get("Bern")));
        stations.put("Genf", new BusStation("Genf", new Location(-10, 50), depots.get("Genf")));

        busTypes.put("klein", new BusType("klein", 20, 5, 100, 200));
        busTypes.put("mittel", new BusType("mittel", 50, 10, 200, 150));
        busTypes.put("gross", new BusType("gross", 100, 20, 1000, 100));

        stations.get("Zürich").getDepot().getBuses().add(new Bus("ZH01", busTypes.get("klein")));
        stations.get("Zürich").getDepot().getBuses().add(new Bus("ZH02", busTypes.get("klein")));
        stations.get("Zürich").getDepot().getBuses().add(new Bus("ZH03", busTypes.get("klein")));
        stations.get("Zürich").getDepot().getBuses().add(new Bus("CH01", busTypes.get("mittel")));
        stations.get("Zürich").getDepot().getBuses().add(new Bus("CH02", busTypes.get("mittel")));
        stations.get("Zürich").getDepot().getBuses().add(new Bus("I01", busTypes.get("gross")));

        stations.get("Bern").getDepot().getBuses().add(new Bus("BE01", busTypes.get("klein")));
        stations.get("Bern").getDepot().getBuses().add(new Bus("BE02", busTypes.get("klein")));
        stations.get("Bern").getDepot().getBuses().add(new Bus("BE03", busTypes.get("klein")));
        stations.get("Bern").getDepot().getBuses().add(new Bus("CH03", busTypes.get("mittel")));
        stations.get("Bern").getDepot().getBuses().add(new Bus("CH04", busTypes.get("mittel")));
        stations.get("Bern").getDepot().getBuses().add(new Bus("I02", busTypes.get("gross")));

        stations.get("Genf").getDepot().getBuses().add(new Bus("GE01", busTypes.get("klein")));
        stations.get("Genf").getDepot().getBuses().add(new Bus("GE02", busTypes.get("klein")));
        stations.get("Genf").getDepot().getBuses().add(new Bus("GE03", busTypes.get("klein")));
        stations.get("Genf").getDepot().getBuses().add(new Bus("CH05", busTypes.get("mittel")));
        stations.get("Genf").getDepot().getBuses().add(new Bus("CH06", busTypes.get("mittel")));
        stations.get("Genf").getDepot().getBuses().add(new Bus("I03", busTypes.get("gross")));

        terminalTypes.put("national", new TerminalType("national", 50));
        terminalTypes.put("international", new TerminalType("international", 200));

        stations.get("Zürich").getTerminals().add(new Terminal(1, terminalTypes.get("national")));
        stations.get("Zürich").getTerminals().add(new Terminal(2, terminalTypes.get("national")));
        stations.get("Zürich").getTerminals().add(new Terminal(3, terminalTypes.get("national")));
        stations.get("Zürich").getTerminals().add(new Terminal(4, terminalTypes.get("international")));
        
        stations.get("Bern").getTerminals().add(new Terminal(5, terminalTypes.get("national")));
        stations.get("Bern").getTerminals().add(new Terminal(6, terminalTypes.get("national")));
        stations.get("Bern").getTerminals().add(new Terminal(7, terminalTypes.get("national")));
        stations.get("Bern").getTerminals().add(new Terminal(8, terminalTypes.get("international")));

        stations.get("Genf").getTerminals().add(new Terminal(9, terminalTypes.get("national")));
        stations.get("Genf").getTerminals().add(new Terminal(10, terminalTypes.get("national")));
        stations.get("Genf").getTerminals().add(new Terminal(11, terminalTypes.get("national")));
        stations.get("Genf").getTerminals().add(new Terminal(12, terminalTypes.get("international")));
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String string  = gson.toJson(stations);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\temp\\busmanager\\r.json", true));
            writer.append(string);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @GetMapping("depot/get/{station}")
    public Depot getDepot(@PathVariable(value = "station") String station) {
        return stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getDepot();
    }

    @GetMapping("depot/getAll")
    public Collection<Depot> getDepots() {
        return depots.values();
    }

    @GetMapping("depot/set/")
    public void setDepot(@RequestBody Depot depot) {
        depots.put(depot.getName(), depot);
    }

    @GetMapping("{station}/get")
    public BusStation getStation(@PathVariable(value = "station") String station) {
        return stations.getOrDefault(URLDecoder.decode(station, StandardCharsets.UTF_8), null);
    }

    @GetMapping("getAll")
    public Collection<BusStation> getStations() {
        return stations.values();
    }


    @GetMapping("bustype/get/{bustype}")
    public BusType getBusType(@PathVariable(value = "bustype") String bus) {
        return busTypes.get(URLDecoder.decode(bus, StandardCharsets.UTF_8));
    }

    @GetMapping("bustype/getAll/")
    public Collection<BusType> getAllBusTypes() {
        return busTypes.values();
    }
    @GetMapping("bustype/add/")
    public void setBusType(@RequestBody BusType type) {
        busTypes.put(type.getName(), type);
    }


    @GetMapping("{station}/bus/get/{bus}")
    public Bus getBus(@PathVariable(value = "station") String station, @PathVariable(value = "bus") String bus) {
        for (Bus bus1 : stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getDepot().getBuses()) {
            if (bus1.getName().equalsIgnoreCase(URLDecoder.decode(bus, StandardCharsets.UTF_8))) {
                return bus1;
            }
        }
        return null;
    }

    @GetMapping("{station}/bus/getAll/")
    public List<Bus> getAllBuses(@PathVariable(value = "station") String station) {
        return stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getDepot().getBuses();
    }

    @PostMapping("{station}/bus/set/")
    public ResponseEntity setBus(@PathVariable(value = "station") String station, @RequestBody Bus bus) {

        if (!stations.containsKey(station)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station mit dem Namen " + station + " nicht gefunden.");
        }

        if (bus.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BusType wurde nicht gesetzt.");
        }

        if (!busTypes.containsKey(bus.getType().getName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BusType mit dem Namen " + bus.getType().getName() + " nicht gefunden.");
        }

        bus.setType(busTypes.get(bus.getType().getName()));

        stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getDepot().getBuses().add(bus);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("{station}/gate/get/{id}")
    public Terminal getTerminal(@PathVariable(value = "station") String station, @PathVariable(value = "id") int id) {
        for (Terminal terminal : stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getTerminals()) {
            if (terminal.getId() == id) {
                return terminal;
            }
        }
        return null;
    }

    @GetMapping("{station}/gate/getAll")
    public List<Terminal> getTerminals(@PathVariable(value = "station") String station) {
        return stations.get(URLDecoder.decode(station, StandardCharsets.UTF_8)).getTerminals();
    }
}