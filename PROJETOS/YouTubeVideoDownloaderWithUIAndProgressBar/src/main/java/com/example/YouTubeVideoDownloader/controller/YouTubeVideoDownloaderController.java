package com.example.YouTubeVideoDownloader.controller;

import com.example.YouTubeVideoDownloader.service.YouTubeVideoDownloaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class YouTubeVideoDownloaderController {

    @Autowired
    private YouTubeVideoDownloaderService youTubeVideoDownloaderService;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @PostMapping("/download")
    @ResponseBody
    public String downloadVideo(@RequestParam("url") String videoUrl, @RequestParam("sessionId") String sessionId) {
        return youTubeVideoDownloaderService.downloadVideo(videoUrl, "videos", sessionId);
    }
}