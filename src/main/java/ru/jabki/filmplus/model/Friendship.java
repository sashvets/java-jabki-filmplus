package ru.jabki.filmplus.model;

import ru.jabki.filmplus.exception.FriendshipException;
import java.util.Set;

public class Friendship {
    private final Long userId;
    private final Long friendId;

    public Friendship(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new FriendshipException("A user cannot be friends with himself");
        }
        this.userId = userId;
        this.friendId = friendId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getFriendId() {
        return this.friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship friendship)) return false;
        return Set.of(this.userId, this.friendId)
                .equals(Set.of(friendship.userId, friendship.friendId));
    }

    @Override
    public int hashCode() {
        return Set.of(this.userId, this.friendId).hashCode();
    }
}
