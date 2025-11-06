package ru.jabki.filmplus.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;

    private static final AtomicLong counter = new AtomicLong(0);

    public User(Long id, String name, String email, String login, LocalDate birthday) {
        if (id != null) {
            counter.accumulateAndGet(id, Math::max);
            this.id = id;
        }
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLogin() {
        return this.login;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void segLogin(String login) {
        this.login = login;
    }

    public void segBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Long nextId() {
        return counter.incrementAndGet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return this.email.equalsIgnoreCase(user.email) &&
                this.login.equalsIgnoreCase(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email.toLowerCase(), this.login.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', email='%s', login='%s', birthday=%s}",
                this.id, this.name, this.email, this.login, this.birthday);
    }
}