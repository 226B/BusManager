package com.github.modul226b.BusManager.service.services;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Depot;
import com.github.modul226b.BusManager.service.AbstractService;

import java.time.LocalDateTime;

public class BusMoverService extends AbstractService {

    @Override
    public boolean startOnStartUp() {
        return true;
    }

    @Override
    public int getInterval() {
        return 60;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        for (Bus bus : DataManager.getInstance().getAllBuses()) {
            BusStation station = BusManager.getInstance().getStationAtTime(bus, now);
            Depot depotStation = BusManager.getInstance().getDepotStation(bus);
            if (!station.getName().equals(depotStation.getName())) {
                station.getDepot().addBus(bus.getName());
                depotStation.removeBus(bus.getName());
            }
        }
    }
}
