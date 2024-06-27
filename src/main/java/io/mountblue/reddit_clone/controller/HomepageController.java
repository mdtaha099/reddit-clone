package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.PostService;
import io.mountblue.reddit_clone.service.SubredditService;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomepageController {
    PostService postService;
    UserService userService;
    SubredditService subredditService;

    public HomepageController(PostService postService, UserService userService, SubredditService subredditService) {
        this.postService = postService;
        this.userService = userService;
        this.subredditService = subredditService;
    }

    @GetMapping("/")
    public String getAllPosts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            model.addAttribute("posts", postService.findLatestPosts());
        } else {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(loggedInUser.getName());
            model.addAttribute("posts", postService.findLatestPosts(user));
        }
        return "homepage";
    }

    @GetMapping("/byUpvotes")
    public String getAllPostsByUpvotes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            model.addAttribute("posts", postService.findTopPosts());
        } else {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(loggedInUser.getName());
            model.addAttribute("posts", postService.findTopPosts(user));
        }
        return "homepage";
    }
}
