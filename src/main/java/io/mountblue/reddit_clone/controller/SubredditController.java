package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.SubredditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SubredditController {
    private SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }
    @RequestMapping("/subreddit/{id}")
    public String getPosts(@PathVariable String id, Model model) {
        List<Post> posts = subredditService.findAllPostById(Integer.parseInt(id));
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }
}
