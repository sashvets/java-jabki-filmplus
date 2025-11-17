package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private static final Set<User> users = new HashSet<>();
    private static final String LOGIN_REGEX = "^[A-Za-z0-9]{6,20}$";

    public User create(final User user) {
        validate(user);
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
        getById(id);
        users.remove(getById(id));
    }

    public User update(final User user) {
        delete(user.getId());
        validate(user);
        users.add(user);
        return getById(user.getId());
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
        if (!user.getLogin().matches(LOGIN_REGEX)) {
            throw new UserException("User login is not valid. It must contain 6–20 characters: letters, digits");
        }
        if (user.getBirthday() == null) {
            throw new UserException("User birthday is null");
        } else {
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new UserException("The user's date of birth(" + user.getBirthday() + ") has not yet arrived");
            }
            Period period = Period.between(user.getBirthday(), LocalDate.now());
            int years = period.getYears();
            if (years >= 100) {
                throw new UserException("A user at this age(" + years + ") cannot be of sound mind");
            } else if (years <= 3) {
                throw new UserException("The user is at an age(" + years + ") when they have barely learned their own name — or haven’t even been born yet.");
            }
        }
        if (users.contains(user)) {
            throw new UserException("User already exists");
        }
    }

    public static void clear() {
        users.clear();
    }
}