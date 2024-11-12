package br.com.alura.desafioCompras.principal.desafioCompras.principal;

import br.com.alura.desafioCompras.principal.desafioCompras.modelos.CartaoDeCredito;
import br.com.alura.desafioCompras.principal.desafioCompras.modelos.Produtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loja {

    public static Scanner leitura = new Scanner(System.in);
    public static CartaoDeCredito meuCartao = new CartaoDeCredito();

    public static Produtos produto1 = new Produtos(1, "Camisa Social", 150.00);
    public static Produtos produto2 = new Produtos(2, "Camisa Regata", 100.00);
    public static Produtos produto3 = new Produtos(3, "Calça Social", 100.00);
    public static Produtos produto4 = new Produtos(4, "Calça Jeans", 80.00);
    public static Produtos produto5 = new Produtos(5, "Sapato Social", 200.00);
    public static Produtos produto6 = new Produtos(6, "Tenis Esportivo", 300.00);
    public static Produtos produto7 = new Produtos(7, "Tenis Comum", 100.00);
    public static Produtos produto8 = new Produtos(8, "Gravata", 50.00);
    public static Produtos produto9 = new Produtos(9, "Cinto", 30.00);
    public static Produtos produto10 = new Produtos(10, "Meias", 20.00);

    public static List<Produtos> listaProdutos = new ArrayList<>();

    public static void fazerCompras(){
        System.out.println();
        System.out.println("############ FAZENDO COMPRAS ############");
        int opcao=1;
        while(opcao != 0) {
            System.out.println("###### Lista de produtos (Conforme saldo) ######");
            int qtdProdutos=0;
            for (Produtos item : listaProdutos) {
                if (item.getValor() <= meuCartao.getSaldo()) {
                    System.out.println(item);
                    qtdProdutos++;
                }
            }
            if (qtdProdutos == 0) {
                System.out.println();
                System.out.println("Nenhum produto pode ser comprado com o saldo disponível");
                System.out.println("Aumente seu limite para realizar mais compras!");
                System.out.println();
                break;
            }
            System.out.println();
            System.out.print("Digite o número do item ou '0' para terminar: ");
            opcao = leitura.nextInt();

            switch (opcao) {
                case 1 -> meuCartao.lancaCompra(listaProdutos.get(0));
                case 2 -> meuCartao.lancaCompra(listaProdutos.get(1));
                case 3 -> meuCartao.lancaCompra(listaProdutos.get(2));
                case 4 -> meuCartao.lancaCompra(listaProdutos.get(3));
                case 5 -> meuCartao.lancaCompra(listaProdutos.get(4));
                case 6 -> meuCartao.lancaCompra(listaProdutos.get(5));
                case 7 -> meuCartao.lancaCompra(listaProdutos.get(6));
                case 8 -> meuCartao.lancaCompra(listaProdutos.get(7));
                case 9 -> meuCartao.lancaCompra(listaProdutos.get(8));
                case 10 -> meuCartao.lancaCompra(listaProdutos.get(9));
                default -> {
                    if (opcao != 0) System.out.println("ERRO! OPÇÂO " + opcao + " INVÁLIDA");
                }
            }
        }
    }

    public static void alteraLimite() {
        System.out.println();
        System.out.println("############ Aumenta limite ############");

            System.out.println();
            System.out.print("Digite o valor: ");
            double limite = leitura.nextDouble();
            meuCartao.aumentarLimite(limite);
    }

    public static void main(String[] args) {

        System.out.println("############ LOJAS PERAMBULANDO ############");
        System.out.println("############ CADASTRO DE PRODUTOS ############");
        System.out.println();

        listaProdutos.add(produto1);
        listaProdutos.add(produto2);
        listaProdutos.add(produto3);
        listaProdutos.add(produto4);
        listaProdutos.add(produto5);
        listaProdutos.add(produto6);
        listaProdutos.add(produto7);
        listaProdutos.add(produto8);
        listaProdutos.add(produto9);
        listaProdutos.add(produto10);

        for (Produtos item : listaProdutos) {
            System.out.println(item);
        }
        System.out.println("\nbr.com.alura.desafioCompras.modelos.Produtos cadastrados com sucesso\n");



        System.out.println("############ CRIANDO SEU CARTÂO ############");
        System.out.println();
        System.out.print("Digite o limite do cartão: ");
        double limite = leitura.nextDouble();
        meuCartao.criaCartao(limite);

        System.out.println();
        System.out.println("############ MENU PRINCIPAL ############");
        int opcao;
        do {
            System.out.println();
            System.out.println("""
                    Digite uma das seguintes opções
                    
                    1 - Exibe lista de produtos
                    2 - Realizar compras
                    3 - Listar compras já realizadas
                    4 - Aumenta o limite do cartão
                    
                    0 - Sair e encerrar o sistema
                    """);

            System.out.println();
            System.out.print("Digite uma opção: ");
            opcao = leitura.nextInt();
            if (opcao == 1) {
                System.out.println("############ LISTA DE PRODUTOS ############");
                for (Produtos item : listaProdutos) {
                    System.out.println(item);
                }
                System.out.println();
            } else if (opcao == 2) {
                fazerCompras();
            } else if (opcao == 3) {
                System.out.println("############ COMPRAS REALIZADAS ############");
                System.out.println();
                for (Produtos compras : meuCartao.getCompras()) {
                    System.out.println(compras);
                }
                System.out.println();
            } else if (opcao == 4) {
                alteraLimite();
            } else if (opcao != 0){
                System.out.println("ERRO! OPÇÂO " + opcao + " INVÁLIDA");
            }

        } while (opcao != 0);


        System.out.println();
        System.out.println("############ LOJAS PERAMBULANDO ############");
        System.out.println("############ SAINDO DO SISTEMA ############");
        System.out.println();
        System.out.println("############ TODAS AS COMPRAS REALIZADAS ############");
        System.out.println();
        for (Produtos compras : meuCartao.getCompras()) {
            System.out.println(compras);
        }
        System.out.println();
        System.out.println("############ ATÉ A PROXIMA COMPRA ############");

    }
}
