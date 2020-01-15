package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.IDataHandler;
import com.github.modul226b.BusManager.datahandeling.JsonDataHandler;
import lombok.Getter;
import lombok.experimental.Delegate;

public class DataManager {
    private static DataManager instance;
    @Getter
    @Delegate
    private IDataHandler dataHandler;

    public DataManager(IDataHandler handler) {
        dataHandler = handler;
    }

    protected DataManager() {
        dataHandler = new JsonDataHandler("data.json");
    }

    public static void setInstance(IDataHandler handler) {
        instance = new DataManager(handler);
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
}
