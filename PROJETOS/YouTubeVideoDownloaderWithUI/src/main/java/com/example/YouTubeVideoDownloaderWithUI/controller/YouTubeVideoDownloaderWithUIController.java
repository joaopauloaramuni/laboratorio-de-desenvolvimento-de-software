package com.example.YouTubeVideoDownloaderWithUI.controller;

import com.example.YouTubeVideoDownloaderWithUI.service.YouTubeVideoDownloaderWithUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/videos")
@Controller
public class YouTubeVideoDownloaderWithUIController {

    @Autowired
    private YouTubeVideoDownloaderWithUIService youTubeVideoDownloaderWithUIService;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @PostMapping("/download")
    @ResponseBody
    public String downloadVideo(@RequestParam("url") String videoUrl) {
        return youTubeVideoDownloaderWithUIService.downloadVideo(videoUrl, "videos");
    }
}
