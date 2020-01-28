package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.IDataHandler;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import lombok.Getter;

import java.util.List;

/**
 * Base Class to get all Objects currently in the Application.
 */
public class DataManager {
    @Getter
    private IDataHandler dataHandler;

    public DataManager(IDataHandler handler) {
        dataHandler = handler;
    }

    private void validate(ValidationManager validationManager) {
        List<ValidationResult> results = validationManager.validate();
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
