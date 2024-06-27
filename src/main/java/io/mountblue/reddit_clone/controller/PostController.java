package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.dao.FileSystemRepository;
import io.mountblue.reddit_clone.dto.PostForm;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.Subreddit;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.FileLocationService;
import io.mountblue.reddit_clone.service.PostService;
import io.mountblue.reddit_clone.service.SubredditService;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private SubredditService subredditService;
    private UserService userService;
    private FileLocationService fileLocationService;

    @Autowired
    public PostController(PostService postService, SubredditService subredditService, UserService userService, FileLocationService fileLocationService, FileSystemRepository fileSystemRepository) {
        this.postService = postService;
        this.subredditService = subredditService;
        this.userService = userService;
        this.fileLocationService = fileLocationService;
    }

    @GetMapping("/newPost")
    public String getPostForm(Model model, @RequestParam("subredditId") int subredditId) {
        Post post = new Post();
        model.addAttribute("subredditId", subredditId);
        model.addAttribute("post", post);
        return "posts/new-post";
    }

    @PostMapping("/newPost")
    public String addNewPost(@ModelAttribute("post") PostForm form,
                             Principal principal) throws Exception {

        Post post = new Post();
        post.setPost(true);

        Subreddit subreddit = subredditService.findById(form.getSubredditId());
        post.setSubreddit(subreddit);

        User user = userService.findByUsername(principal.getName());
        post.setUser(user);

        post.setTitle(form.getTitle());
        post.setContent(form.getContent());

        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        if (form.getImage().getOriginalFilename() != "") {
            post.setImage(fileLocationService.save(form.getImage().getBytes(), form.getImage().getOriginalFilename()));
        }
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("postId") int postId,
                             @RequestParam("content") String content,
                             Principal principal) {
        Post post = postService.findById(postId);

        content = content.trim();
        if (content.isEmpty()) {
            return "redirect:/posts/" + postId;
        }

        Post comment = new Post();
        comment.setParent(post);
        comment.setContent(content);
        User user = userService.findByUsername(principal.getName());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        post.addComment(comment);
        postService.save(post);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/allposts")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/all-posts";
    }

    @GetMapping("/postsByUpvotes")
    public String getPostsByUpvotes(Model model) {
        model.addAttribute("posts", postService.findAllOrderByUpvotes());
        return "posts/all-posts";
    }
}
