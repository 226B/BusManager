package com.github.modul226b.BusManager.validator.internal;

import com.github.modul226b.BusManager.model.IValidatable;

public abstract class Validator<V extends IValidatable> {
    public abstract ValidationResult validate(V validation);
    public abstract Class<? extends IValidatable> getType();

    public boolean isType(Object object) {
        return object.getClass() == getType();
    }

}
