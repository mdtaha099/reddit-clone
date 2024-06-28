package io.mountblue.reddit_clone.controller;

import com.github.javafaker.Faker;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @PostMapping("/generateUserName")
    public String getUsernames(@RequestParam String username,Model model) {
        Faker faker = new Faker();
        List<String> usernames = new ArrayList<>();
        Set<String> existingUsernames = userService.findAllUsernames();
        username += "_";
        while (usernames.size() < 5) {
            String coolUserName = username+faker.superhero().name();
            coolUserName = coolUserName.replace(" ","_");
            if(existingUsernames.contains(coolUserName)) {
                continue;
            }
            usernames.add(coolUserName);
        }
        model.addAttribute("username",usernames);
        return "user/user-names";
    }
    @GetMapping("/generateUserName")
    public String getUserGenerationForm() {
        return "user/user-names";
    }
}
