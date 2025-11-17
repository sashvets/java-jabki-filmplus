package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {
    private static final Set<Film> films = new HashSet<>();

    public Film create(final Film film) {
        validate(film);
        films.add(film);
        return film;
    }

    public Film getById(final Long id) {
        final Film film = films.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (film == null) {
            throw new FilmException("Film not found");
        }
        return film;
    }

    public void delete(final Long id) {
        films.remove(getById(id));
    }

    public Film update(final Film film) {
        delete(film.getId());
        validate(film);
        films.add(film);
        return getById(film.getId());
    }

    public List<Film> search(final String name,
                             final String description,
                             final Integer durationMin,
                             final Integer durationMax,
                             final LocalDate releaseDateFrom,
                             final LocalDate releaseDateTo,
                             final Set<Genre> genres) {

        return films.stream()
                .filter(film -> name == null || film.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(film -> description == null || film.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(film -> durationMin == null || film.getDuration() >= durationMin)
                .filter(film -> durationMax == null || film.getDuration() <= durationMax)
                .filter(film -> releaseDateFrom == null || !film.getReleaseDate().isBefore(releaseDateFrom))
                .filter(film -> releaseDateTo == null || !film.getReleaseDate().isAfter(releaseDateTo))
                .filter(film -> genres == null || genres.isEmpty() || film.getGenres().stream().anyMatch(genres::contains))
                .sorted(Comparator.comparing(Film::getReleaseDate)).toList();
    }

    private void validate(final Film film) {
        if (film == null) {
            throw new FilmException("Film is null");
        }
        if (!StringUtils.hasText(film.getName())) {
            throw new FilmException("Film name is empty");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new FilmException("Film description is empty");
        }
        if (film.getReleaseDate() == null) {
            throw new FilmException("Film release date is null");
        }
        if (film.getDuration() <= 0) {
            throw new FilmException("Film duration must be greater than 0");
        }
        if (film.getGenres() == null || film.getGenres().isEmpty() || film.getGenres().contains(null)) {
            throw new FilmException("Film genres is empty");
        }
           if (films.contains(film)) {
            throw new FilmException("Film already exists");
        }
    }
    public static void clear() {
        films.clear();
    }
}