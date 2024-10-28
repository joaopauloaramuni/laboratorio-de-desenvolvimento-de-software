package com.example.YouTubeVideoDownloader.controller;

import com.example.YouTubeVideoDownloader.dto.VideoRequestDTO;
import com.example.YouTubeVideoDownloader.service.YouTubeVideoDownloaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class YouTubeVideoDownloaderController {

    @Autowired
    private YouTubeVideoDownloaderService youTubeVideoDownloaderService;

    @PostMapping("/download")
    public String downloadVideo(@RequestBody VideoRequestDTO videoRequestDTO) {
        return youTubeVideoDownloaderService.downloadVideo(videoRequestDTO.getUrl(), videoRequestDTO.getPath());
    }
}
