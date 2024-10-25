package com.example.GetSubs.controller;

import com.example.GetSubs.dto.DownloadByMovieNameRequestDTO;
import com.example.GetSubs.dto.DownloadRequestDTO;
import com.example.GetSubs.dto.LoginRequestDTO;
import com.example.GetSubs.dto.SearchRequestDTO;
import com.example.GetSubs.model.Download;
import com.example.GetSubs.service.GetSubsService;
import com.example.GetSubs.model.Subtitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/getsubs")
public class GetSubsController {
    @Autowired
    private GetSubsService getSubsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        String token = getSubsService.authenticateUser(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Subtitle>> searchSubtitles(@RequestBody SearchRequestDTO searchRequest) {
        List<Subtitle> subtitles = getSubsService.searchSubtitles(searchRequest);
        return ResponseEntity.ok(subtitles);
    }

    @PostMapping("/download")
    public ResponseEntity<Download> downloadSubtitle(@RequestBody DownloadRequestDTO downloadRequest, @RequestHeader("Authorization") String token) {
        Download downloadUrl = getSubsService.downloadSubtitle(downloadRequest, token);
        return ResponseEntity.ok(downloadUrl);
    }

    @PostMapping("/downloadByMovieName")
    public ResponseEntity<String> downloadByMovieName(@RequestBody DownloadByMovieNameRequestDTO downloadByMovieNameRequest) {
        String downloadUrl = getSubsService.downloadSubtitleByMovieName(downloadByMovieNameRequest);
        return ResponseEntity.ok(downloadUrl);
    }
}
