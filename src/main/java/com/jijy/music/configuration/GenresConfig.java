package com.jijy.music.configuration;

import com.jijy.music.persistence.model.Genres;
import com.jijy.music.persistence.repository.GenresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenresConfig  {

    private final GenresRepository genresRepository;


    public void run(String... args) throws Exception {
        if(genresRepository.count() == 0) {
            List<Genres> genresList = List.of(
                    new Genres(null,"Rock", "Género con guitarras eléctricas y batería fuerte.", null),
                    new Genres(null, "Pop", "Música popular con melodías pegajosas y ritmos bailables.", null),
                    new Genres(null, "Jazz", "Género con improvisación y armonías sofisticadas.", null),
                    new Genres(null, "Reggaetón", "Música urbana con ritmos latinos y letras pegadizas.", null),
                    new Genres(null, "Hip-Hop", "Género con rimas y beats urbanos.", null),
                    new Genres(null, "Electrónica", "Música producida con sintetizadores y efectos digitales.", null),
                    new Genres(null, "Clásica", "Composiciones orquestales de grandes maestros como Beethoven.", null),
                    new Genres(null, "Metal", "Rock pesado con guitarras distorsionadas y baterías rápidas.", null),
                    new Genres(null, "Salsa", "Música bailable de origen latino con percusión intensa.", null),
                    new Genres(null, "Blues", "Género con estructuras melódicas melancólicas y guitarras expresivas.", null)
            );
            genresRepository.saveAll(genresList);
            log.info("Se insertaron los generos");
        }

    }
}
