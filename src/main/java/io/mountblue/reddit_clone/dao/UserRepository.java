package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findAllPostsByUserId(int userId);
}
