package com.br.delogic.enums;

public enum ErrorMessages {

    NOT_FOUND("%s não foi encontrado."),
    ID_NULL("O ID não pode ser nulo."),
    INVALID_REQUEST("Requisição inválida."),
    ACCESS_DENIED("Acesso negado."),
    INTERNAL_SERVER_ERROR("Erro interno do servidor.");

    private final String template;

    ErrorMessages(String template) {
        this.template = template;
    }

    public String getMessage(Object... args) {
        return String.format(template, args);
    }
}
