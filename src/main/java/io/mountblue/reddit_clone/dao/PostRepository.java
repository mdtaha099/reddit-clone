package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            " AND p.is_post IS TRUE " +
            "ORDER BY p.created_at DESC",
            countQuery = "SELECT COUNT(*) FROM post p " +
                    "INNER JOIN subreddit s ON s.id = p.subreddit_id " +
                    "INNER JOIN user_subreddit us ON us.subreddit_id = s.id " +
                    "INNER JOIN user u ON u.id = us.user_id " +
                    "WHERE u.id = :userId",
            nativeQuery = true)
    Page<Post> findAllPostsForUserOrderedByCreationDate(@Param("userId") int userId, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p " +
            "INNER JOIN subreddit s ON s.id = p.subreddit_id " +
            "INNER JOIN user_subreddit us ON us.subreddit_id = s.id " +
            "INNER JOIN user u ON u.id = us.user_id " +
            "WHERE u.id = :userId " +
            " AND p.is_post IS TRUE " +
            "ORDER BY p.upvotes DESC",
            countQuery = "SELECT COUNT(*) FROM post p " +
                    "INNER JOIN subreddit s ON s.id = p.subreddit_id " +
                    "INNER JOIN user_subreddit us ON us.subreddit_id = s.id " +
                    "INNER JOIN user u ON u.id = us.user_id " +
                    "WHERE u.id = :userId",
            nativeQuery = true)
    Page<Post> findAllPostsForUserOrderedByUpvotes(@Param("userId") int userId, Pageable pageable);



    Page<Post> findAllByIsPostTrueOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findAllByIsPostTrueOrderByUpvotesDesc(Pageable pageable);
}
