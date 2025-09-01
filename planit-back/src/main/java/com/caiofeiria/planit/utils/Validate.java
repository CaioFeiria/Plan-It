package com.caiofeiria.planit.utils;

import com.caiofeiria.planit.exceptions.invalid.InvalidIdNullOrNegativeException;

import java.util.UUID;

public class Validate {

    public static void validarId(UUID id) {
        if (id == null || id.equals("")) {
            throw new InvalidIdNullOrNegativeException(id);
        }
    }
}
