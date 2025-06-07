package com.caiofeiria.planit.utils;

import com.caiofeiria.planit.exceptions.invalid.InvalidIdNullOrNegativeException;

public class Validate {

    public static void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdNullOrNegativeException(id);
        }
    }
}
