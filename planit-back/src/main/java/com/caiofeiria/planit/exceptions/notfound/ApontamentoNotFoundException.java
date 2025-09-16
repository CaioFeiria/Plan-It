package com.caiofeiria.planit.exceptions.notfound;

public class ApontamentoNotFoundException extends RuntimeException {
    public ApontamentoNotFoundException() {
        super("Apontamento não encontrado.");
    }
}
