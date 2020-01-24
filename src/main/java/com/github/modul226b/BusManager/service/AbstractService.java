package com.github.modul226b.BusManager.service;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public abstract class AbstractService {
    private volatile boolean exit = false;
    private boolean started = false;
    @Getter
    DataManager dataManager;
    @Getter
    private BusManager busManager;
    @Getter
    private TripManager tripManager;

    public AbstractService(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
        this.tripManager = tripManager;
    }

    public void start() {
        started = true;
        System.out.println("starting Service " + this.getClass().getSimpleName());
        new Thread(() -> {
           while (!exit) {
               try {
                   TimeUnit.SECONDS.sleep(getInterval());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("running " + this.getClass().getSimpleName() + ".");
               run();
           }
        }).start();
    }

    public void stop() {
        exit = true;
        started = false;
    }

    public void startIfStopped() {
        if (!started) {
            start();
        }
    }

    public abstract boolean startOnStartUp();

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public abstract int getInterval();

    public abstract void run();
}
