package br.com.alura.screenmatch.modelos;

public class Serie extends Titulo {
    private int quantidadeDeTemporadas;
    private int episodiosPorTemporada;
    private int duracaoPorEpisodio;
    private int duracaoTotal;


    public Serie(String tipo, String nome, String anoDeLancamento, int quantidadeDeTemporadas, int episodiosPorTemporada, String duracaoEmMinutos) {
        super(tipo, nome, anoDeLancamento, duracaoEmMinutos);
        this.setQuantidadeDeTemporadas(quantidadeDeTemporadas);
        this.setEpisodiosPorTemporada(episodiosPorTemporada);
        this.setDuracaoPorEpisodio(duracaoPorEpisodio);
        super.setTipo("Serie");
    }

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

    public void setDuracaoPorEpisodio(int duracaoPorEpisodio) {
        this.duracaoPorEpisodio = duracaoPorEpisodio;
        this.duracaoTotal = (this.quantidadeDeTemporadas * this.episodiosPorTemporada * this.duracaoPorEpisodio);
    }

    public int getDuracaoTotal() {
        return duracaoTotal;
    }

    public void exibeFichaTecnica() {
        System.out.println();
        System.out.println("****************************************************");
        System.out.println();
        System.out.printf("""                
                Nome do titulo:         %s
                Lançamento:             %s
                Temporadas:             %d
                Episodios (temporada):  %d
                Duracao por episodio:   %d minutos
                Duração para maratonar: %d minutos
                Incluido no plano:      %s
                Quantidade de notas:    %d
                Nota final:             %.1f
                """,
                super.getNome(),
                super.getLancamento(),
                this.getQuantidadeDeTemporadas(),
                this.getEpisodiosPorTemporada(),
                this.getDuracaoPorEpisodio(),
                super.getDuracao(),
                super.getNoPlano(),
                super.getQtdNotas(),
                super.getNotaFinal());
        System.out.println();
        System.out.println("****************************************************");
    }
}
