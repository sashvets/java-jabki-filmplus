package ru.jabki.filmplus.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class UserComment {
    private Long id;
    private String commentText;
    private final Long filmId;
    private final Long userId;
    private final LocalDateTime created;
    private LocalDateTime modified;


    private static final AtomicLong counter = new AtomicLong(0);

    public UserComment(String commentText, Long filmId, Long userId) {
        this.id = nextId();
        this.commentText = commentText;
        this.filmId = filmId;
        this.userId = userId;
        this.created = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    private static Long nextId() {
        return counter.incrementAndGet();
    }

    public String getCommentText() {
        return this.commentText;
    }

    public void editCommentText(String commentText) {
        this.modified = LocalDateTime.now();
        this.commentText = commentText;
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
        if (!(o instanceof UserComment userComment)) return false;
        return this.id.equals(userComment.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
