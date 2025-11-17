package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.FriendshipException;
import ru.jabki.filmplus.exception.GenreException;
import ru.jabki.filmplus.exception.UserCommentException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.exception.UserLikeException;
import ru.jabki.filmplus.model.ApiError;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserError(final UserException userException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                userException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FilmException.class)
    public ResponseEntity<ApiError> handleFilmError(final FilmException filmException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                filmException.getMessage()
                        )
                );
    }

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<ApiError> handleGenreError(final GenreException genreException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                genreException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FriendshipException.class)
    public ResponseEntity<ApiError> handleFriendshipError(final FriendshipException friendshipException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                friendshipException.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserLikeException.class)
    public ResponseEntity<ApiError> handleUserLikeError(final UserLikeException userLikeException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                userLikeException.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserCommentException.class)
    public ResponseEntity<ApiError> handleUserCommentError(final UserCommentException userCommentException) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                                false,
                                userCommentException.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralError(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ApiError(false, "Internal error: " + ex.getMessage()));
    }
}