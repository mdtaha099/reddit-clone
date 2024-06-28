package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.exception.UserAlreadyExistsException;
import io.mountblue.reddit_clone.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void Save(User user) throws UserAlreadyExistsException;
    User findByUsername(String username);

    List<Post> findAllPostsByUserId(int userId);
    Set<String> findAllUsernames();
}
