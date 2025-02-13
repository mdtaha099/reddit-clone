package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.exception.UserAlreadyExistsException;
import io.mountblue.reddit_clone.dao.UserRepository;
import io.mountblue.reddit_clone.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void Save(User user) throws UserAlreadyExistsException {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email is already registered");
        }
        user.setAuthority("ROLE_USER");
        user.setPassword("{noop}"+user.getPassword());
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Post> findAllPostsByUserId(int userId) {
        return userRepository.findAllPostsByUserId(userId);
    }

    @Override
    public Set<String> findAllUsernames() {
        return userRepository.findAllUsernames();
    }
}
