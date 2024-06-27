package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.dao.UserRepository;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.exception.UserAlreadyExistsException;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/signup-form";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) throws UserAlreadyExistsException {
        userService.Save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Post> postsByUser = userService.findAllPostsByUserId(user.getId());
        model.addAttribute("posts",postsByUser);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
