package br.com.alura.screenmatch.complementos;

// Importações necessárias
import br.com.alura.screenmatch.excecao.ErroDeCoversaoDeAnoException; // Exceção personalizada para erro de conversão de ano
import br.com.alura.screenmatch.modelos.Titulo; // Classe que representa um título de filme/série
import br.com.alura.screenmatch.modelos.TituloJson; // Classe intermediária para mapear o JSON da API
import com.google.gson.Gson; // Biblioteca para manipulação de JSON
import com.google.gson.GsonBuilder; // Permite configuração do Gson
import java.io.IOException; // Tratamento de exceções de entrada/saída
import java.net.URI; // Manipulação de URIs
import java.net.http.HttpClient; // Cliente HTTP para envio de requisições
import java.net.http.HttpRequest; // Representação de uma requisição HTTP
import java.net.http.HttpResponse; // Representação de uma resposta HTTP
import java.util.ArrayList; // Implementação da interface List
import java.util.List; // Interface para listas
import java.util.Scanner; // Entrada de dados do usuário

/**
 * Classe responsável por realizar a pesquisa de títulos (filmes/séries) utilizando uma API externa.
 * Armazena os títulos pesquisados em uma lista para operações posteriores.
 */
public class PesquisaTitulos {

    // Scanner para entrada de dados do usuário
    public static Scanner leitura = new Scanner(System.in);

    // Instância de servidor que contém informações e funcionalidades relacionadas à API
    public static ServidorApi servidor = new ServidorApi();

    // Lista de títulos pesquisados e armazenados
    public static List<Titulo> listaPesquisados = new ArrayList<>();

    /**
     * Retorna a lista de títulos pesquisados.
     *
     * @return Lista de objetos da classe Titulo.
     */
    public List<Titulo> getListaPesquisados() {
        return listaPesquisados;
    }

    /**
     * Registra a chave de acesso à API no servidor.
     */
    public void cadastraKey() {
        servidor.cadastraKey();
    }

    /**
     * Método principal para realizar a busca de filmes/séries.
     * Permite que o usuário pesquise por nome e armazena os resultados válidos.
     */
    public void buscaFilme() {
        // Configuração do Gson com saída formatada
        Gson gson = new GsonBuilder()
                .setPrettyPrinting() // Formata a saída JSON de forma legível
                .create();

        String nomeFilme; // Variável para armazenar o nome do filme digitado pelo usuário

        do {
            System.out.println("\n==================== Pesquisa de filmes e séries ==========================");
            System.out.print("\nDigite o nome do filme ou 0 para finalizar: ");
            nomeFilme = leitura.nextLine(); // Recebe o nome do filme

            // Verifica se o usuário deseja encerrar ou se a entrada é inválida
            if (nomeFilme.equalsIgnoreCase("0") || nomeFilme.isBlank()) {
                System.out.println("\nSaindo da busca");
                break; // Encerra o loop
            }

            try {
                // Constrói a URL da requisição substituindo espaços por "+"
                String endereco = servidor.getUrlServidor() + nomeFilme.replace(" ", "+");

                // Configura o cliente HTTP
                HttpClient client = HttpClient.newHttpClient();

                // Cria a requisição HTTP para o endereço configurado
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                // Envia a requisição e recebe a resposta como string
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body(); // Conteúdo do corpo da resposta

                // Converte o JSON para um objeto TituloJson
                TituloJson meuTituloJson = gson.fromJson(json, TituloJson.class);

                // Converte o TituloJson para um objeto Titulo
                Titulo meuTituloFinal = new Titulo(meuTituloJson);

                System.out.println("Título encontrado: " + meuTituloFinal);

                // Adiciona o título à lista de pesquisados
                listaPesquisados.add(meuTituloFinal);

            } catch (NumberFormatException erro) {
                // Trata erros de conversão numérica (como o ano inválido no JSON)
                System.out.println("\nOCORREU UM ERRO NA CONVERSÃO DOS DADOS!");
                System.out.println("Mensagem de erro: " + erro.getMessage());

            } catch (IllegalArgumentException erro) {
                // Trata erros relacionados à criação da URL
                System.out.println("\nOCORREU UM ERRO NA CRIAÇÃO DA URL!");
                System.out.println("Mensagem de erro: " + erro.getMessage());

            } catch (ErroDeCoversaoDeAnoException erro) {
                // Trata exceções específicas relacionadas ao ano inválido no JSON
                System.out.println("\nOCORREU UM ERRO NOS DADOS RECEBIDOS, ANO INVÁLIDO!");
                System.out.println("Mensagem de erro: " + erro.getMessage());

            } catch (NullPointerException erro) {
                // Trata erros relacionados a dados incompletos ou ausência de chave da API
                System.out.println("\nOCORREU UM ERRO NA PESQUISA!");
                System.out.println("Dados recebidos incompletos");
                System.out.println("Favor verifique se sua KEY OMDB é válida");
                System.out.println("\nMensagem de erro: " + erro.getMessage());

            } catch (IOException | InterruptedException erro) {
                // Trata erros de entrada/saída ou interrupção durante a requisição
                System.out.println("\nOcorreu um erro de RuntimeException");
                throw new RuntimeException(erro); // Relança a exceção como RuntimeException
            }

        } while (!nomeFilme.equalsIgnoreCase("0")); // Continua o loop enquanto o usuário não digitar "0"
    }

    /**
     * Exibe a lista de filmes pesquisados ou informa que está vazia.
     */
    public void listarPesquisados() {
        // Verifica se há itens na lista
        if (listaPesquisados.isEmpty()) {
            System.out.println("\nLista vazia, favor faça uma pesquisa antes de usar essa função!");
        } else {
            System.out.println("\nLista de filmes pesquisados:\n");

            // Itera sobre os títulos e os exibe
            for (Titulo item : listaPesquisados) {
                System.out.println(item);
            }
        }
        // Aguarda interação do usuário para continuar
        System.out.println("\nPressione Enter para continuar...");
        leitura.nextLine();
    }

    /**
     * Retorna a chave da API utilizada para pesquisas.
     *
     * @return A chave da API cadastrada.
     */
    public Object getkeyOmdb() {
        return servidor.getkeyOmdb();
    }
}

