package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.GenreException;
import ru.jabki.filmplus.exception.UserException;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralError(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ApiError(false, "Internal error: " + ex.getMessage()));
    }
}