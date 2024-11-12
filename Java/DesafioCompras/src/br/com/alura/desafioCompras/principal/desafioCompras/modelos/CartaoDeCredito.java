package br.com.alura.desafioCompras.principal.desafioCompras.modelos;

import java.util.ArrayList;
import java.util.List;

public class CartaoDeCredito {
    private double limite;
    private double saldo;
    private final List<Produtos> compras = new ArrayList<>();

    public void criaCartao(double limite) {
        this.limite = limite;
        this.saldo = limite;
        System.out.println();
        System.out.println("Cartão criado com sucesso");
        System.out.println("Limite total: R$ " + this.limite + "0");
    }

    public void lancaCompra(Produtos compras) {
        if (this.saldo >= compras.getValor()) {
            this.saldo -= compras.getValor();
            this.compras.add(compras);
            System.out.println();
            System.out.println("COMPRA REALIZADA COM SUCESSO");
            System.out.println();
            System.out.println("Produto: " + compras.getNome());
            System.out.println("Valor: " + compras.getValor());
            System.out.println("Limite ainda disponivel: R$ " + this.getSaldo());
            System.out.println();
        } else {
            System.out.println();
            System.out.println("ERRO! COMPRA NÃO REALIZADA");
            System.out.println("Limite insuficiente para realizar a compra!");
            System.out.println("Limite disponivel: R$ " + this.getSaldo());
            System.out.println();
        }
    }

    public void aumentarLimite (double limite) {
        this.limite += limite;
        this.saldo += limite;
        System.out.println("Limite aumentado em R$ " + limite + "0");
        System.out.println("Limite total do cartão: " + this.limite + "0");
        System.out.println("Saldo disponivel para compras: " + this.saldo + "0");
    }

    public double getSaldo() {
        return this.saldo;
    }

    public List<Produtos> getCompras() {
        return compras;
    }
}
