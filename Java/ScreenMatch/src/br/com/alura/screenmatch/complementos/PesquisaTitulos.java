package br.com.alura.screenmatch.complementos;

import br.com.alura.screenmatch.excecao.ErroDeCoversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PesquisaTitulos {
    Scanner leitura = new Scanner(System.in);
    public static List<Titulo> listaPesquisados = new ArrayList<>();

    public List<Titulo> getListaPesquisados() {
        return listaPesquisados;
    }

    public void buscaFilme (String servidor) {

        Gson gson = new GsonBuilder()
                //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        String nomeFilme;
        do {
            System.out.println("\n==================== PesquisaTitulos de filmes e séries ==========================");
            System.out.print("\nDigite o nome do filme ou 0 para finalizar: ");
            nomeFilme = leitura.nextLine();

            if (nomeFilme.equalsIgnoreCase("0")){
                System.out.println("\nSaindo da busca");
            }else {
                try {
                    String endereco = servidor + nomeFilme.replace(" ", "+");
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(endereco))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String json = response.body();

                    TituloJson meuTituloJson = gson.fromJson(json, TituloJson.class);
                    Titulo meuTituloFinal = new Titulo(meuTituloJson);

                    System.out.println("Título encontrado: " + meuTituloFinal);
                    listaPesquisados.add(meuTituloFinal);

                } catch (NumberFormatException erro) {
                    System.out.println("\nOCORREU UM ERRO NA CONVERSÃO DOS DADOS!");
                    System.out.println("Mensagem de erro: " + erro.getMessage());

                } catch (IllegalArgumentException erro) {
                    System.out.println("\nOCORREU UM ERRO NA CRIAÇÃO DA URL!");
                    System.out.println("Mensagem de erro: " + erro.getMessage());

                } catch (ErroDeCoversaoDeAnoException erro) {
                    System.out.println("\nOCORREU UM ERRO NOS DADOS RECEBIDOS, ANO INVALIDO!");
                    System.out.println("Mensagem de erro: " + erro.getMessage());

                } catch (NullPointerException erro) {
                    System.out.println("\nOCORREU UM ERRO NA PESQUISA!");
                    System.out.println("Dados recebidos imcompletos");
                    System.out.println("Favor verifique se sua KEY OMDB é valida");
                    System.out.println("\nMensagem de erro: " + erro.getMessage());

                } catch (IOException | InterruptedException erro) {
                    System.out.println("\nOcorreu um erro de RuntimeException");
                    throw new RuntimeException(erro);
                }
            }
        }while (!nomeFilme.equalsIgnoreCase("0"));
    }

    public void listaPesquisados(){
        if (listaPesquisados.isEmpty()) {
            System.out.println("\nLista vazia, favor faça uma pesquisa antes de usar essa função!");

        } else {
            System.out.println("\nLista de filmes pesquisados:\n");

            for (Titulo item : listaPesquisados) {
                System.out.println(item);
            }
        }
        System.out.println("\nPressione Enter para continuar...");
        leitura.nextLine();
    }
}
