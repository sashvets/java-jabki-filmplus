package ru.jabki.filmplus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        UserService.clear();
        userService.create(
                new User(33L,
                        "Ильдар Исаков",
                        "ildar.isakov@example.com",
                        "ildarisakov",
                        LocalDate.of(1998, 9, 22)
                )
        );
        userService.create(
                new User(44L,
                        "Азар Давыдов",
                        "azar.davydov@example.com",
                        "azardavydov",
                        LocalDate.of(1988, 3, 8)
                ));
    }


    @Test
    void testCreate() {
        User user = userService.create(
                new User(1L,
                        "Марк Давыдов",
                        "mark.davydov@example.com",
                        "markdavydov",
                        LocalDate.of(1994, 3, 15)
                )
        );
        assertEquals(1L, user.getId());
        assertEquals("Марк Давыдов", user.getName());
        assertEquals("mark.davydov@example.com", user.getEmail());
        assertEquals(LocalDate.of(1994, 3, 15), user.getBirthday());
    }

    @Test
    void testCreateValidate() {
        assertThrows(UserException.class, () ->
                userService.create(
                        new User(
                                "Ильдар Исаков",
                                "ildar.isakov@example.com",
                                "ildarisakov",
                                LocalDate.of(1998, 9, 22)
                        ))
        );
        assertThrows(UserException.class, () -> userService.create(null));
        assertThrows(UserException.class, () ->
                userService.create(
                        new User(null,
                                "ilya.davydov@example.com",
                                "ilyadavydov",
                                LocalDate.of(1988, 3, 8)
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Илья Давыдов",
                                null,
                                "ilyadavydov",
                                LocalDate.of(1988, 3, 8)
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Илья Давыдов",
                                "ilya.davydov@example.com",
                                null,
                                LocalDate.of(1988, 3, 8)
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Илья Давыдов",
                                "ilya.davydov@example.com",
                                "ilyadavydov",
                                null
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Герман Давыдов",
                                "german.davydov@example.com",
                                "germandavydov",
                                LocalDate.of(2026, 3, 15)
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Илья Давыдов",
                                "ilya.davydov@example.com",
                                "ilyadavydov",
                                LocalDate.of(2023, 3, 8)
                        ))
        );
        assertThrows(UserException.class, () ->
                userService.create(
                        new User("Афоня Давыдов",
                                "afonya.davydov@example.com",
                                "fonyadavydov",
                                LocalDate.of(1894, 3, 15)
                        ))
        );
    }

    @Test
    void getById() {
        User user = userService.getById(33L);
        assertEquals(33L, user.getId());
        assertEquals("Ильдар Исаков", user.getName());
        assertEquals("ildar.isakov@example.com", user.getEmail());
        assertEquals("ildarisakov", user.getLogin());
        assertEquals(LocalDate.of(1998, 9, 22), user.getBirthday());
    }

    @Test
    void delete() {
        userService.delete(33L);
        assertThrows(UserException.class, () -> userService.getById(33L));
        assertThrows(UserException.class, () -> userService.getById(33L));
    }

    @Test
    void update() {
        User user = userService.getById(44L);
        assertEquals(44L, user.getId());
        assertEquals("Азар Давыдов", user.getName());
        assertEquals("azar.davydov@example.com", user.getEmail());
        assertEquals(LocalDate.of(1988, 3, 8), user.getBirthday());
        user = userService.update(new User(44L,
                "Илья Давыдов",
                "ilya.davydov@example.com",
                "ilyadavydov",
                LocalDate.of(1988, 3, 9)));
    }

    @Test
    void clear() {
        UserService.clear();
        assertThrows(UserException.class, () -> userService.getById(44L));
    }
}