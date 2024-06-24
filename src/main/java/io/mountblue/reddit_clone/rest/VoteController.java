package io.mountblue.reddit_clone.rest;

import io.mountblue.reddit_clone.dto.VoteResponse;
import io.mountblue.reddit_clone.entity.Post;
import io.mountblue.reddit_clone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteController {
    PostService postService;

    @Autowired
    VoteController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/upvote/{id}")
    public ResponseEntity<VoteResponse> upvote(@PathVariable int id) {
        VoteResponse voteResponse = postService.upvote(id);
        System.out.println("voteResponse: " + voteResponse);
        if (voteResponse.getPostId() == 0) {
            return new ResponseEntity<>(voteResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(voteResponse, HttpStatus.OK);
    }

    @GetMapping("/downvote/{id}")
    public ResponseEntity<VoteResponse> downvote(@PathVariable int id) {
        VoteResponse voteResponse = postService.downvote(id);
        System.out.println("voteResponse: " + voteResponse);
        if (voteResponse.getPostId() == 0) {
            return new ResponseEntity<>(voteResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(voteResponse, HttpStatus.OK);
    }
}
