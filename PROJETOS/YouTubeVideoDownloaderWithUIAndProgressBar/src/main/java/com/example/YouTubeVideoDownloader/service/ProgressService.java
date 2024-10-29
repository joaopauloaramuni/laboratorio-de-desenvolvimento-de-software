package com.example.YouTubeVideoDownloader.service;

import com.example.YouTubeVideoDownloader.controller.ProgressController;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    public void sendProgress(String sessionId, String progress) {
        ProgressController.sendProgress(sessionId, progress);
    }
}