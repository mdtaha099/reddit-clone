package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.exception.UserAlreadyExistsException;
import io.mountblue.reddit_clone.entity.User;
import io.mountblue.reddit_clone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "user/signup-form";
    }
    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) throws UserAlreadyExistsException {
        userService.Save(user);
        return "redirect:/login";
    }
}
