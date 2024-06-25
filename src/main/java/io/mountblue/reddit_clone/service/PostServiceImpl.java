package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.PostRepository;
import io.mountblue.reddit_clone.dto.VoteResponse;
import io.mountblue.reddit_clone.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public VoteResponse upvote(int id) {
        Optional<Post> op = postRepository.findById(id);
        if (op.isEmpty()) {
            return new VoteResponse();
        }

        Post p = op.get();
        p.setUpvotes(p.getUpvotes() + 1);
        postRepository.save(p);
        return new VoteResponse(p.getId(), p.getUpvotes(), p.getDownvotes());
    }

    @Override
    public VoteResponse downvote(int id) {
        Optional<Post> op = postRepository.findById(id);
        if (op.isEmpty()) {
            return new VoteResponse();
        }

        Post p = op.get();
        p.setDownvotes(p.getDownvotes() + 1);
        postRepository.save(p);
        return new VoteResponse(p.getId(), p.getUpvotes(), p.getDownvotes());
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAllByIsPostTrueOrderByUpdatedAtDesc();
    }
}
