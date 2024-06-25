package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dto.VoteResponse;
import io.mountblue.reddit_clone.entity.Post;

import java.util.List;

public interface PostService {
    Post findById(int id);

    void save(Post post);

    VoteResponse upvote(int id);

    VoteResponse downvote(int id);

    List<Post> findAll();
}
