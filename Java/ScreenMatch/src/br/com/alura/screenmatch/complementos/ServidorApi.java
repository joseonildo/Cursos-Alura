package br.com.alura.screenmatch.complementos;

import java.io.IOException; // Para tratar erros de entrada/saída
import java.net.URI; // Manipulação de URIs
import java.net.http.HttpClient; // Cliente HTTP para requisições
import java.net.http.HttpRequest; // Representação de uma requisição HTTP
import java.net.http.HttpResponse; // Representação de uma resposta HTTP
import java.util.Scanner; // Entrada de dados do usuário

/**
 * Classe responsável por configurar e testar a comunicação com a API OMDB.
 */
public class ServidorApi {

    // Variável para armazenar a chave da API OMDB
    private static String keyOmdb;

    // Variável para armazenar a URL base do servidor OMDB
    private static String urlServidor;

    // Mensagem de erro para chaves inválidas
    private final String erroKey = "ERRO: Key inválida! Digite novamente sua Key com 8 dígitos!";

    // Scanner para entrada de dados do usuário
    public static Scanner leitura = new Scanner(System.in);

    /**
     * Método responsável por cadastrar a chave da API OMDB.
     * Garante que a chave é válida antes de continuar.
     */
    public void cadastraKey() {
        keyOmdb = ""; // Inicializa a chave como vazia

        do {
            System.out.println("\n=================== Configuração do servidor de busca =========================");
            System.out.println("Favor informe abaixo sua Key do servidor OMDB API para buscar os filmes\n");
            System.out.print("Digite sua key: ");
            keyOmdb = leitura.nextLine(); // Recebe a chave digitada pelo usuário

            // Verifica se a chave é inválida (vazia ou com tamanho diferente de 8)
            if (keyOmdb.isBlank() || keyOmdb.length() != 8) {
                System.out.println(erroKey); // Mensagem de erro
                keyOmdb = ""; // Reseta a chave
            } else {
                // Define a URL base utilizando a chave fornecida
                urlServidor = "https://www.omdbapi.com/?apikey=" + keyOmdb + "&t=";

                // Testa se a chave funciona corretamente no servidor
                testaKeyServidor();
            }
        } while (keyOmdb.isEmpty()); // Repete até que uma chave válida seja cadastrada
    }

    /**
     * Retorna a chave da API OMDB.
     *
     * @return Chave cadastrada.
     */
    public String getkeyOmdb() {
        return keyOmdb;
    }

    /**
     * Retorna a URL base do servidor OMDB.
     *
     * @return URL base configurada.
     */
    public String getUrlServidor() {
        return urlServidor;
    }

    /**
     * Método para testar a validade da chave configurada no servidor OMDB.
     * Envia uma requisição de teste e verifica o código de status da resposta.
     */
    public void testaKeyServidor() {
        try {
            // Cria a URL de teste com um título genérico ("teste")
            String endereco = urlServidor + "teste";

            // Configura o cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Cria a requisição HTTP para o endereço de teste
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            // Envia a requisição e recebe a resposta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se o código de status não é 200 (requisição bem-sucedida)
            if (response.statusCode() != 200) {
                keyOmdb = ""; // Reseta a chave
                System.out.println(erroKey); // Mensagem de erro
                System.out.println("Status Code: " + response.statusCode()); // Exibe o código de status
            }

        } catch (IOException | InterruptedException erro) {
            // Trata possíveis erros de entrada/saída ou interrupção
            System.out.println("Houve um erro de IOException ou InterruptedException");
            keyOmdb = ""; // Reseta a chave em caso de erro
        }
    }
}
