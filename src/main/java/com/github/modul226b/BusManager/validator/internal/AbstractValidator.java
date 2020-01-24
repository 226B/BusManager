package com.github.modul226b.BusManager.validator.internal;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.IValidatable;
import lombok.Getter;

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

    public abstract ValidationResult validate(V validation);
    public abstract Class<? extends IValidatable> getType();

    public boolean isType(Object object) {
        return object.getClass() == getType();
    }

}
