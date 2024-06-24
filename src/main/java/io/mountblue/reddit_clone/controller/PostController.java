package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.dao.PostRepository;
import io.mountblue.reddit_clone.dao.SubredditRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.PostService;
import io.mountblue.reddit_clone.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private SubredditService subredditService;
    @Autowired
    public PostController(PostService postService,SubredditService subredditService) {
        this.postService = postService;
        this.subredditService = subredditService;
    }
    @GetMapping("/newPost")
    public String getPostForm(Model model,@RequestParam("subredditId") int subredditId) {
        Post post = new Post();
        model.addAttribute("subredditId",subredditId);
        model.addAttribute("post",post);
        return "posts/new-post";
    }
    @PostMapping("/newPost")
    public String addNewPost(@ModelAttribute("post") Post post,@RequestParam("subredditId") int subredditId) {
        post.setSubreddit(subredditService.findById(subredditId));
        post.setPost(true);
        postService.save(post);
        return "posts/show";
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
