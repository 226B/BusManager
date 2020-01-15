package com.github.modul226b.BusManager.validator.internal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    Validation validation;
    String message;

    public static ValidationResult get(Validation validation, String message) {
        return new ValidationResult(validation, message);
    }
}
