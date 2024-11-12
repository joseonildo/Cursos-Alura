package br.com.alura.screenmatch.complementos;

import java.util.Scanner;

public class ServidorApi {
    private String keyOmdb;
    public static Scanner leitura = new Scanner(System.in);

    String url;
    public String cadastraKey() {
        keyOmdb="";
        while (keyOmdb.isEmpty()) {
            System.out.println("\n=================== Configuração do servidor de busca =========================");
            System.out.println("Favor informe abaixo sua Key do servidor OMDB API para buscar os filmes\n");
            System.out.print("Digite sua key: ");
            keyOmdb = leitura.nextLine();

            url = "https://www.omdbapi.com/?apikey=" + this.keyOmdb + "&t=";

            if (keyOmdb.isBlank() || keyOmdb.length() < 8) {
                System.out.println("Key inválida! Digite novamente sua Key com 8 digitos!");
                keyOmdb="";
            }
        }
        return url;
    }

    public String getkeyOmdb() {
        return keyOmdb;
    }
}
