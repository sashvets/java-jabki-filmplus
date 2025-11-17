package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import ru.jabki.filmplus.exception.GenreException;

public enum Genre {
    ACTION("Боевик"),
    ADVENTURE("Приключения"),
    COMEDY("Комедия"),
    DRAMA("Драма"),
    HORROR("Ужасы"),
    FICTION("Фантастика"),
    FANTASY("Фэнтези"),
    THRILLER("Триллер"),
    ROMANCE("Мелодрама"),
    MYSTERY("Детектив"),
    CRIME("Криминал"),
    ANIMATION("Анимация"),
    DOCUMENTARY("Документальный"),
    WESTERN("Вестерн"),
    MUSICAL("Мюзикл"),
    BIOGRAPHY("Биография");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static Genre fromName(String value) {
        for (Genre genre : values()) {
            if (genre.name().equalsIgnoreCase(value) ||
                    genre.name.equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new GenreException("Unknown genre: " + value);
    }
}