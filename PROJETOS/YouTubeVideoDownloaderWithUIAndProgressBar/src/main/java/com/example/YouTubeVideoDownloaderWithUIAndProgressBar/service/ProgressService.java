package com.example.YouTubeVideoDownloaderWithUIAndProgressBar.service;

import com.example.YouTubeVideoDownloaderWithUIAndProgressBar.controller.ProgressController;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    public void sendProgress(String sessionId, String progress) {
        ProgressController.sendProgress(sessionId, progress);
    }
}