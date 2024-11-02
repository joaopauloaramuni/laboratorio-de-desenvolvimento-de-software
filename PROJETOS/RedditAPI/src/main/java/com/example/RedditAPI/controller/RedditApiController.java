package com.example.RedditAPI.controller;

import com.example.RedditAPI.service.RedditApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reddit")
public class RedditApiController {

    @Autowired
    private RedditApiService redditApiService;

    @GetMapping("/discussions/{query}")
    public ResponseEntity<String> getDiscussions(@PathVariable String query) {
        return ResponseEntity.ok(redditApiService.searchDiscussions(query));
    }

    @GetMapping("/discussions/{community}/{query}")
    public ResponseEntity<String> getDiscussionsByComunity(@PathVariable String community, @PathVariable String query) {
        return ResponseEntity.ok(redditApiService.searchDiscussionsByComunity(community, query));
    }
}
