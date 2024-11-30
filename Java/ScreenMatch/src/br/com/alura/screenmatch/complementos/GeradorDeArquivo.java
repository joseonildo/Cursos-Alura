package br.com.alura.screenmatch.complementos;

// Importações necessárias para o funcionamento da classe
import br.com.alura.screenmatch.modelos.Titulo; // Classe representando títulos (provavelmente filmes, séries, etc.)
import com.google.gson.FieldNamingPolicy; // Define políticas de nomenclatura para serialização/deserialização JSON
import com.google.gson.Gson; // Biblioteca para manipulação de JSON
import com.google.gson.GsonBuilder; // Utilizada para configurar o comportamento do Gson
import java.io.File; // Manipulação de arquivos
import java.io.FileWriter; // Escrita em arquivos
import java.io.IOException; // Tratamento de exceções de entrada/saída
import java.util.List; // Manipulação de listas
import java.util.Scanner; // Entrada de dados pelo usuário

/**
 * Classe responsável por gerar arquivos com os dados obtidos em pesquisas.
 * Suporta geração de arquivos nos formatos `.txt` e `.json`.
 */
public class GeradorDeArquivo {

    // Scanner para entrada de dados do usuário
    private final Scanner leitura = new Scanner(System.in);

    // Instância do Gson configurada para usar notação UpperCamelCase e saída formatada (pretty print)
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE) // Campos seguem UpperCamelCase no JSON
            .setPrettyPrinting() // Gera JSON formatado e legível
            .create();

    /**
     * Método principal para gerar arquivos.
     * O formato é definido pelo parâmetro `tipo` (1 para `.txt`, outro valor para `.json`).
     *
     * @param tipo Tipo do arquivo a ser gerado (1 para `.txt`, outros valores para `.json`).
     * @throws IOException Se ocorrer um erro de entrada/saída.
     */
    public void gravarArquivo(int tipo) throws IOException {
        // Obtém a lista de títulos a partir de uma pesquisa
        var pesquisa = new PesquisaTitulos();
        var listaPesquisados = pesquisa.getListaPesquisados();

        System.out.println("\n======================== Gerador de arquivo ==============================");

        // Verifica se há títulos pesquisados
        if (listaPesquisados.isEmpty()) {
            System.out.println("\nLista vazia, favor faça uma pesquisa antes de usar essa função!");
            System.out.println("Operação cancelada!");
            aguardarEnter(); // Aguarda interação do usuário para continuar
            return; // Encerra a execução do método
        }

        // Obtém o nome do arquivo com base no tipo
        String nomeFinal = obterNomeArquivo(tipo);
        if (nomeFinal == null) return; // Encerra caso o nome do arquivo seja inválido

        // Gera o arquivo com os dados e exibe o conteúdo, se solicitado
        gerarArquivo(tipo, nomeFinal, listaPesquisados);
        exibirConteudoArquivo(nomeFinal);
    }

    /**
     * Obtém o nome do arquivo a ser gerado, garantindo que seja válido e possua a extensão correta.
     *
     * @param tipo Tipo do arquivo (1 para `.txt`, outro valor para `.json`).
     * @return O nome do arquivo validado ou `null` caso seja inválido.
     */
    private String obterNomeArquivo(int tipo) {
        String nomeArquivo;
        String extensao = tipo == 1 ? ".txt" : ".json"; // Define a extensão com base no tipo
        boolean nomeValido = false;

        // Loop para garantir que o nome do arquivo seja válido
        do {
            System.out.print("Digite o nome do arquivo: ");
            nomeArquivo = leitura.nextLine();

            // Verifica se o nome está vazio
            if (nomeArquivo.isBlank()) {
                System.out.println("Nome do arquivo inválido! Necessário digitar um nome.\n");
                continue; // Recomeça o loop para pedir o nome novamente
            }

            // Adiciona a extensão ao nome, caso necessário
            if (!nomeArquivo.endsWith(extensao)) {
                nomeArquivo += extensao;
            }

            // Verifica se o arquivo já existe
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists()) {
                System.out.println("\nArquivo " + nomeArquivo + " já existe.");
                System.out.print("Sobrescrever o arquivo existente? Sim ou Não: ");
                String opcao = leitura.nextLine();
                if (opcao.equalsIgnoreCase("sim") || opcao.equalsIgnoreCase("s")) {
                    nomeValido = true; // Permite sobrescrever o arquivo
                } else {
                    System.out.println("Digite outro nome para o arquivo.");
                }
            } else {
                nomeValido = true; // O arquivo não existe, nome é válido
            }
        } while (!nomeValido);

        return nomeArquivo;
    }

    /**
     * Gera o arquivo no formato especificado, gravando os dados da lista fornecida.
     *
     * @param tipo Tipo do arquivo (1 para `.txt`, outro valor para `.json`).
     * @param nomeFinal Nome final do arquivo a ser gerado.
     * @param listaPesquisados Lista de títulos a serem gravados.
     * @throws IOException Se ocorrer um erro de entrada/saída.
     */
    private void gerarArquivo(int tipo, String nomeFinal, List<Titulo> listaPesquisados) throws IOException {
        // Abre o arquivo para escrita, utilizando `try-with-resources` para garantir o fechamento
        try (FileWriter arquivo = new FileWriter(nomeFinal)) {
            if (tipo == 1) {
                // Grava cada título no arquivo `.txt`, linha por linha
                for (Titulo item : listaPesquisados) {
                    arquivo.write(item + "\n");
                }
            } else {
                // Serializa a lista completa como JSON e grava no arquivo
                arquivo.write(gson.toJson(listaPesquisados));
            }
        }
        System.out.println("\nArquivo " + nomeFinal + " gerado com sucesso!");
    }

    /**
     * Exibe o conteúdo do arquivo gerado, se o usuário optar por isso.
     *
     * @param nomeFinal Nome do arquivo gerado.
     */
    private void exibirConteudoArquivo(String nomeFinal) {
        System.out.print("Exibir o conteúdo do arquivo gravado? Sim ou Não: ");
        String resposta = leitura.nextLine();
        if (resposta.equalsIgnoreCase("sim") || resposta.equalsIgnoreCase("s")) {
            try (Scanner scanner = new Scanner(new File(nomeFinal))) {
                System.out.println("\nConteúdo do " + nomeFinal + ":");
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            }
            aguardarEnter();
        } else {
            System.out.println("\nOK! Continuando...");
        }
    }

    /**
     * Aguarda que o usuário pressione Enter para continuar.
     */
    private void aguardarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        leitura.nextLine();
    }
}