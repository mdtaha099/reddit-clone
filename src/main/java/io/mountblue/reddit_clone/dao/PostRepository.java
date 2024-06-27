package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByIsPostTrueOrderByUpdatedAtDesc();

    List<Post> findAllByIsPostTrueOrderByUpvotesDesc();
    @Query("SELECT p FROM Post p WHERE p.isPost = true AND (p.content LIKE %:content% OR p.title LIKE %:title%)")
    List<Post> findAllByIsPostTrueAndContentContainingOrTitleContaining(String content,String title);
}
