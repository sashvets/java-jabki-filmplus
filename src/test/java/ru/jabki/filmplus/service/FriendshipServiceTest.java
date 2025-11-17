package ru.jabki.filmplus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jabki.filmplus.exception.FriendshipException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FriendshipServiceTest {
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService();
        UserService.clear();
        friendshipService = new FriendshipService(userService);
        FriendshipService.clear();

        userService.create(new User(1L, "Марк Давыдов", "mark.davydov@example.com", "markdavydov", LocalDate.of(1994, 3, 15)));
        userService.create(new User(2L, "Эмиль Рубцов", "emil.rubtsov@example.com", "emilrubtsov", LocalDate.of(1990, 1, 8)));
        userService.create(new User(3L, "Ильдар Исаков", "ildar.isakov@example.com", "ildarisakov", LocalDate.of(1998, 9, 22)));
    }

    @Test
    void testAddFriend() {
        Set<User> friends = friendshipService.addFriend(1L, 2L);
        assertEquals(1, friends.size());
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(2L)));
        friends = friendshipService.getFriends(2L);
        assertEquals(1, friends.size());
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(1L)));
    }

    @Test
    void testAddFriendValidation() {
        friendshipService.addFriend(1L, 2L);
        assertThrows(FriendshipException.class, () -> friendshipService.addFriend(1L, 2L));
        assertThrows(FriendshipException.class, () -> friendshipService.addFriend(1L, 1L));
        assertThrows(UserException.class, () -> friendshipService.addFriend(1L, 99L));
    }

    @Test
    void testGetFriends() {
        friendshipService.addFriend(1L, 2L);
        friendshipService.addFriend(1L, 3L);
        Set<User> friends = friendshipService.getFriends(1L);
        assertEquals(2, friends.size());
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(2L)));
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(3L)));
        friends = friendshipService.getFriends(2L);
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(1L)));
    }

    @Test
    void testRemoveFriend() {
        friendshipService.addFriend(1L, 2L);
        friendshipService.addFriend(1L, 3L);
        friendshipService.removeFriend(1L, 2L);
        Set<User> friends = friendshipService.getFriends(1L);
        assertEquals(1, friends.size());
        assertTrue(friends.stream().anyMatch(u -> u.getId().equals(3L)));
        assertThrows(FriendshipException.class, () -> friendshipService.removeFriend(1L, 2L));
    }

}
