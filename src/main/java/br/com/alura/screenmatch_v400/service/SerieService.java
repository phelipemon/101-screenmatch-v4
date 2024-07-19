package br.com.alura.screenmatch_v400.service;

import br.com.alura.screenmatch_v400.dto.EpisodiosDTO;
import br.com.alura.screenmatch_v400.dto.SerieDTO;
import br.com.alura.screenmatch_v400.models.Categoria;
import br.com.alura.screenmatch_v400.models.Serie;
import br.com.alura.screenmatch_v400.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repository.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }

    public List<EpisodiosDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodiosDTO(e.getTemporada(), e.getNumero(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodiosDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repository.obterEpisodioPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodiosDTO(e.getTemporada(), e.getNumero(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromStringPortugues(genero);
        return converteDados(repository.findByGenero(categoria));
    }

    public List<EpisodiosDTO> obterTop5Episodios(Long id) {
        return repository.obterTop5EpisodiosPorSerie(id).stream()
                .map(e -> new EpisodiosDTO(e.getTemporada(), e.getNumero(), e.getTitulo()))
                .collect(Collectors.toList());
    }
}
