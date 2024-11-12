package br.com.alura.screenmatch.calculos;

import br.com.alura.screenmatch.modelos.Serie;
import br.com.alura.screenmatch.modelos.Titulo;

public class Calculadora {
    private String titulos = "";
    private int tempoTotal;
    private int totalEpisodios;

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public String getTitulos() {
        return titulos;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public int getTotalEpisodios() {
        return totalEpisodios;
    }

    public void incluiTempo(Titulo titulo) {
        if (titulos.isBlank()) {
            this.titulos += titulo.toString();
        } else {
            this.titulos += ", " + titulo.toString();
        }
        this.tempoTotal += Integer.getInteger(titulo.getDuracao());
    }

    public void incluiEpisodios(Serie serie) {
        if (titulos.isBlank()) {
            this.titulos += serie.toString();
        } else {
            this.titulos += ", " + serie.toString();
        }
        this.totalEpisodios += serie.getEpisodiosPorTemporada() * serie.getQuantidadeDeTemporadas();
    }

    public void exibeTempoTotal(){
        System.out.println();
        System.out.println("Titulos selecionados: " + this.getTitulos());
        System.out.println("Tempo para maratonar: " + this.getTempoTotal() + " minutos");
    }

    public void exibeQuantidadeEpisodios(){
        System.out.println();
        System.out.println("Titulos selecionados: " + this.getTitulos());
        System.out.println("Quantidade de episodios: " + this.getTotalEpisodios());
    }

}
