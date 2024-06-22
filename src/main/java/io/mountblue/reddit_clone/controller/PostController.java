package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addComment")
    public String addComment(@RequestParam("postId") int postId,
                             @RequestParam("content") String content) {
        Post post = postService.findById(postId);

        content = content.trim();
        if (content.isEmpty()) {
            return "redirect:/posts/" + postId;
        }

        Post comment = new Post();
        comment.setParent(post);
        comment.setContent(content);
        post.addComment(comment);
        postService.save(post);
        return "redirect:/posts/" + postId;
    }
}
