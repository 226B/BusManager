package com.github.modul226b.BusManager.validator.internal;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class            ValidationManager{
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
        for (Validator<?> validator : validatorList) {
            this.validate(results, validator);
        }
        return results;
    }

    private void validate(List<ValidationResult> results, Validator<?> validator) {
        //todo
    }

    public List<ValidationResult> validate(Validator<?> validator) {
        ArrayList<ValidationResult> results = new ArrayList<>();
        this.validate(results, validator);
        return results;
    }
}
