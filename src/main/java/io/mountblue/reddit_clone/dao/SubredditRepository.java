package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubredditRepository extends JpaRepository<Subreddit,Integer> {
    @Query("FROM Post p " +
            "INNER JOIN p.subreddit s " +
            "WHERE p.isPost = true " +
            "AND p.subreddit.id = :id")
    List<Post> findAllPostsById(int id);

    @Query("FROM Post p " +
            "INNER JOIN p.subreddit s " +
            "WHERE p.isPost = true " +
            "AND p.subreddit.id = :id  " +
            "order by p.upvotes DESC ")
    List<Post> findAllPostsByIdOrderByUpvotes(int id);

    @Query("FROM Post p " +
            "INNER JOIN p.subreddit s " +
            "WHERE p.isPost = true " +
            "AND p.subreddit.id = :id  " +
            "order by p.updatedAt DESC ")
    List<Post> findAllPostsByIdOrderByUpdatedAt(int id);

    Subreddit findByName(String name);
    @Query("FROM Post p " +
            "INNER JOIN p.subreddit s " +
            "WHERE p.isPost = true " +
            "AND p.subreddit.id = :id "+
            "AND (p.content LIKE %:content% OR p.title LIKE %:title%) ")
    List<Post> findAllPostBySubredditContaining(int id,String content,String title);

    List<Subreddit> findAllByNameContaining(String search);
}
