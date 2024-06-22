package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;

public interface PostService {
    Post findById(int id);

    void save(Post post);
}
