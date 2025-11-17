package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.UserCommentException;
import ru.jabki.filmplus.exception.UserLikeException;
import ru.jabki.filmplus.model.UserComment;
import ru.jabki.filmplus.model.UserLike;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserActivityService {
    private final FilmService filmService;
    private final UserService userService;
    private final Set<UserLike> likes = new HashSet<>();
    private final Set<UserComment> comments = new HashSet<>();

    public UserActivityService(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public FilmService getFilmService() {
        return this.filmService;
    }

    public UserLike setLikeFilm(Boolean state, Long filmId, Long userId) {
        this.filmService.getById(filmId);
        this.userService.getById(userId);
        if (state == null) {
            throw new UserLikeException("A reaction cannot be empty");
        }
        for (UserLike like : this.likes) {
            if (like.getFilmId().equals(filmId) && like.getUserId().equals(userId)) {
                if (like.getState() == state) {
                    return like;
                } else {
                    like.toggleLike();
                    return like;
                }
            }
        }
        UserLike userLike = new UserLike(state, filmId, userId);
        this.likes.add(userLike);
        return userLike;
    }

    public void deleteLikeFilm(Long filmId, Long userId) {
        this.filmService.getById(filmId);
        this.userService.getById(userId);
        if (!this.likes.removeIf(like ->
                like.getFilmId().equals(filmId) && like.getUserId().equals(userId)
        )) {
            throw new UserLikeException("Reaction is not set");
        }
    }

    public Set<UserLike> getFilmReactions(Long filmId, Boolean state) {
        this.filmService.getById(filmId);
        Set<UserLike> filmLikes = new HashSet<>();
        for (UserLike like : this.likes) {
            if (!like.getFilmId().equals(filmId)) {
                continue;
            }
            if (state == null || like.getState().equals(state)) {
                filmLikes.add(like);
            }
        }
        return filmLikes;
    }

    public Set<UserLike> getUserReactions(Long userId, Boolean state) {
        this.userService.getById(userId);
        Set<UserLike> userLikes = new HashSet<>();
        for (UserLike like : this.likes) {
            if (!like.getUserId().equals(userId)) {
                continue;
            }
            if (state == null || like.getState().equals(state)) {
                userLikes.add(like);
            }
        }
        return userLikes;
    }

    public UserComment addComment(String commentText, Long filmId, Long userId) {
        this.filmService.getById(filmId);
        this.userService.getById(userId);
        if (commentText == null || commentText.isBlank()) {
            throw new UserCommentException("Comment cannot be empty");
        }
        UserComment userComment = new UserComment(commentText, filmId, userId);
        this.comments.add(userComment);
        return userComment;
    }

    public UserComment editComment(Long commentId, String commentText) {
        if (commentText == null || commentText.isBlank()) {
            throw new UserCommentException("Comment text cannot be empty");
        }
        for (UserComment userComment : this.comments) {
            if (userComment.getId().equals(commentId)) {
                userComment.editCommentText(commentText);
                return userComment;
            }
        }
        throw new UserCommentException("Comment not found");
    }

    public void deleteComment(Long commentId) {
        if (!this.comments.removeIf(comment -> comment.getId().equals(commentId))) {
            throw new UserCommentException("Comment not found");
        }
    }

    public UserComment getCommentById(Long commentId) {
        UserComment userComment = null;
        for (UserComment comment : this.comments)
            if (comment.getId().equals(commentId)){
                userComment = comment;
            }
        if (userComment == null) {
            throw new UserCommentException("Comment not found");
        }
        return userComment;
    }

    public Set<UserComment> getCommentsByFilmId(Long filmId) {
        this.filmService.getById(filmId);
        Set<UserComment> filmComments = new HashSet<>();
        for (UserComment comment : this.comments) {
            if (!comment.getFilmId().equals(filmId)) {
                continue;
            }
            filmComments.add(comment);
        }
        return filmComments;
    }

    public Set<UserComment> getCommentsByUserId(Long userId) {
        this.userService.getById(userId);
        Set<UserComment> filmComments = new HashSet<>();
        for (UserComment comment : this.comments) {
            if (!comment.getUserId().equals(userId)) {
                continue;
            }
            filmComments.add(comment);
        }
        return filmComments;
    }
}