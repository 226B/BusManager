package com.github.modul226b.BusManager;


import com.github.modul226b.BusManager.datahandeling.MockDataHandler;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.Location;
import com.github.modul226b.BusManager.manager.ValidationManager;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import com.github.modul226b.BusManager.validator.validators.NullValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ValidationTests {
    @Test
    public void testNullValidation() {
        DataManager dataManager = new DataManager(new MockDataHandler());
        BusManager busManager = new BusManager(dataManager);
        TripManager tripManager = new TripManager(dataManager, busManager);

        ValidationManager manager = new ValidationManager(dataManager, busManager, tripManager);

        dataManager.getDataHandler().addLocation(new Location(1, 100, 100));

        List<ValidationResult> result = manager.validate(new NullValidator(dataManager, busManager, tripManager));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(ValidationState.SUCCESS, result.get(0).getValidation());
    }
}
