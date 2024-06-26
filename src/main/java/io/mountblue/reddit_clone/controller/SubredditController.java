package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.dao.SubredditRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.SubredditService;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/subreddit")
public class SubredditController {
    private SubredditService subredditService;
    private UserService userService;
    public SubredditController(SubredditService subredditService,UserService userService) {
        this.subredditService = subredditService;
        this.userService = userService;
    }

    @RequestMapping("/{id}")
    public String getPosts(@PathVariable String id, Model model) {
        List<Post> posts = subredditService.findAllPostById(Integer.parseInt(id));
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }

    @GetMapping("/postsOrderByDate/{id}")
    public String getPostsOrderByDate(@PathVariable String id, Model model) {
        List<Post> posts = subredditService.findAllPostByIdOrderByUpdatedAt(Integer.parseInt(id));
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }

    @GetMapping("/postsByUpvotes/{id}")
    public String getPostsOrderByUpvotes(@PathVariable String id, Model model) {
        List<Post> posts = subredditService.findAllPostByIdOrderByUpvotes(Integer.parseInt(id));
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }
    @GetMapping("/join")
    public String joinSubreddit(@RequestParam int subredditId) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(loggedInUser.getName());
        Subreddit subreddit = subredditService.findById(subredditId);
        Set<User> usersBySubreddit = new HashSet<>(subreddit.getUsers());
        if(usersBySubreddit.contains(user)) {
            return "redirect:/subreddit/" + subredditId;
        }
        subreddit.addUser(user);
        subredditService.save(subreddit);
        return "redirect:/subreddit/" + subredditId;
    }

}
