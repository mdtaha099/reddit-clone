package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;

import java.util.List;

public interface SubredditService {
    List<Post> findAllPostById(int id);
    Subreddit findById(int id);
}
