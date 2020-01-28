package com.github.modul226b.BusManager.validator.internal;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.IValidatable;
import lombok.Getter;

/**
 * Base Class for the Validation mechanic. Every Validator needs to implement this Class.
 * @param <V> The Type that should be validated.
 */
@Getter
public abstract class AbstractValidator<V extends IValidatable> {
    private DataManager dataManager;
    private BusManager busManager;
    private TripManager tripManager;

    public AbstractValidator(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
        this.tripManager = tripManager;
    }

    /**
     * the Method that is Called for Validating a Object.
     * @param validation the Objects to be validated.
     * @return the {@link ValidationResult} that represents the result of the validation.
      */
    public abstract ValidationResult validate(V validation);
    public abstract Class<? extends IValidatable> getType();
}
