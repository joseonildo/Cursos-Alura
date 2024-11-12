package br.com.alura.screenmatch.modelos;

public class Filme extends Titulo {

    public Filme(String tipo, String nome, String anoDeLancamento, String duracaoEmMinutos) {
        super(tipo, nome, anoDeLancamento, duracaoEmMinutos);
        super.setTipo("Filme");
    }

    public void exibeFichaTecnica() {
        System.out.println();
        System.out.println("****************************************************");
        System.out.println();
        System.out.printf("""                
                Nome do titulo:      %s
                Lançamento:          %s
                Duração em minutos   %d
                Incluido no plano:   %s
                Quantidade de notas: %d
                Nota final:          %.1f
                """,

                super.getNome(),
                super.getLancamento(),
                super.getDuracao(),
                super.getNoPlano(),
                super.getQtdNotas(),
                super.getNotaFinal());
        System.out.println();
        System.out.println("****************************************************");
    }
}
