package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.complementos.GeradorDeArquivo;
import br.com.alura.screenmatch.complementos.PesquisaTitulos;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principal que inicia o aplicativo de busca de filmes e séries.
 * O usuário pode buscar títulos, visualizar resultados, gravar em arquivos e gerenciar a configuração da chave da API.
 */
public class AppBuscaFilmes {

    /**
     * Método principal para execução do aplicativo.
     * @param args Argumentos de linha de comando (não utilizados).
     * @throws IOException Se ocorrer erro na leitura/gravação de arquivos.
     */
    public static void main(String[] args) throws IOException {

        // Inicializando objetos necessários
        Scanner leitura = new Scanner(System.in); // Scanner para ler entradas do usuário
        PesquisaTitulos pesquisa = new PesquisaTitulos(); // Objeto para realizar a pesquisa de filmes e séries
        GeradorDeArquivo arquivo = new GeradorDeArquivo(); // Objeto para gerar e gravar arquivos de saída

        // Realiza o cadastro da chave da API do OMDB
        pesquisa.cadastraKey();

        // Variável para controlar o menu e as opções do usuário
        String opcao = "";

        // Cabeçalho do aplicativo
        System.out.println("\n=================== APP - BUSCA DE FILMES E SERIES =========================");

        // Laço de repetição para mostrar o menu até que o usuário decida sair
        while (!opcao.equalsIgnoreCase("0")) {

            // Exibição do menu principal com opções de interação
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
                    """, pesquisa.getkeyOmdb()); // Exibe a chave atual do servidor OMDB no menu

            // Solicita que o usuário escolha uma opção
            System.out.print("Digite sua opção: ");
            opcao = leitura.nextLine(); // Lê a entrada do usuário

            // Executa a ação correspondente à opção selecionada
            switch (opcao) {
                case "1" -> pesquisa.cadastraKey(); // Troca a chave de acesso à API
                case "2" -> pesquisa.buscaFilme(); // Realiza a busca de filmes ou séries
                case "3" -> pesquisa.listarPesquisados(); // Exibe os filmes e séries pesquisados
                case "4" -> arquivo.gravarArquivo(1); // Grava os títulos pesquisados em um arquivo .TXT
                case "5" -> arquivo.gravarArquivo(2); // Converte e grava os títulos em um arquivo .JSON
                case "0" -> System.out.println("\nSaindo do sistema..."); // Encerra o aplicativo
                default -> System.out.println("Opção inválida!"); // Caso o usuário insira uma opção inválida
            }
        }

        // Mensagem de agradecimento ao usuário ao encerrar o aplicativo
        System.out.println("Obrigado por utilizar o APP - Busca de Filmes e Séries");

        // Exibe a lista final de títulos pesquisados após o encerramento
        pesquisa.listarPesquisados();
    }
}
