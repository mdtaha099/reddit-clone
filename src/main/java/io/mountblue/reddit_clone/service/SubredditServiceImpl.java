package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.SubredditRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditServiceImpl implements SubredditService{
    private SubredditRepository subredditRepository;
    public SubredditServiceImpl(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    public Page<Post> findAllPostById(int id, int page) {
        Pageable pageable = PageRequest.of(page,10);
        return subredditRepository.findAllPostsById(id,pageable);
    }

    public Subreddit findById(int id) {
        return subredditRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Post> findAllPostByIdOrderByUpvotes(int id,int page) {
        Pageable pageable = PageRequest.of(page,10);
        return subredditRepository.findAllPostsByIdOrderByUpvotes(id,pageable);
    }

    @Override
    public Page<Post> findAllPostByIdOrderByUpdatedAt(int id,int page) {
        Pageable pageable = PageRequest.of(page,10);
        return subredditRepository.findAllPostsByIdOrderByUpdatedAt(id,pageable);
    }
    @Override
    public void save(Subreddit subreddit) {
        subredditRepository.save(subreddit);
    }

    @Override
    public Subreddit findByName(String name) {
        return subredditRepository.findByName(name);
    }

    @Override
    public List<Post> findAllPostBySubredditContaining(int id, String content, String title) {
        return subredditRepository.findAllPostBySubredditContaining(id,content,title);
    }

    @Override
    public List<Subreddit> findAllByNameContaining(String search) {
        return subredditRepository.findAllByNameContaining(search);
    }

    @Override
    public List<Subreddit> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return subredditRepository.findAll(pageable).getContent();
    }
}
