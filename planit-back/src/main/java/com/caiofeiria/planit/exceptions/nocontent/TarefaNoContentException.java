package com.caiofeiria.planit.exceptions.nocontent;

public class TarefaNoContentException extends NoContentException{
	
	public TarefaNoContentException() {
		super("Tarefa sem conteúdo");
	}
}
