package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByIsPostTrueOrderByUpdatedAtDesc();

    List<Post> findAllByIsPostTrueOrderByUpvotesDesc();

    @Query("SELECT p FROM Post p WHERE p.isPost = true AND (p.content LIKE %:content% OR p.title LIKE %:title%)")
    List<Post> findAllByIsPostTrueAndContentContainingOrTitleContaining(String content,String title);

    @Query(value = "SELECT p.* FROM post p " +
            "INNER JOIN subreddit s ON s.id = p.subreddit_id " +
            "INNER JOIN user_subreddit us ON us.subreddit_id = s.id " +
            "INNER JOIN user u ON u.id = us.user_id " +
            "WHERE u.id = :userId " +
            "ORDER BY p.created_at DESC", nativeQuery = true)
    List<Post> findAllPostsForUserOrderedByCreationDate(@Param("userId") int userId);

    @Query(value = "SELECT p.* FROM post p " +
            "INNER JOIN subreddit s ON s.id = p.subreddit_id " +
            "INNER JOIN user_subreddit us ON us.subreddit_id = s.id " +
            "INNER JOIN user u ON u.id = us.user_id " +
            "WHERE u.id = :userId " +
            "ORDER BY p.upvotes DESC", nativeQuery = true)
    List<Post> findAllPostsForUserOrderedByUpvotes(int userId);

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByUpvotesDesc();
}
