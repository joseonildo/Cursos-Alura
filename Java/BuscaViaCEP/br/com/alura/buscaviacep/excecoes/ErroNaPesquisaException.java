package br.com.alura.buscaviacep.excecoes;

public class ErroNaPesquisaException extends RuntimeException {
    private final String mensagem;

    public ErroNaPesquisaException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return this.mensagem;
    }
}
