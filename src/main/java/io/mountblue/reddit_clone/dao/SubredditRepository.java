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
}
