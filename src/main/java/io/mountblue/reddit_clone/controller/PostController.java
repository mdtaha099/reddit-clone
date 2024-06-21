package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }
}
