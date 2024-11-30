package br.com.alura.screenmatch.modelos;

/**
 * Classe que representa um título genérico (filme ou série).
 *
 * Essa classe serve como base para títulos e contém atributos e métodos comuns,
 * como tipo, nome, ano de lançamento, duração, se está incluído no plano, nota média e quantidade de avaliações.
 */
public class Titulo {

    // Atributos gerais do título
    private String tipo;         // Tipo do título (Filme, Série, etc.)
    private String nome;         // Nome do título
    private String lancamento;   // Ano de lançamento
    private String duracao;      // Duração (geralmente em minutos ou formato de texto, como "2h")
    private String noPlano;      // Indica se está incluído no plano de streaming
    private String notaFinal;    // Nota média do título
    private String qtdNotas;     // Quantidade de avaliações no IMDb ou similar

    /**
     * Construtor principal.
     *
     * @param tipo Tipo do título (Filme, Série, etc.).
     * @param nome Nome do título.
     * @param lancamento Ano de lançamento.
     * @param duracao Duração do título.
     */
    public Titulo(String tipo, String nome, String lancamento, String duracao) {
        this.setTipo(tipo);          // Define o tipo, com conversão automática
        this.setNome(nome);          // Define o nome
        this.setLancamento(lancamento); // Define o ano de lançamento
        this.setDuracao(duracao);    // Define a duração
    }

    /**
     * Construtor que inicializa o título com dados de um objeto `TituloJson`.
     *
     * @param meuTituloOmdb Objeto JSON convertido que contém informações do título.
     */
    public Titulo(TituloJson meuTituloOmdb) {
        // Define o tipo com base no valor recebido (conversão automática)
        if (meuTituloOmdb.Type().equalsIgnoreCase("movie")) {
            this.tipo = "Filme";
        } else if (meuTituloOmdb.Type().equalsIgnoreCase("series")) {
            this.tipo = "Serie";
        } else {
            this.tipo = meuTituloOmdb.Type(); // Caso seja um tipo não mapeado
        }

        // Inicializa os demais atributos com os dados do objeto JSON
        this.nome = meuTituloOmdb.Title();          // Nome do título
        this.lancamento = meuTituloOmdb.Year();     // Ano de lançamento
        this.duracao = meuTituloOmdb.Runtime();     // Duração do título
        this.notaFinal = meuTituloOmdb.imdbRating();// Nota final (IMDb)
        this.qtdNotas = meuTituloOmdb.imdbVotes();  // Quantidade de avaliações (IMDb)
    }

    // Métodos Getters e Setters para acessar e modificar os atributos

    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do título, convertendo automaticamente para "Filme" ou "Série", se necessário.
     *
     * @param tipo Tipo do título, geralmente recebido como "movie" ou "series".
     */
    public void setTipo(String tipo) {
        if (tipo.equalsIgnoreCase("movie")) {
            this.tipo = "Filme";
        } else if (tipo.equalsIgnoreCase("series")) {
            this.tipo = "Serie";
        } else {
            this.tipo = tipo; // Tipo genérico ou não mapeado
        }
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLancamento() {
        return this.lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getDuracao() {
        return this.duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getNoPlano() {
        return this.noPlano;
    }

    /**
     * Define se o título está incluído no plano de streaming, com mensagem correspondente.
     *
     * @param noPlano Valor recebido ("sim" ou "não").
     */
    public void setNoPlano(String noPlano) {
        if (noPlano.equalsIgnoreCase("sim")) {
            this.noPlano = "Sim, pode assistir à vontade";
        } else {
            this.noPlano = "Não, alugar por R$25,00";
        }
    }

    public String getNotaFinal() {
        return this.notaFinal;
    }

    public String getQtdNotas() {
        return this.qtdNotas;
    }

    /**
     * Método que retorna uma representação em texto do título.
     *
     * @return String com informações do título formatadas.
     */
    @Override
    public String toString() {
        return this.getTipo() + ": " + this.getNome() + ", (" + this.getLancamento() + "), " +
                getDuracao() + ", Avaliações: " + getQtdNotas() + ", Nota final: " + getNotaFinal();
    }
}
