package br.com.alura.desafioCompras.principal.desafioCompras.modelos;

public class Produtos {
    private final int numero;
    private final String nome;
    private double valor;

    public Produtos(int numero, String nome, Double valor){
        this.numero = numero;
        this.nome = nome;
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Item " + getNumero() +
                " - " + getNome() +
                " - R$ " + getValor() + "0";
    }
}
