package com.github.modul226b.BusManager.validator.validators;

import com.github.modul226b.BusManager.model.IValidatable;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import com.github.modul226b.BusManager.validator.internal.Validator;

public class NullValidator extends Validator<IValidatable> {
    @Override
    public ValidationResult validate(IValidatable validation) {
        if (validation == null) {
            return ValidationResult.create(ValidationState.ERROR, "Objekt vom Type " + this.getClass().getSimpleName() + " darf nicht null sein.");
        }else {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        }
    }

    @Override
    public Class<?> getType() {
        return IValidatable.class;
    }
}
