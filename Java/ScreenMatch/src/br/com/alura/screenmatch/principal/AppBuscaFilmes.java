package br.com.alura.screenmatch.principal;
import br.com.alura.screenmatch.complementos.GeradorDeArquivo;
import br.com.alura.screenmatch.complementos.PesquisaTitulos;
import br.com.alura.screenmatch.complementos.ServidorApi;

import java.io.IOException;
import java.util.Scanner;

public class AppBuscaFilmes {
    public static void main(String[] args) throws IOException {
        Scanner leitura = new Scanner(System.in);
        ServidorApi servidor = new ServidorApi();
        PesquisaTitulos pesquisa = new PesquisaTitulos();

        String opcao = "";
        String urlServidor;
        System.out.println("\n=================== APP - BUSCA DE FILMES E SERIES =========================");
        urlServidor = servidor.cadastraKey();

        while (!opcao.equalsIgnoreCase("0")) {
            System.out.printf("""
                     \n======================== MENU PRINCIPAL =============================="
                     Digite uma das seguintes opçoes:
                     
                     1 - Trocar a key do servidor OMDB. Atual: %s
                     2 - Busca filmes ou Séries e amazena numa lista
                     3 - Exibe a lista de Titulos pesquisados
                     4 - Grava a lista pesquisada num aquivo .TXT
                     5 - Converte e grava a lista pesquisada num arquivo .JSON
                     
                     0 - Sair e encerrar o APP.
                     ===================== DESENVOLVIDO POR JOSÉ ONILDO =====================
                    """, servidor.getkeyOmdb());
            System.out.print("Digite sua opção: ");

            opcao = leitura.nextLine();
            switch (opcao) {
                case "1" -> urlServidor = servidor.cadastraKey();
                case "2" -> pesquisa.buscaFilme(urlServidor);
                case "3" -> pesquisa.listaPesquisados();
                case "4" -> gerarArquivo(1);
                case "5" -> gerarArquivo(2);
                case "0" -> System.out.println("\nSaindo do sistema...");
                default ->  System.out.println("Opção inválida!");
            }
        }
        System.out.println("Obrigado por utilizar o APP - Busca de Filmes e Séries");
        pesquisa.listaPesquisados();
    }
    public static void gerarArquivo(int tipo) throws IOException {
        GeradorDeArquivo meuArquivo = new GeradorDeArquivo();
        meuArquivo.gravarArquivo(tipo);
    }
}
