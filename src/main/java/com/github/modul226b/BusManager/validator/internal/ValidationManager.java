package com.github.modul226b.BusManager.validator.internal;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationManager{
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
        //todo
        return null;
    }
}
