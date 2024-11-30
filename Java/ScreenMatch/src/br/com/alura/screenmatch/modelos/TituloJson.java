package br.com.alura.screenmatch.modelos;

/**
 * Classe `TituloJson` implementada como um `record` para armazenar informações de títulos retornadas por APIs externas.
 *
 * A classe `record` é uma funcionalidade do Java (a partir da versão 14 em preview e estabilizada na versão 16),
 * projetada para modelar classes imutáveis de forma concisa.
 */
public record TituloJson(
        String Title,         // Título do filme ou série.
        String Year,          // Ano de lançamento.
        String Rated,         // Classificação indicativa (ex.: PG-13, R).
        String Released,      // Data de lançamento.
        String Runtime,       // Duração (em formato de texto, ex.: "120 min").
        String Genre,         // Gêneros (ex.: "Action, Adventure, Sci-Fi").
        String Director,      // Nome do diretor.
        String Writer,        // Nome(s) do(s) roteirista(s).
        String Actors,        // Nome(s) dos atores principais.
        String Plot,          // Sinopse.
        String Language,      // Idioma(s) do título.
        String Country,       // País de produção.
        String Awards,        // Prêmios recebidos.
        String Poster,        // URL do pôster.
        String Source,        // Fonte de avaliação (ex.: "Internet Movie Database").
        String Value,         // Valor de avaliação.
        String Metascore,     // Nota de avaliação do Metascore.
        String imdbRating,    // Nota de avaliação no IMDb.
        String imdbVotes,     // Quantidade de votos no IMDb.
        String imdbID,        // ID único no IMDb.
        String Type,          // Tipo do título (ex.: "movie", "series").
        String totalSeasons,  // Número total de temporadas (caso seja uma série).
        Boolean Response      // Indica se a resposta da API foi bem-sucedida.
) {
}











