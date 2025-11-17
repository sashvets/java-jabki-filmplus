package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.service.FilmService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody Film film) {
        return filmService.create(film);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить фильм по id")
    public Film getById(@PathVariable Long id) {
        return filmService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление фильма")
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление фильма")
    public void delete(@PathVariable Long id) {
        filmService.delete(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск фильмов по параметрам")
    public List<Film> search(
            @Parameter(description = "Название фильма(\"Миссия невыполнима\")")
            @RequestParam(required = false) String name,

            @Parameter(description = "Описание фильма(\"Жили-были дед да баба\")")
            @RequestParam(required = false) String description,

            @Parameter(description = "Минимальная длительность в минутах(30)")
            @RequestParam(required = false) Integer durationMin,

            @Parameter(description = "Максимальная длительность в минутах(150)")
            @RequestParam(required = false) Integer durationMax,

            @Parameter(description = "Дата релиза с(YYYY-MM-DD)")
            @RequestParam(required = false) LocalDate releaseDateFrom,

            @Parameter(description = "Дата релиза по(YYYY-MM-DD)")
            @RequestParam(required = false) LocalDate releaseDateTo,

            @Parameter(description = "Жанры фильма(Боевик, Приключение)")
            @RequestParam(required = false) Set<String> genres
    ) {
        Set<Genre> genreSet = null;
        if (genres != null && !genres.isEmpty()) {
            genreSet = genres.stream()
                    .map(Genre::fromName)
                    .collect(Collectors.toSet());
        }

        return filmService.search(
                name,
                description,
                durationMin,
                durationMax,
                releaseDateFrom,
                releaseDateTo,
                genreSet
        );
    }
}