package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;

import java.util.List;

public interface PostService {
    Post findById(int id);

    Post save(Post post);

    Post upvote(int id);

    Post downvote(int id);

    List<Post> findAll();

    List<Post> findAllOrderByUpvotes();
}
