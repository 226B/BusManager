package com.github.modul226b.BusManager.service.services;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Depot;
import com.github.modul226b.BusManager.service.AbstractService;

import java.time.LocalDateTime;

/**
 * Service responsible for moving Buses to the right Station.
 */
public class BusMoverService extends AbstractService {

    public BusMoverService(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        super(dataManager, busManager, tripManager);
    }

    @Override
    public boolean startOnStartUp() {
        return true;
    }

    @Override
    public int getIntervalInSeconds() {
        return 60;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        for (Bus bus : this.getDataManager().getDataHandler().getAllBuses()) {
            BusStation station = this.getBusManager().getStationAtTime(bus, now);
            Depot depotStation = this.getBusManager().getDepotStation(bus);
            if (!station.getName().equals(depotStation.getName())) {
                this.getDataManager().getDataHandler().getDepot(station.getDepotName()).addBus(bus.getName());
                depotStation.removeBus(bus.getName());
            }
        }
    }
}
