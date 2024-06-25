package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByIsPostTrueOrderByUpdatedAtDesc();
}
