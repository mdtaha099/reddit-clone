package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.PostRepository;
import io.mountblue.reddit_clone.dao.UserRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post upvote(int id) {
        Optional<Post> op = postRepository.findById(id);
        if (op.isEmpty()) {
            return null;
        }
        Post p = op.get();
        p.setUpvotes(p.getUpvotes() + 1);
        p.getUser().incrementKarma();
        postRepository.save(p);
        return p;
    }

    @Override
    public Post downvote(int id) {
        Optional<Post> op = postRepository.findById(id);
        if (op.isEmpty()) {
            return null;
        }
        Post p = op.get();
        p.setDownvotes(p.getDownvotes() + 1);
        postRepository.save(p);
        return p;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAllByIsPostTrueOrderByUpdatedAtDesc();
    }

    @Override
    public List<Post> findAllOrderByUpvotes() {
        return postRepository.findAllByIsPostTrueOrderByUpvotesDesc();
    }

    @Override
    public List<Post> findAllByIsPostTrueAndContentContainingOrTitleContaining(String content, String title) {
        return postRepository.findAllByIsPostTrueAndContentContainingOrTitleContaining(content,title);
    }

    @Override
    public Page<Post> findLatestPosts(User user,int page) {
        Pageable pageable = PageRequest.of(page,10);
        return postRepository.findAllPostsForUserOrderedByCreationDate(user.getId(),pageable);
    }

    @Override
    public Page<Post> findTopPosts(User user,int page) {
        Pageable pageable = PageRequest.of(page,10);
        return postRepository.findAllPostsForUserOrderedByUpvotes(user.getId(),pageable);
    }

    @Override
    public Page<Post> findLatestPosts(int page) {
        Pageable pageable = PageRequest.of(page,10);
        return postRepository.findAllByIsPostTrueOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Post> findTopPosts(int page) {
        Pageable pageable = PageRequest.of(page,10);
        return postRepository.findAllByIsPostTrueOrderByUpvotesDesc(pageable);
    }
}
