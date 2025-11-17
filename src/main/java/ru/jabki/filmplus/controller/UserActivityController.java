package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.UserComment;
import ru.jabki.filmplus.model.UserLike;
import ru.jabki.filmplus.service.UserActivityService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/activity")
@Tag(name = "Лайки и комментарии")
public class UserActivityController {
    private final UserActivityService userActivityService;

    public UserActivityController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @PostMapping("/film/{filmId}/like")
    @Operation(summary = "Поставить лайк фильму")
    public UserLike like(@PathVariable Long filmId,
                         @RequestParam Long userId) {
        return this.userActivityService.setLikeFilm(true, filmId, userId);
    }

    @PostMapping("/film/{filmId}/unlike")
    @Operation(summary = "Поставить дизлайк фильму")
    public UserLike unlike(@PathVariable Long filmId,
                           @RequestParam Long userId) {
        return this.userActivityService.setLikeFilm(false, filmId, userId);
    }

    @DeleteMapping("/film/{filmId}/like")
    @Operation(summary = "Удалить реакцию на фильм")
    public void deleteLike(@PathVariable Long filmId,
                           @RequestParam Long userId) {
        this.userActivityService.deleteLikeFilm(filmId, userId);
    }

    @GetMapping("/film/{filmId}/likes")
    @Operation(summary = "Получить список реакций пользователей на фильм")
    public Set<UserLike> getFilmReactions(@PathVariable Long filmId,
                                          @RequestParam(required = false) Boolean likeState) {
        return this.userActivityService.getFilmReactions(filmId, likeState);
    }

    @GetMapping("/user/{userId}/likes")
    @Operation(summary = "Получить список реакций пользователя на фильмы ")
    public Set<UserLike> getUserReactions(@PathVariable Long userId,
                                          @RequestParam(required = false) Boolean likeState) {
        return this.userActivityService.getUserReactions(userId, likeState);
    }

    @PostMapping("/film/{filmId}/comment")
    @Operation(summary = "Добавить комментарий к фильму")
    public UserComment addComment(@PathVariable Long filmId,
                                  @RequestParam Long userId,
                                  @RequestParam String comment) {
        return this.userActivityService.addComment(comment, filmId, userId);
    }

    @GetMapping("/comment/{commentId}/comment")
    @Operation(summary = "Получить комментарий по id")
    public UserComment getCommentById(@PathVariable Long commentId) {
        return this.userActivityService.getCommentById(commentId);
    }

    @PatchMapping("/comment/{commentId}")
    @Operation(summary = "Редактировать комментарий")
    public UserComment editComment(@PathVariable Long commentId,
                                   @RequestParam String comment) {
        return this.userActivityService.editComment(commentId, comment);
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "Удалить комментарий")
    public void deleteComment(@PathVariable Long commentId) {
        this.userActivityService.deleteComment(commentId);
    }

    @GetMapping("/film/{filmId}/comments")
    @Operation(summary = "Получить список комментариев к фильму")
    public Set<UserComment> getCommentsByFilm(@PathVariable Long filmId) {
        return this.userActivityService.getCommentsByFilmId(filmId);
    }

    @GetMapping("/user/{userId}/comments")
    @Operation(summary = "Получить список комментариев пользователя")
    public Set<UserComment> getCommentsByUser(@PathVariable Long userId) {
        return this.userActivityService.getCommentsByUserId(userId);
    }
}
