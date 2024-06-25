package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoteController {
    PostService postService;

    @Autowired
    VoteController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/upvote/{id}")
    public String upvote(@PathVariable int id) {
        Post p = postService.upvote(id);
        while(!p.isPost()) {
            p = p.getParent();
        }
        return "redirect:/posts/" + p.getId();
    }

    @GetMapping("/downvote/{id}")
    public String downvote(@PathVariable int id) {
        Post p = postService.downvote(id);
        while(!p.isPost()) {
            p = p.getParent();
        }
        return "redirect:/posts/" + p.getId();
    }
}
