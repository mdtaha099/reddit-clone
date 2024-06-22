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
        Optional<Subreddit> subreddit = subredditRepository.findById(id);
        if(subreddit.isEmpty()) {
            throw new RuntimeException("not found");
        }
        return subreddit.get().getPosts();
    }
}
