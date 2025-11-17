package ru.jabki.filmplus.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserLike {
    private Boolean state;
    private final Long filmId;
    private final Long userId;
    private final LocalDateTime created;
    private LocalDateTime modified;

    public UserLike(Boolean state, Long filmId, Long userId) {
        this.state = state;
        this.filmId = filmId;
        this.userId = userId;
        this.created = LocalDateTime.now();
    }

    public Boolean getState() {
        return this.state;
    }

    public void toggleLike() {
        this.modified = LocalDateTime.now();
        this.state = !this.state;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public LocalDateTime getModified() {
        return this.modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLike userLike)) return false;
        return this.filmId.equals(userLike.filmId) && this.userId.equals(userLike.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.filmId, this.userId);
    }
}
