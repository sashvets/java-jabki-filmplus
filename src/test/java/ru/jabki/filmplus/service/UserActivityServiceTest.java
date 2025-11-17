package ru.jabki.filmplus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jabki.filmplus.exception.UserCommentException;
import ru.jabki.filmplus.exception.UserLikeException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.model.UserComment;
import ru.jabki.filmplus.model.UserLike;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserActivityServiceTest {
    private UserActivityService userActivityService;

    @BeforeEach
    void setUp() {
        FilmService filmService = new FilmService();
        UserService userService = new UserService();
        FilmService.clear();
        UserService.clear();

        userActivityService = new UserActivityService(filmService, userService);
        filmService.create(new Film(1L, "Матрица", "The Matrix", LocalDate.of(1999, 3, 31), 136, new HashSet<>(Set.of(Genre.FICTION, Genre.ACTION))));
        filmService.create(new Film(2L, "Начало", "Inception", LocalDate.of(2010, 7, 16), 148, new HashSet<>(Set.of(Genre.FICTION, Genre.THRILLER))));
        filmService.create(new Film(3L, "Грань будущего", "Edge of Tomorrow", LocalDate.of(2014, 6, 6), 113, new HashSet<>(Set.of(Genre.ACTION, Genre.FICTION))));

        userService.create(new User(1L, "Марк Давыдов", "mark.davydov@example.com", "markdavydov", LocalDate.of(1994, 3, 15)));
        userService.create(new User(2L, "Эмиль Рубцов", "emil.rubtsov@example.com", "emilrubtsov", LocalDate.of(1990, 1, 8)));
        userService.create(new User(3L, "Ильдар Исаков", "ildar.isakov@example.com", "ildarisakov", LocalDate.of(1998, 9, 22)));
    }

    @Test
    void testSetLikeFilm() {
        UserLike like = userActivityService.setLikeFilm(true, 1L, 1L);
        assertTrue(like.getState());
        assertEquals(1L, like.getFilmId());
        assertEquals(1L, like.getUserId());
        like = userActivityService.setLikeFilm(false, 1L, 2L);
        assertFalse(like.getState());
        assertEquals(1L, like.getFilmId());
        assertEquals(2L, like.getUserId());
        like = userActivityService.setLikeFilm(true, 1L, 2L);
        assertTrue(like.getState());
        assertEquals(1L, like.getFilmId());
        assertEquals(2L, like.getUserId());
    }

    @Test
    void testDeleteLikeFilm() {
        UserLike like = userActivityService.setLikeFilm(true, 1L, 1L);
        assertTrue(like.getState());
        assertEquals(1L, like.getFilmId());
        assertEquals(1L, like.getUserId());
        userActivityService.deleteLikeFilm(1L, 1L);
        like = null;
        for (UserLike userLike : userActivityService.getUserReactions(2L, null)) {
            if (userLike.getFilmId().equals(1L) &&
                    userLike.getUserId().equals(2L)) {
                like = userLike;
            }
        }
        assertNull(like);
        assertThrows(UserLikeException.class, () -> userActivityService.deleteLikeFilm(1L, 2L));
    }

    @Test
    void testGetUserReactions() {
        userActivityService.setLikeFilm(true, 1L, 1L);
        userActivityService.setLikeFilm(false, 2L, 1L);
        Set<UserLike> expected = Set.of(new UserLike(true, 1L, 1L));
        assertEquals(expected, userActivityService.getUserReactions(1L, true));
        expected = Set.of(new UserLike(false, 2L, 1L));
        assertEquals(expected, userActivityService.getUserReactions(1L, false));
        expected = Set.of(
                new UserLike(true, 1L, 1L),
                new UserLike(false, 2L, 1L)
        );
        assertEquals(expected, userActivityService.getUserReactions(1L, null));
        userActivityService.deleteLikeFilm(1L, 1L);
        userActivityService.deleteLikeFilm(2L, 1L);
        assertTrue(userActivityService.getUserReactions(1L, null).isEmpty());
    }

    @Test
    void testGetFilmReactions() {
        userActivityService.setLikeFilm(true, 1L, 1L);
        userActivityService.setLikeFilm(false, 1L, 2L);
        Set<UserLike> expected = Set.of(new UserLike(true, 1L, 1L));
        assertEquals(expected, userActivityService.getFilmReactions(1L, true));
        expected = Set.of(new UserLike(false, 1L, 2L));
        assertEquals(expected, userActivityService.getFilmReactions(1L, false));
        expected = Set.of(
                new UserLike(true, 1L, 1L),
                new UserLike(false, 1L, 2L)
        );
        assertEquals(expected, userActivityService.getFilmReactions(1L, null));
        userActivityService.deleteLikeFilm(1L, 1L);
        userActivityService.deleteLikeFilm(1L, 2L);
        assertTrue(userActivityService.getFilmReactions(1L, null).isEmpty());
    }

    @Test
    void testAddComment() {
        UserComment comment = userActivityService.addComment("Комментарий", 1L, 1L);
        assertNotNull(comment.getId());
        assertEquals("Комментарий", comment.getCommentText());
        assertThrows(UserCommentException.class, () -> userActivityService.addComment(null, 1L, 1L));
        assertThrows(UserCommentException.class, () -> userActivityService.addComment("", 1L, 1L));
    }

    @Test
    void testEditComment() {
        Long commentId = userActivityService.addComment("Комментарий", 1L, 1L).getId();
        UserComment comment = userActivityService.editComment(commentId, "Комментарий отредактирован");
        assertNotNull(comment.getId());
        assertEquals("Комментарий отредактирован", comment.getCommentText());
        assertThrows(UserCommentException.class, () -> userActivityService.editComment(commentId, null));
        assertThrows(UserCommentException.class, () -> userActivityService.editComment(commentId, ""));
        assertThrows(UserCommentException.class, () -> userActivityService.editComment(99L, "Комментарий не существует"));
    }

    @Test
    void testDeleteComment() {
        Long commentId = userActivityService.addComment("Комментарий", 1L, 1L).getId();
        userActivityService.deleteComment(commentId);
        assertThrows(UserCommentException.class, () -> userActivityService.getCommentById(commentId));
    }

    @Test
    void testGetCommentById() {
        Long commentId = userActivityService.addComment("Комментарий", 1L, 1L).getId();
        UserComment comment = userActivityService.getCommentById(commentId);
        userActivityService.deleteComment(commentId);
        assertThrows(UserCommentException.class, () -> userActivityService.getCommentById(commentId));
    }

    @Test
    void testGetCommentsByFilmId() {
        userActivityService.addComment("Комментарий 1", 1L, 1L);
        userActivityService.addComment("Комментарий 2", 1L, 2L);
        userActivityService.addComment("Комментарий 3", 1L, 3L);
        Set<UserComment> comments = userActivityService.getCommentsByFilmId(1L);
        assertEquals(3, comments.size());
    }

    @Test
    void testGetCommentsByUserId() {
        userActivityService.addComment("Комментарий 1", 1L, 1L);
        userActivityService.addComment("Комментарий 2", 2L, 1L);
        userActivityService.addComment("Комментарий 3", 3L, 1L);
        Set<UserComment> comments = userActivityService.getCommentsByUserId(1L);
        assertEquals(3, comments.size());
    }
}