package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.model.IValidatable;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.Validator;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ValidationManager {
    private static ValidationManager instance;

    public static ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }

    private List<Validator<?>> validatorList;

    private List<Validator<?>> loadAllValidators() {
        List<Validator<?>> result = new ArrayList<>();

        Reflections reflections = new Reflections("com.github.modul226b.BusManager.validator.validators");
        Set<Class<? extends Validator>> classes;
        classes = reflections.getSubTypesOf(Validator.class);
        for (Class<? extends Validator> c : classes) {
            try {
                result.add(c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ValidationManager() {
        validatorList = loadAllValidators();
    }

    public List<ValidationResult> validate() {
        List<ValidationResult> results = new ArrayList<>();
        List<IValidatable> objects = DataManager.getInstance().getAllObjects();
        for (Validator<?> validator : validatorList) {
            this.validate(objects, results, validator);
        }
        return results;
    }

    public List<ValidationResult> validate(IValidatable... objects) {
        List<ValidationResult> results = new ArrayList<>();
        for (Validator<?> validator : validatorList) {
            this.validate(Arrays.asList(objects), results, validator);
        }
        return results;
    }



    private <V extends IValidatable> void validate(List<IValidatable> objects, List<ValidationResult> results, Validator<V> validator) {
        for (IValidatable object : objects) {
            if (validator.getType().isAssignableFrom(object.getClass())) {
                results.add(validator.validate((V) object));
            }
        }
    }

    private List<ValidationResult> validate(List<IValidatable> objects, Validator<? extends IValidatable> validator) {
        ArrayList<ValidationResult> results = new ArrayList<>();
        this.validate(objects, results, validator);
        return results;
    }

    public List<ValidationResult> validate(Validator<? extends IValidatable> validator) {
        List<IValidatable> validatables = DataManager.getInstance().getAllObjects();
        return this.validate(validatables, validator);
    }
}
