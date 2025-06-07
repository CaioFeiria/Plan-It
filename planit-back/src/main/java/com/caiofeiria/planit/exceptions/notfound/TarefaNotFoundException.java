package com.caiofeiria.planit.exceptions.notfound;

public class TarefaNotFoundException extends NotFoundException{
	
	public TarefaNotFoundException() {
		super("Tarefa nâo encontrada.");
	}
}
