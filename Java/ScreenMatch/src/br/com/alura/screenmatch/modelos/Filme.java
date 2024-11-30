package br.com.alura.screenmatch.modelos;

/**
 * Classe que representa um filme.
 *
 * A classe `Filme` herda a funcionalidade básica de `Titulo`, especializando o comportamento
 * para títulos do tipo "Filme". Inclui um método específico para exibição da ficha técnica.
 */
public class Filme extends Titulo {

    /**
     * Construtor da classe Filme.
     *
     * @param tipo Tipo do título (geralmente definido como "Filme").
     * @param nome Nome do filme.
     * @param anoDeLancamento Ano de lançamento do filme.
     * @param duracaoEmMinutos Duração do filme em minutos.
     */
    public Filme(String tipo, String nome, String anoDeLancamento, String duracaoEmMinutos) {
        // Chama o construtor da classe base (Titulo) para inicializar os atributos herdados
        super(tipo, nome, anoDeLancamento, duracaoEmMinutos);

        // Define explicitamente o tipo como "Filme" (sobrescrevendo o valor passado como parâmetro)
        super.setTipo("Filme");
    }

    /**
     * Exibe a ficha técnica do filme.
     *
     * Este método imprime as informações detalhadas do filme formatadas de maneira legível.
     */
    public void exibeFichaTecnica() {
        System.out.println(); // Linha em branco para separar visualmente a saída
        System.out.println("****************************************************");
        System.out.println();
        System.out.printf("""
                Nome do titulo:      %s
                Lançamento:          %s
                Duração em minutos   %d
                Incluído no plano:   %s
                Quantidade de notas: %d
                Nota final:          %.1f
                """,

                // Usa os métodos acessores da classe base para obter as informações
                super.getNome(),       // Nome do filme
                super.getLancamento(), // Ano de lançamento
                super.getDuracao(),    // Duração em minutos
                super.getNoPlano(),    // Indica se está incluído em um plano
                super.getQtdNotas(),   // Quantidade de avaliações recebidas
                super.getNotaFinal()   // Nota média final
        );
        System.out.println(); // Linha em branco
        System.out.println("****************************************************");
    }
}
