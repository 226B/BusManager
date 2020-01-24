package com.github.modul226b.BusManager.validator.validators;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.IValidatable;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import com.github.modul226b.BusManager.validator.internal.AbstractValidator;

public class NullValidator extends AbstractValidator<IValidatable> {
    public NullValidator(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        super(dataManager, busManager, tripManager);
    }

    @Override
    public ValidationResult validate(IValidatable validation) {
        if (validation == null) {
            return ValidationResult.create(ValidationState.ERROR, "Objekt vom Type " + this.getClass().getSimpleName() + " darf nicht null sein.");
        }else {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        }
    }

    @Override
    public Class<? extends IValidatable> getType() {
        return IValidatable.class;
    }
}
