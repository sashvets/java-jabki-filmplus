package ru.jabki.filmplus.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Set<Genre> genres;

    private static final AtomicLong counter = new AtomicLong(0);

    public Film(Long id, String name, String description, LocalDate releaseDate, Integer duration, Set<Genre> genres) {
        if (id != null) {
            counter.accumulateAndGet(id, Math::max);
            this.id = id;
        }
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Set<Genre> getGenres() {
        return this.genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Long nextId() {
        return counter.incrementAndGet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Film film)) {
            return false;
        }
        return this.name.equalsIgnoreCase(film.name) &&
                this.releaseDate.equals(film.releaseDate) &&
                this.duration.equals(film.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name.toLowerCase(), this.releaseDate, this.duration);
    }

    @Override
    public String toString() {
        return String.format("Film{id=%d, name='%s', release=%s, duration=%d, genres=%s}",
                this.id, this.name, this.releaseDate, this.duration, this.genres);
    }
}