package com.github.modul226b.BusManager.validator.internal;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationManager{

    public List<Validator> getAllValidators() {
        List<Validator> result = new ArrayList<>();

        Reflections reflections = new Reflections("com.github.modul226b.BusManager.validator.validators");
        Set<Class<? extends Validator>> classes = reflections.getSubTypesOf(Validator.class);
        for (Class<? extends Validator> c : classes) {
            try {
                result.add(c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
