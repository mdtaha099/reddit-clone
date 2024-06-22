package io.mountblue.reddit_clone.dao;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubredditRepository extends JpaRepository<Subreddit,Integer> {
}
