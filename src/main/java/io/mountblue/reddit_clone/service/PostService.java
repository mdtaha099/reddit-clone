package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Post findById(int id);

    Post save(Post post);

    Post upvote(int id);

    Post downvote(int id);

    List<Post> findAll();

    List<Post> findAllOrderByUpvotes();

    List<Post> findAllByIsPostTrueAndContentContainingOrTitleContaining(String content, String title);

    Page<Post> findLatestPosts(User user,int page);
    Page<Post> findTopPosts(User user,int page);

    Page<Post> findLatestPosts(int page);

    Page<Post> findTopPosts(int page);
}
