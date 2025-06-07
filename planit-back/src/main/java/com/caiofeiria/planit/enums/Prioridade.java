package com.caiofeiria.planit.enums;

public enum Prioridade {
    BAIXO,
    MEDIO,
    ALTO,
    URGENTE;

    public static Prioridade fromCode(String code) {
        if (code == null) return null;
        try {
            return Prioridade.valueOf(code);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Código de prioridade inválido: " + code, ex);
        }
    }
}
