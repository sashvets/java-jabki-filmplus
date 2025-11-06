package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private static final Set<User> users = new HashSet<>();

    public User create(final User user) {
        validate(user);
        user.setId(user.nextId());
        users.add(user);
        return user;
    }

    public User getById(final Long id) {
        final User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    public void delete(final Long id) {
        users.remove(getById(id));
    }

    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        return existUser;
    }

    private void validate(final User user) {
        if (user == null) {
            throw new UserException("User is null");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("User name is empty");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("User email is empty");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("User login is empty");
        }
        if (!user.getLogin().matches("^[A-Za-z0-9]{6,20}$")) {
            throw new UserException("User login is not valid. It must contain 6–20 characters: letters, digits");
        }
        if (user.getBirthday() == null) {
            throw new UserException("User birthday is null");
        }
        if (users.contains(user)) {
            throw new UserException("User already exists");
        }
    }
}