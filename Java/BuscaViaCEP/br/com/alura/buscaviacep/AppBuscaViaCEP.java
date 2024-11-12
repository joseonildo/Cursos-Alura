package br.com.alura.buscaviacep;
import br.com.alura.buscaviacep.complementos.GeradorDeArquivo;
import br.com.alura.buscaviacep.principal.PesquisarCEP;

import java.io.IOException;
import java.util.Scanner;

public class AppBuscaViaCEP {
    public static void main(String[] args) throws IOException {
        Scanner leitura = new Scanner(System.in);
        PesquisarCEP pesquisarCEP = new PesquisarCEP();

        String opcao = "";
        System.out.println("\n=============== APP - BuscaViaCEP - Busca de Endereços pelo CEP =====================");

        while (!opcao.equalsIgnoreCase("0")) {
            System.out.print("""
                     \n======================== MENU PRINCIPAL =============================="
                     Digite uma das seguintes opçoes:
                     
                     1 - Buscar endereço e amazenar numa lista
                     2 - Exibe a lista de Endereços pesquisados
                     3 - Grava a lista pesquisada num aquivo .TXT
                     4 - Converte e grava a lista pesquisada num arquivo .JSON
                     
                     0 - Sair e encerrar o APP.
                     ===================== DESENVOLVIDO POR JOSÉ ONILDO =====================
                    """);
            System.out.print("Digite sua opção: ");

            opcao = leitura.nextLine();
            switch (opcao) {
                case "1" -> pesquisarCEP.realizarBusca();
                case "2" -> pesquisarCEP.listaPesquisados();
                case "3" -> gerarArquivo(1);
                case "4" -> gerarArquivo(2);
                case "0" -> System.out.println("\nSaindo do sistema...");
                default ->  System.out.println("Opção inválida!");
            }
        }
        System.out.println("Obrigado por utilizar o APP - Busca de Filmes e Séries");
        pesquisarCEP.listaPesquisados();
    }
    public static void gerarArquivo(int tipo) throws IOException {
        GeradorDeArquivo meuArquivo = new GeradorDeArquivo();
        meuArquivo.gravarArquivo(tipo);
    }
}
