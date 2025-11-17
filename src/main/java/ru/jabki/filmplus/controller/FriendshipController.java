package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.FriendshipService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/{userId}/friends")
@Tag(name = "Друзья")
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/{friendId}")
    @Operation(summary = "Добавить пользователя в друзья")
    public Set<User> addFriend(@PathVariable Long userId,
                               @PathVariable Long friendId) {
        return this.friendshipService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    @Operation(summary = "Удалить пользователя из друзей")
    public void removeFriend(@PathVariable Long userId,
                             @PathVariable Long friendId) {
        this.friendshipService.removeFriend(userId, friendId);
    }

    @GetMapping
    @Operation(summary = "Получить список друзей пользователя")
    public Set<User> getFriends(@PathVariable Long userId) {
        return this.friendshipService.getFriends(userId);
    }
}