package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.exception.UserNameAlreadyExistsException;
import io.mountblue.reddit_clone.dao.UserRepository;
import io.mountblue.reddit_clone.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void Save(User user) throws UserNameAlreadyExistsException {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserNameAlreadyExistsException("Username already exists");
        }
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserNameAlreadyExistsException("Email is already registered");
        }
        user.setAuthority("ROLE_USER");
        user.setPassword("{noop}"+user.getPassword());
        user.setActive(true);
        userRepository.save(user);
    }
}
