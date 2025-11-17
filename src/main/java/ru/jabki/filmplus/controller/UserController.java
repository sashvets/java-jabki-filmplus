package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody User user) {
        return this.userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody User user) {
        return this.userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }
}