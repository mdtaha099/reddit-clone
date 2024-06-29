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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SubredditController {
    private SubredditService subredditService;

    private PostService postService;
    private UserService userService;
    private String url = "";
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
        subreddit.addUser(user);
        subredditService.save(subreddit);
        return "redirect:r/"+subreddit.getName();
    }
    @RequestMapping("r/{name}")
    public String getPosts(@PathVariable String name,
                           Model model,
                           @RequestParam(defaultValue = "0") int page) {
        Subreddit subreddit = subredditService.findByName(name);
        Page<Post> posts = subredditService.findAllPostById(subreddit.getId(),page);
        url = "/r/"+name;
        System.out.println(url);
        model.addAttribute("subredditId",subreddit.getId());
        model.addAttribute("name",name);
        model.addAttribute("posts",posts);
        model.addAttribute("url",url);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",posts.getTotalPages());
        return "subreddit/posts";
    }

    @GetMapping("r/{name}/new")
    public String getPostsOrderByDate(@PathVariable String name,
                                      Model model,
                                      @RequestParam(defaultValue = "0") int page) {
        Subreddit subreddit = subredditService.findByName(name);
        Page<Post> posts = subredditService.findAllPostByIdOrderByUpdatedAt(subreddit.getId(),page);
        url = "/r/"+name+"/new";
        System.out.println(url);
        model.addAttribute("subredditId",subreddit.getId());
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",posts.getTotalPages());
        model.addAttribute("url",url);
        return "subreddit/posts";
    }

    @GetMapping("r/{name}/top")
    public String getPostsOrderByUpvotes(@PathVariable String name,
                                         Model model,
                                         @RequestParam(defaultValue = "0") int page) {
        Subreddit subreddit = subredditService.findByName(name);
        int id = subreddit.getId();
        Page<Post> posts = subredditService.findAllPostByIdOrderByUpvotes(id,page);
        url = "/r/"+name+"/top";
        System.out.println(url);
        model.addAttribute("subredditId",id);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",posts.getTotalPages());
        model.addAttribute("url",url);
        return "subreddit/posts";
    }
    @GetMapping("r/{name}/join")
    public String joinSubreddit(@PathVariable String name,Model model,@RequestParam(defaultValue = "0") int page) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(loggedInUser.getName());
        Subreddit subreddit = subredditService.findByName(name);
        Set<User> usersBySubreddit = new HashSet<>(subreddit.getUsers());
        Page<Post> posts = subredditService.findAllPostById(subreddit.getId(), page);
        int subredditId = subreddit.getId();
        model.addAttribute("subredditId",subredditId);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",posts.getTotalPages());
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
        List<Subreddit> subreddits = subredditService.findAllByNameContaining(search);
        //model.addAttribute("subreddits",subreddits);
        model.addAttribute("posts",posts);
        url = "/r/"+name+"/search";
        model.addAttribute("url",url);
        return "search/subreddit-search";
    }
    @GetMapping("/search/{search}")
    public String searchAll(@PathVariable String search,Model model) {
        List<Post> posts = postService.findAllByIsPostTrueAndContentContainingOrTitleContaining(search,search);
        List<Subreddit> subreddits = subredditService.findAllByNameContaining(search);
        model.addAttribute("subreddits",subreddits);
        model.addAttribute("posts",posts);
        return "search/search";
    }

    @GetMapping("/search")
    public String searchHome(@RequestParam String search) {
        return "redirect:/search/"+search;
    }

    @GetMapping("/r/{sub}/search")
    public String searchSubreddit(@PathVariable String sub,
                                  @RequestParam("search") String search,Model model) {
        url = "/r/"+sub+"/search";
        model.addAttribute("url",url);
        return "redirect:/r/" + sub + "/search/"+search;
    }
}
