package br.com.alura.screenmatch.modelos;

import br.com.alura.screenmatch.calculos.Classificavel;
//import br.com.alura.screenmatch.excecao.ErroDeCoversaoDeAnoException;
//import com.google.gson.annotations.SerializedName;

public class Titulo implements Classificavel {
    private String tipo;
    private String nome;
    private String lancamento;
    private String duracao;
    private String noPlano;
    private String notaFinal;
    private String qtdNotas;


    public Titulo(String tipo, String nome, String lancamento, String duracao) {
        this.setTipo(tipo);
        this.setNome(nome);
        this.setLancamento(lancamento);
        this.setDuracao(duracao);
    }

    public Titulo(TituloJson meuTituloOmdb) {
        if (meuTituloOmdb.Type().equalsIgnoreCase("movie")) {
            this.tipo = "Filme";
        } else if (meuTituloOmdb.Type().equalsIgnoreCase("series")) {
            this.tipo = "Serie";
        } else {
            this.tipo = meuTituloOmdb.Type();
        }
        this.nome = meuTituloOmdb.Title();
        this.lancamento = meuTituloOmdb.Year();
        this.duracao = meuTituloOmdb.Runtime();
        this.notaFinal = meuTituloOmdb.imdbRating();
        this.qtdNotas = meuTituloOmdb.imdbVotes();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo.equalsIgnoreCase("movie")) {
            this.tipo = "Filme";
        } else if (tipo.equalsIgnoreCase("series")) {
           this.tipo = "Serie";
        } else {
            this.tipo = tipo;
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

    public String getDuracao() {
        return this.duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getNoPlano() {
        return this.noPlano;
    }

    public void setNoPlano(String noPlano) {
        if (noPlano.equalsIgnoreCase("sim")) {
            this.noPlano = "Sim, pode assistir a vontade";
        } else {
            this.noPlano = "Não, alugar por R$25,00";
        }

    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getNotaFinal() {
        return this.notaFinal;
    }

    public String getQtdNotas() {
        return this.qtdNotas;
    }
//
//    public void incluiNota(String nota) {
//
//        ++this.totalDeAvalicoes;
//        //this.setNotaFinal(this.somaDeAvalicoes / (double)this.totalDeAvalicoes);
//    }

    public void avalia() {
        System.out.println();/*

        if (Integer.parseInt(this.getAnoDeLancamento()) >= 2022) {
            if (this.getNotaFinal() > 6.0) {
                System.out.println(this.getNome() + " é recente e os clientes estão curtindo");
            } else {
                System.out.println(this.getNome() + " é recente mas não é muito bom");
            }
        } else if (this.getNotaFinal() > 6.0) {
            System.out.println(this.getNome() + " é retrô porém os clientes estão curtindo");
        } else {
            System.out.println(this.getNome() + " é retrô e não é muito boa");
        }*/

    }

    public int getClassificacao() {
        return 2;
        //(int)this.getNotaFinal() / 2;
    }

    public String toString() {
        return this.getTipo() + ": " + this.getNome() + ", (" + this.getLancamento() + "), " +
                getDuracao() + ", Avaliações: " + getQtdNotas() + ", Nota final: " + getNotaFinal();
    }
}