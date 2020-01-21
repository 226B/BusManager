package com.github.modul226b.BusManager.service;

import java.util.concurrent.TimeUnit;

public abstract class AbstractService {
    private volatile boolean exit = false;
    private boolean started = false;

    public void start() {
        started = true;
        new Thread(() -> {
           while (!exit) {
               try {
                   TimeUnit.SECONDS.sleep(getInterval());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
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
