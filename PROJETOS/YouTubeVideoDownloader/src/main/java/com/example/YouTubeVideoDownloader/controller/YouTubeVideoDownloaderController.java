package com.example.YouTubeVideoDownloader.controller;

import com.example.YouTubeVideoDownloader.service.YouTubeVideoDownloaderService;
import model.VideoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class YouTubeVideoDownloaderController {

    @Autowired
    private YouTubeVideoDownloaderService youTubeVideoDownloaderService;

    @PostMapping("/download")
    public String downloadVideo(@RequestBody VideoRequest videoRequest) {
        youTubeVideoDownloaderService.downloadVideo(videoRequest.getUrl(), videoRequest.getPath());
        return "Download iniciado!";
    }
}

