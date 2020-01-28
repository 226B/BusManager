package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.IDataHandler;
import com.github.modul226b.BusManager.model.IValidatable;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.AbstractValidator;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Class Managing the Validation process.
 * This Class will get all Classes that Implement {@link AbstractValidator} in the Package:
 * com.github.modul226b.BusManager.validator.validators
 */
public class ValidationManager {
    private List<AbstractValidator<? extends IValidatable>> abstractValidatorList;
    private DataManager dataManager;
    private BusManager busManager;
    private TripManager tripManager;

    /**
     * add Validator to the Validation Pool.
     * @param validator the Validator that should be added.
     */
    private void addValidator(AbstractValidator<? extends IValidatable> validator) {
        abstractValidatorList.add(validator);
    }

    /**
     * This Class will get all Instances in the Package "com.github.modul226b.BusManager.validator.validators"
     * @return a List of all AbstractValidators in the Package.
     */
    private List<AbstractValidator<? extends IValidatable>> loadAllValidators() {
        List<AbstractValidator<?>> result = new ArrayList<>();

        Reflections reflections = new Reflections("com.github.modul226b.BusManager.validator.validators");
        Set<Class<? extends AbstractValidator>> classes;
        classes = reflections.getSubTypesOf(AbstractValidator.class);
        for (Class<? extends AbstractValidator> c : classes) {
            try {
                result.add(c.getConstructor(DataManager.class, BusManager.class, TripManager.class).newInstance(dataManager, busManager, tripManager));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ValidationManager(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
        this.tripManager = tripManager;
        abstractValidatorList = loadAllValidators();
    }

    /**
     * This Class will call the {@link IDataHandler#getAllObjects()} method to get all Objects and Validate them with the right Validator.
     * @return the Result of the Validation.
     */
    public List<ValidationResult> validate() {
        List<ValidationResult> results = new ArrayList<>();
        List<IValidatable> objects = dataManager.getDataHandler().getAllObjects();
        for (AbstractValidator<?> abstractValidator : abstractValidatorList) {
            this.validate(objects, results, abstractValidator);
        }
        return results;
    }

    /**
     * Validating a single Objects.
     */
    public List<ValidationResult> validate(IValidatable... objects) {
        List<ValidationResult> results = new ArrayList<>();
        for (AbstractValidator<?> abstractValidator : abstractValidatorList) {
            this.validate(Arrays.asList(objects), results, abstractValidator);
        }
        return results;
    }



    private <V extends IValidatable> void validate(List<IValidatable> objects, List<ValidationResult> results, AbstractValidator<V> abstractValidator) {
        for (IValidatable object : objects) {
            if (abstractValidator.getType().isAssignableFrom(object.getClass())) {
                results.add(abstractValidator.validate((V) object));
            }
        }
    }

    private List<ValidationResult> validate(List<IValidatable> objects, AbstractValidator<? extends IValidatable> abstractValidator) {
        ArrayList<ValidationResult> results = new ArrayList<>();
        this.validate(objects, results, abstractValidator);
        return results;
    }

    public List<ValidationResult> validate(AbstractValidator<? extends IValidatable> abstractValidator) {
        List<IValidatable> validatables = dataManager.getDataHandler().getAllObjects();
        return this.validate(validatables, abstractValidator);
    }
}
