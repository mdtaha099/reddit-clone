package io.mountblue.reddit_clone.service;

import io.mountblue.reddit_clone.dao.SubredditRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubredditServiceImpl implements SubredditService{
    private SubredditRepository subredditRepository;
    public SubredditServiceImpl(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    public List<Post> findAllPostById(int id) {
        return subredditRepository.findAllPostsById(id);
    }

    public Subreddit findById(int id) {
        Subreddit subreddit = subredditRepository.findById(id).get();
        if (subreddit == null) {
            throw new RuntimeException("invalid subreddit id");
        }
        return subreddit;
    }
}
