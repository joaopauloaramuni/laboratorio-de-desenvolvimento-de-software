package com.example.GifFinder.response;

import com.example.GifFinder.data.GifData;

import java.util.List;

public class GifResponse {
    private List<GifData> data;

    public List<GifData> getData() {
        return data;
    }

    public void setData(List<GifData> data) {
        this.data = data;
    }
}
