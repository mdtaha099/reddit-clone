package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.exception.UserNameAlreadyExistsException;
import io.mountblue.reddit_clone.entity.User;

public interface UserService {
    void Save(User user) throws UserNameAlreadyExistsException;
}
