package com.caiofeiria.planit.exceptions.invalid;

public class InvalidBodyAndUrlId extends InvalidException{

	public InvalidBodyAndUrlId() {
		super("O ID fornecido no corpo da requisição não corresponde ao ID especificado na URL.");
	}
}
