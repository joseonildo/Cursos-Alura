package br.com.alura.screenmatch.excecao;

/**
 * Classe de exceção personalizada para tratar erros de conversão de ano.
 *
 * Essa exceção é utilizada para identificar situações onde há problemas ao
 * converter dados relacionados ao ano, como formato incorreto ou dados inválidos.
 */
public class ErroDeCoversaoDeAnoException extends RuntimeException {

    // Mensagem de erro personalizada que descreve o problema
    private final String mensagem;

    /**
     * Construtor da exceção que recebe uma mensagem de erro personalizada.
     *
     * @param mensagem Descrição detalhada do erro ocorrido.
     */
    public ErroDeCoversaoDeAnoException(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Sobrescreve o método `getMessage` da classe RuntimeException.
     * Retorna a mensagem personalizada da exceção.
     *
     * @return Mensagem de erro associada à exceção.
     */
    @Override
    public String getMessage() {
        return this.mensagem;
    }
}

