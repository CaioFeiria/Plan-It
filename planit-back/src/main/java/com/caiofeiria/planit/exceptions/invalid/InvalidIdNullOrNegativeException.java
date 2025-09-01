package com.caiofeiria.planit.exceptions.invalid;

import java.util.UUID;

public class InvalidIdNullOrNegativeException extends InvalidException{

	public InvalidIdNullOrNegativeException(UUID id) {
		super("O Id está inválido: " + id);
	}
}
