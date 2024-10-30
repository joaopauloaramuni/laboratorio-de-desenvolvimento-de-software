package com.example.YouTubeVideoDownloaderWithUIAndProgressBar.controller;

import com.example.YouTubeVideoDownloaderWithUIAndProgressBar.service.YouTubeVideoDownloaderWithUIAndProgressBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class YouTubeVideoDownloaderWithUIAndProgressBarController {

    @Autowired
    private YouTubeVideoDownloaderWithUIAndProgressBarService youTubeVideoDownloaderWithUIAndProgressBarService;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @PostMapping("/download")
    @ResponseBody
    public String downloadVideo(@RequestParam("url") String videoUrl, @RequestParam("sessionId") String sessionId) {
        return youTubeVideoDownloaderWithUIAndProgressBarService.downloadVideo(videoUrl, "videos", sessionId);
    }
}