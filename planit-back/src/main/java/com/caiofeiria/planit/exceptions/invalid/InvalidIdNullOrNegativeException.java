package com.caiofeiria.planit.exceptions.invalid;

public class InvalidIdNullOrNegativeException extends InvalidException{

	public InvalidIdNullOrNegativeException(Long id) {
		super("O Id está inválido: " + id);
	}
}
