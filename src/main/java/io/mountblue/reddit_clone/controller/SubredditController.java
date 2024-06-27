package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.dao.SubredditRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.PostService;
import io.mountblue.reddit_clone.service.SubredditService;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SubredditController {
    private SubredditService subredditService;

    private PostService postService;
    private UserService userService;
    public SubredditController(SubredditService subredditService,UserService userService,PostService postService) {
        this.subredditService = subredditService;
        this.userService = userService;
        this.postService = postService;
    }
    @GetMapping("/newSubreddit")
    public String newSubreddit(Model model) {
        Subreddit subreddit = new Subreddit();
        model.addAttribute("subreddit",subreddit);
        return "subreddit/new-subreddit";
    }
    @PostMapping("/newSubreddit")
    public String saveSubreddit(@ModelAttribute Subreddit subreddit){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(loggedInUser.getName());
        subreddit.setAuthor(user);
        subredditService.save(subreddit);
        return "redirect:r/"+subreddit.getName();
    }
    @RequestMapping("r/{name}")
    public String getPosts(@PathVariable String name, Model model) {
        Subreddit subreddit = subredditService.findByName(name);
        List<Post> posts = subredditService.findAllPostById(subreddit.getId());
        model.addAttribute("subredditId",subreddit.getId());
        model.addAttribute("name",name);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }

    @GetMapping("r/{name}/new")
    public String getPostsOrderByDate(@PathVariable String name, Model model) {
        Subreddit subreddit = subredditService.findByName(name);
        List<Post> posts = subredditService.findAllPostByIdOrderByUpdatedAt(subreddit.getId());
        model.addAttribute("subredditId",subreddit.getId());
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }

    @GetMapping("r/{name}/top")
    public String getPostsOrderByUpvotes(@PathVariable String name, Model model) {
        Subreddit subreddit = subredditService.findByName(name);
        int id = subreddit.getId();
        List<Post> posts = subredditService.findAllPostByIdOrderByUpvotes(id);
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        return "subreddit/posts";
    }
    @GetMapping("r/{name}/join")
    public String joinSubreddit(@PathVariable String name,Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(loggedInUser.getName());
        Subreddit subreddit = subredditService.findByName(name);
        Set<User> usersBySubreddit = new HashSet<>(subreddit.getUsers());
        List<Post> posts = subredditService.findAllPostById(subreddit.getId());
        int subredditId = subreddit.getId();
        model.addAttribute("subredditId",subredditId);
        model.addAttribute("posts",posts);
        if(usersBySubreddit.contains(user)) {
            return "subreddit/posts";
        }
        subreddit.addUser(user);
        subredditService.save(subreddit);
        return "subreddit/posts";
    }
    @GetMapping("r/{name}/search/{search}")
    public String getAllPosts(@PathVariable String search, @PathVariable String name,Model model) {
        Subreddit subreddit = subredditService.findByName(name);
        List<Post> posts = subredditService.findAllPostBySubredditContaining(subreddit.getId(),search,search);
        model.addAttribute("posts",posts);
        return "search/search";
    }
    @GetMapping("/search/{search}")
    public String searchAll(@PathVariable String search,Model model) {
        List<Post> posts = postService.findAllByIsPostTrueAndContentContainingOrTitleContaining(search,search);
        model.addAttribute("posts",posts);
        return "search/search";
    }
}
