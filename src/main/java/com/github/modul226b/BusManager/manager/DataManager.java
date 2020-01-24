package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.IDataHandler;
import com.github.modul226b.BusManager.datahandeling.JsonDataHandler;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;

public class DataManager {
    @Getter
    private IDataHandler dataHandler;

    public DataManager(IDataHandler handler) {
        dataHandler = handler;
    }

    public DataManager() {
        dataHandler = new JsonDataHandler("data.json");
    }

    private void validate() {
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
}
