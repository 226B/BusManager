package com.github.modul226b.BusManager.validator.internal;

import com.github.modul226b.BusManager.model.IValidatable;

public abstract class Validator<V extends IValidatable> {
    public abstract Validation validate(V validation);
}
