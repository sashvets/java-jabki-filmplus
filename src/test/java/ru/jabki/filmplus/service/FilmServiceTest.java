package ru.jabki.filmplus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService();
        FilmService.clear();

        filmService.create(new Film(
                33L,
                "Матрица",
                "Классический sci-fi боевик",
                LocalDate.of(1999, 3, 31),
                136,
                new HashSet<>(Set.of(Genre.FICTION, Genre.ACTION))
        ));
        filmService.create(new Film(
                44L,
                "Начало",
                "Фильм о снах и подсознании",
                LocalDate.of(2010, 7, 16),
                148,
                new HashSet<>(Set.of(Genre.FICTION, Genre.THRILLER))
        ));
        filmService.create(new Film(
                55L,
                "Интерстеллар",
                "Фильм о космических путешествиях",
                LocalDate.of(2014, 11, 7),
                169,
                new HashSet<>(Set.of(Genre.FICTION, Genre.DRAMA))
        ));
        filmService.create(new Film(
                66L,
                "Тёмный рыцарь",
                "Бэтмен против Джокера",
                LocalDate.of(2008, 7, 18),
                152,
                new HashSet<>(Set.of(Genre.ACTION, Genre.THRILLER))
        ));
    }

    @Test
    void testCreate() {
        Film film = filmService.create(new Film(
                1L,
                "Грань будущего",
                "Боевой sci-fi фильм",
                LocalDate.of(2014, 6, 6),
                113,
                new HashSet<>(Set.of(Genre.ACTION, Genre.FICTION))
        ));
        assertEquals(1L, film.getId());
        assertEquals("Грань будущего", film.getName());
        assertEquals("Боевой sci-fi фильм", film.getDescription());
        assertEquals(LocalDate.of(2014, 6, 6), film.getReleaseDate());
        assertEquals(113, film.getDuration());
        assertTrue(film.getGenres().contains(Genre.ACTION));
        assertTrue(film.getGenres().contains(Genre.FICTION));
    }

    @Test
    void testCreateValidate() {
        assertThrows(FilmException.class, () -> filmService.create(null));
        assertThrows(FilmException.class, () ->
                filmService.create(
                        new Film(
                                "Грань будущего 2",
                                "Без даты",
                                null,
                                120,
                                new HashSet<>(Set.of(Genre.ACTION))
                        )));
        assertThrows(FilmException.class, () ->
                filmService.create(
                        new Film("Грань будущего 2",
                                "",
                                LocalDate.of(2020, 1, 1),
                                120,
                                new HashSet<>(Set.of(Genre.ACTION))
                        )));
        assertThrows(FilmException.class, () ->
                filmService.create(
                        new Film("Грань будущего 2",
                                "Нулевая длительность",
                                LocalDate.of(2020, 1, 1),
                                0,
                                new HashSet<>(Set.of(Genre.ACTION))
                        )));
        assertThrows(FilmException.class, () ->
                filmService.create(
                        new Film("Начало",
                                "Фильм о снах и подсознании",
                                LocalDate.of(2010, 7, 16),
                                148,
                                new HashSet<>(Set.of(Genre.FICTION, Genre.THRILLER))
                        )));
    }

    @Test
    void getById() {
        Film film = filmService.getById(33L);
        assertEquals(33L, film.getId());
        assertEquals("Матрица", film.getName());
        assertEquals("Классический sci-fi боевик", film.getDescription());
        assertEquals(LocalDate.of(1999, 3, 31), film.getReleaseDate());
        assertEquals(136, film.getDuration());
        assertTrue(film.getGenres().contains(Genre.FICTION));
    }

    @Test
    void delete() {
        filmService.delete(33L);
        assertThrows(FilmException.class, () -> filmService.getById(33L));
    }

    @Test
    void update() {
        Film film = filmService.getById(44L);
        assertEquals("Начало", film.getName());
        film = filmService.update(new Film(
                44L,
                "Начало (обновлено)",
                "Обновленное описание",
                LocalDate.of(2010, 7, 16),
                150,
                new HashSet<>(Set.of(Genre.FICTION, Genre.THRILLER))
        ));
        assertEquals("Начало (обновлено)", film.getName());
        assertEquals("Обновленное описание", film.getDescription());
        assertEquals(150, film.getDuration());
    }

    @Test
    void testSearch() {
        List<Film> results = filmService.search(
                "начало",
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(1, results.size());
        assertEquals("Начало", results.getFirst().getName());

        results = filmService.search(
                null,
                "Бэтмен",
                null,
                null,
                null,
                null,
                null);
        assertEquals(1, results.size());
        assertEquals("Тёмный рыцарь", results.getFirst().getName());

        results = filmService.search(
                null,
                null,
                150,
                170,
                null,
                null,
                null);
        assertEquals(2, results.size());

        results = filmService.search(
                null,
                null,
                null,
                null,
                LocalDate.of(2010, 1, 1),
                null,
                null);
        assertEquals(2, results.size());

        results = filmService.search(
                null,
                null,
                null,
                null,
                null,
                LocalDate.of(2010, 12, 31),
                null);
        assertEquals(3, results.size());

        results = filmService.search(
                null,
                null,
                null,
                null,
                null,
                null,
                Set.of(Genre.DRAMA));
        assertEquals(1, results.size());
        assertEquals("Интерстеллар", results.getFirst().getName());
    }

    @Test
    void clear() {
        FilmService.clear();
        assertThrows(FilmException.class, () -> filmService.getById(44L));
    }
}
