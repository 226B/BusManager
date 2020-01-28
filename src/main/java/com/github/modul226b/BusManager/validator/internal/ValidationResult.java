package com.github.modul226b.BusManager.validator.internal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * this Class that represents the Result of a Validation.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    ValidationState validation;
    String message;

    public static ValidationResult create(ValidationState validation, String message) {
        return new ValidationResult(validation, message);
    }
}
