package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.FriendshipException;
import ru.jabki.filmplus.model.Friendship;
import ru.jabki.filmplus.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class FriendshipService {
    private final UserService userService;
    private static final Set<Friendship> friendships = new HashSet<>();

    public FriendshipService(UserService userService) {
        this.userService = userService;
    }

    public Set<User> addFriend(Long userId, Long friendId) {
        Friendship friendship = new Friendship(userId, friendId);
        validate(friendship);
        friendships.add(friendship);
        return getFriends(friendship.getUserId());
    }

    private void validate(Friendship friendship) {
        this.userService.getById(friendship.getUserId());
        this.userService.getById(friendship.getFriendId());
        if (friendship.getUserId().equals(friendship.getFriendId())) {
            throw new FriendshipException("A user cannot be friends with himself");
        }
        if (friendships.contains(friendship)) {
            throw new FriendshipException("Friendship already exists");
        }
    }

    public Set<User> getFriends(Long userId) {
        this.userService.getById(userId);
        Set<User> friends = new HashSet<>();
        for (Friendship f : friendships) {
            if (f.getUserId().equals(userId)) {
                friends.add(this.userService.getById(f.getFriendId()));
            } else if (f.getFriendId().equals(userId)) {
                friends.add(this.userService.getById(f.getUserId()));
            }
        }
        return friends;
    }

    public void removeFriend(Long userId, Long friendId) {
        if (!friendships.remove(new Friendship(userId, friendId))) {
            throw new FriendshipException("Friendship does not exist");
        }
    }
    public static void clear() {
        friendships.clear();
    }
}
