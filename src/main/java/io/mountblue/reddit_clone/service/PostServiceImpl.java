package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.PostRepository;
import io.mountblue.reddit_clone.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
}
