package br.com.alura.screenmatch.modelos;

/**
 * Classe que representa uma série.
 *
 * A classe `Serie` estende a classe base `Titulo`, especializando o comportamento para títulos do tipo "Série".
 * Adiciona informações específicas, como temporadas, episódios e duração total.
 */
public class Serie extends Titulo {

    // Atributos específicos para séries
    private int quantidadeDeTemporadas;  // Quantidade total de temporadas da série
    private int episodiosPorTemporada;   // Número de episódios em cada temporada
    private int duracaoPorEpisodio;      // Duração média de cada episódio (em minutos)
    private int duracaoTotal;            // Duração total da série (calculada automaticamente)

    /**
     * Construtor da classe Serie.
     *
     * @param tipo Tipo do título (geralmente definido como "Série").
     * @param nome Nome da série.
     * @param anoDeLancamento Ano de lançamento da série.
     * @param quantidadeDeTemporadas Número de temporadas da série.
     * @param episodiosPorTemporada Número de episódios por temporada.
     * @param duracaoEmMinutos Duração média dos episódios, passado como string (herdado da classe Titulo).
     */
    public Serie(String tipo, String nome, String anoDeLancamento, int quantidadeDeTemporadas, int episodiosPorTemporada, String duracaoEmMinutos) {
        // Inicializa os atributos herdados com o construtor da classe base
        super(tipo, nome, anoDeLancamento, duracaoEmMinutos);
        this.setQuantidadeDeTemporadas(quantidadeDeTemporadas);  // Define o número de temporadas
        this.setEpisodiosPorTemporada(episodiosPorTemporada);    // Define o número de episódios por temporada
        this.setDuracaoPorEpisodio(Integer.parseInt(duracaoEmMinutos)); // Define a duração de cada episódio
        super.setTipo("Serie");  // Sobrescreve o tipo como "Série"
    }

    // Métodos Getters e Setters para os atributos específicos
    public int getQuantidadeDeTemporadas() {
        return quantidadeDeTemporadas;
    }

    public void setQuantidadeDeTemporadas(int quantidadeDeTemporadas) {
        this.quantidadeDeTemporadas = quantidadeDeTemporadas;
    }

    public int getEpisodiosPorTemporada() {
        return episodiosPorTemporada;
    }

    public void setEpisodiosPorTemporada(int episodiosPorTemporada) {
        this.episodiosPorTemporada = episodiosPorTemporada;
    }

    public int getDuracaoPorEpisodio() {
        return duracaoPorEpisodio;
    }

    /**
     * Define a duração de cada episódio e atualiza automaticamente a duração total da série.
     *
     * @param duracaoPorEpisodio Duração média de cada episódio (em minutos).
     */
    public void setDuracaoPorEpisodio(int duracaoPorEpisodio) {
        this.duracaoPorEpisodio = duracaoPorEpisodio;
        // Calcula automaticamente a duração total da série (em minutos)
        this.duracaoTotal = this.quantidadeDeTemporadas * this.episodiosPorTemporada * this.duracaoPorEpisodio;
    }

    public int getDuracaoTotal() {
        return duracaoTotal;
    }

    /**
     * Exibe a ficha técnica da série com todas as informações formatadas.
     */
    public void exibeFichaTecnica() {
        System.out.println(); // Linha em branco para separar visualmente a saída
        System.out.println("****************************************************");
        System.out.println();
        System.out.printf("""
                Nome do titulo:         %s
                Lançamento:             %s
                Temporadas:             %d
                Episódios (temporada):  %d
                Duração por episódio:   %d minutos
                Duração para maratonar: %d minutos
                Incluído no plano:      %s
                Quantidade de notas:    %d
                Nota final:             %.1f
                """,

                // Usa métodos herdados e específicos para obter os valores
                super.getNome(),                  // Nome da série
                super.getLancamento(),            // Ano de lançamento
                this.getQuantidadeDeTemporadas(), // Número de temporadas
                this.getEpisodiosPorTemporada(),  // Episódios por temporada
                this.getDuracaoPorEpisodio(),     // Duração média por episódio
                this.getDuracaoTotal(),           // Duração total para maratonar a série
                super.getNoPlano(),               // Indica se está incluída no plano
                super.getQtdNotas(),              // Quantidade de avaliações
                super.getNotaFinal()              // Nota média final
        );
        System.out.println(); // Linha em branco
        System.out.println("****************************************************");
    }
}
