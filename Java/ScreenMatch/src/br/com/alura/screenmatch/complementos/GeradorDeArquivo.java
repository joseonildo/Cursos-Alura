package br.com.alura.screenmatch.complementos;

import br.com.alura.screenmatch.modelos.Titulo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GeradorDeArquivo {
    Scanner leitura = new Scanner(System.in);

        public void gravarArquivo(int tipo) throws IOException {
        var pesquisa = new PesquisaTitulos();
        var listaPesquisados = pesquisa.getListaPesquisados();
        File NomeArquivo;

        System.out.println("\n======================== Gerador de arquivo ==============================");
        if (listaPesquisados.isEmpty()) {
            System.out.println("\nLista vazia, favor faça uma pesquisa antes de usar essa função!");
            System.out.println("Operação cancelada!");
            System.out.println("\nPressione Enter para continuar...");
            leitura.nextLine();
        } else {
            String nomeArquivo = "";
            String nomeFinal = "";
            boolean nomeOK = false;
            do {
                while (nomeArquivo.isBlank()) {
                    System.out.print("Digite o nome do arquivo: ");
                    nomeArquivo = leitura.nextLine();
                    if (nomeArquivo.isBlank()) {
                        System.out.println("Nome do arquivo inválido! Necessário digitar um nome!\n");
                    }
                }
                if (tipo == 1) {
                    if (nomeArquivo.contains(".txt")) {
                        nomeFinal = nomeArquivo;
                    } else {
                        nomeFinal = nomeArquivo + ".txt";
                    }
                } else if (tipo == 2) {
                    if (nomeArquivo.contains(".json")) {
                        nomeFinal = nomeArquivo;
                    } else {
                        nomeFinal = nomeArquivo + ".json";
                    }
                }

                NomeArquivo = new File(nomeFinal);
                if (NomeArquivo.exists()) {
                    while (NomeArquivo.exists()) {
                        System.out.println("\nArquivo " + nomeFinal + " já existe");
                        System.out.print("Sobrescrever o arquivo existente? Sim ou não: ");
                        String opcao = leitura.nextLine();

                        if (opcao.equalsIgnoreCase("Sim")) {
                            nomeOK = true;
                            break;
                        } else if (opcao.equalsIgnoreCase("Não") || opcao.isBlank()) {
                            System.out.println("\nDigite outro nome para o arquivo!");
                            nomeArquivo = "";
                            break;
                        }
                    }
                }else {
                    nomeOK = true;
                }
            } while(!nomeOK);

            if (tipo == 1) {
                FileWriter arquivo = new FileWriter(NomeArquivo);

                for (Titulo item : listaPesquisados) {
                    arquivo.write(item.toString() + "\n");
                }
                System.out.println("\nArquivo " + NomeArquivo + " gerado com sucesso!");
                arquivo.close();

                System.out.print("Exibir o conteúdo do arquivo gravado? Sim ou Não: ");
                String resposta = leitura.nextLine();
                if (resposta.equalsIgnoreCase("sim")) {
                    System.out.println("\nConteúdo do " + NomeArquivo);
                    Scanner scanner = new Scanner(NomeArquivo);
                    while (scanner.hasNextLine()) {
                        String linha = scanner.nextLine();
                        System.out.println(linha);
                    }
                    scanner.close();
                    System.out.println("\nPressione enter para finalizar!");
                    leitura.nextLine();
                } else {
                    System.out.print("\nOK! Continuando...");
                }
            } else if (tipo == 2) {
                Gson gson = new GsonBuilder()
                        //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .create();

                FileWriter arquivo = new FileWriter(NomeArquivo);
                arquivo.write(gson.toJson(listaPesquisados));
                System.out.println("\nArquivo " + nomeFinal + " gerado com sucesso!");
                arquivo.close();

                System.out.print("Exibir o conteúdo do arquivo gravado? Sim ou Não: ");
                String resposta = leitura.nextLine();

                if (resposta.equalsIgnoreCase("sim")) {
                    System.out.println("\nConteúdo do " + NomeArquivo);
                    Scanner scanner = new Scanner(NomeArquivo);
                    while (scanner.hasNextLine()) {
                        String linha = scanner.nextLine();
                        System.out.println(linha);
                    }
                    scanner.close();
                    System.out.println("\nPressione enter para finalizar!");
                    leitura.nextLine();
                }
            }
        }
    }
}
