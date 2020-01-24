package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.IDataHandler;
import com.github.modul226b.BusManager.datahandeling.JsonDataHandler;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;

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

        ServiceManager.getInstance();
        List<ValidationResult> results = ValidationManager.getInstance().validate();
        for (ValidationResult result : results) {
            if (result.getValidation() == ValidationState.ERROR) {
                System.out.println("ERROR: " + result.getMessage());
            }
            if (result.getValidation() == ValidationState.WARNING) {
                System.out.println("WARNING: " + result.getMessage());
            }
        }
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
