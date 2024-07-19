package br.com.alura.screenmatch_v400.dto;
import br.com.alura.screenmatch_v400.models.Categoria;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Categoria genero,
                       String atores,
                       String poster,
                       String sinopse) {
}
