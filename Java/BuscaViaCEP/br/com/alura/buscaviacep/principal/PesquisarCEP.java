package br.com.alura.buscaviacep.principal;

import br.com.alura.buscaviacep.excecoes.ErroNaPesquisaException;
import br.com.alura.buscaviacep.modelos.Endereco;
import br.com.alura.buscaviacep.modelos.EnderecoJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PesquisarCEP {
    Scanner leitura = new Scanner(System.in);
    public static List<Endereco> listaPesquisados = new ArrayList<>();

    public List<Endereco> getListaPesquisados() {
        return listaPesquisados;
    }

    public void realizarBusca() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String numeroCep;
        System.out.println("\n==================== Pesquisa de endereço através do CEP ==========================");
        do {
            System.out.print("\nDigite o CEP para pesquisar ou 0 para finalizar: ");
            numeroCep = leitura.nextLine();

            if (numeroCep.equalsIgnoreCase("0")){
                System.out.println("\nSaindo da busca");
            } else if (numeroCep.length() != 8) {
                System.out.println("\nNúmero do CEP inválido, digite um CEP com 8 digitos");
            } else {
                try {
                    String endereco = "https://viacep.com.br/ws/" + numeroCep + "/json/";
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(endereco))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String json = response.body();
                    //System.out.println(response.body());

                    EnderecoJson meuEnderecoJson = gson.fromJson(json, EnderecoJson.class);
                    Endereco meuEnderecoFinal = new Endereco(meuEnderecoJson);

                    if (meuEnderecoFinal.getErro()) {
                        throw new ErroNaPesquisaException("CEP Iválido, verifique o número digitado!");
                    }

                    System.out.println("\n" + meuEnderecoFinal);
                    listaPesquisados.add(meuEnderecoFinal);

                } catch (IllegalStateException | JsonSyntaxException erro) {
                    System.out.println("\nOOCORREU UM ERRO NA PESQUISA!");
                    throw new RuntimeException(erro);

                } catch (NumberFormatException erro) {
                    System.out.println("\nOCORREU UM ERRO NA CONVERSÃO DOS DADOS!");
                    System.out.println("Mensagem de erro: " + erro.getMessage());

                } catch (IllegalArgumentException erro) {
                    System.out.println("\nOCORREU UM ERRO NA CRIAÇÃO DA URL!");
                    System.out.println("Mensagem de erro: " + erro.getMessage());

                } catch (ErroNaPesquisaException erro) {
                    System.out.println("\nOCORREU UM ERRO NA PESQUISA!\n");
                    System.out.println(erro.getMessage());

                } catch (IOException | InterruptedException erro) {
                    System.out.println("\nOcorreu um erro de RuntimeException");
                    throw new RuntimeException(erro);
                }
            }
        }while (!numeroCep.equalsIgnoreCase("0"));
    }

    public void listaPesquisados(){
        if (listaPesquisados.isEmpty()) {
            System.out.println("\nLista vazia, favor faça uma pesquisa antes de usar essa função!");

        } else {
            System.out.println("\nLista de CEPs pesquisados:\n");

            for (Endereco item : listaPesquisados) {
                System.out.println(item);
            }
        }
        System.out.println("\nPressione Enter para continuar...");
        leitura.nextLine();
    }

}
