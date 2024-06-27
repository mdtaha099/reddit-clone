package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.PostService;
import io.mountblue.reddit_clone.service.SubredditService;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomepageController {
    private PostService postService;
    private UserService userService;
    private SubredditService subredditService;

    public HomepageController(PostService postService, UserService userService, SubredditService subredditService) {
        this.postService = postService;
        this.userService = userService;
        this.subredditService = subredditService;
    }

    @GetMapping("/")
    public String getAllPosts(Model model,@RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            Page<Post> posts = postService.findLatestPosts(page);
            model.addAttribute("posts", posts);
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",posts.getTotalPages());
        } else {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(loggedInUser.getName());
            Page<Post> posts = postService.findLatestPosts(user,page);
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",posts.getTotalPages());
            model.addAttribute("posts", posts);
        }
        return "homepage";
    }

    @GetMapping("/byUpvotes")
    public String getAllPostsByUpvotes(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals("anonymousUser")) {
            Page<Post> posts = postService.findTopPosts(page);
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",posts.getTotalPages());
            model.addAttribute("posts", posts);
        } else {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(loggedInUser.getName());
            Page<Post> posts = postService.findTopPosts(user,page);
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",posts.getTotalPages());
            model.addAttribute("posts", posts);
        }
        return "homepage";
    }
}
