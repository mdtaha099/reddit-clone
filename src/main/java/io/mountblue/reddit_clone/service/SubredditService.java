package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubredditService {
    Page<Post> findAllPostById(int id, int page);
    Subreddit findById(int id);

    Page<Post> findAllPostByIdOrderByUpvotes(int id,int page);

    Page<Post> findAllPostByIdOrderByUpdatedAt(int id,int page);
    void save(Subreddit subreddit);

    Subreddit findByName(String name);

    List<Post> findAllPostBySubredditContaining(int id, String content, String title);

    List<Subreddit> findAllByNameContaining(String search);

    List<Subreddit> findAll();
}
