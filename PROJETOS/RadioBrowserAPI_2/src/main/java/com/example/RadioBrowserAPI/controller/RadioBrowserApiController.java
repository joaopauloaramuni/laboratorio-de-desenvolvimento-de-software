package com.example.RadioBrowserAPI.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.service.FavoriteService;
import com.example.RadioBrowserAPI.service.RadioBrowserApiService;

@Controller
public class RadioBrowserApiController {

    private final RadioBrowserApiService radioBrowserApiService;
    private final FavoriteService favoriteService;

    public RadioBrowserApiController(RadioBrowserApiService radioBrowserApiService,
                                      FavoriteService favoriteService) {
        this.radioBrowserApiService = radioBrowserApiService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/")
    public String listRadioStations(
            @RequestParam(defaultValue = "Brazil") String country,
            @RequestParam(required = false, defaultValue = "Minas Gerais") String state,
            Model model) {

        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(country, state);

        // Marca quais estações já são favoritas
        radioStations.forEach(station ->
                station.setFavorite(favoriteService.isFavorite(station.getStationuuid())));

        // Sort estável: favoritas primeiro, preservando a ordenação por votos já aplicada
        // dentro de cada grupo (favoritas e não favoritas), já que List.sort é estável.
        radioStations.sort(Comparator.comparing(RadioStation::isFavorite).reversed());

        model.addAttribute("stations", radioStations);
        model.addAttribute("countries", radioBrowserApiService.listCountries());
        model.addAttribute("states", radioBrowserApiService.listStates());
        model.addAttribute("selectedCountry", country);
        model.addAttribute("selectedState", state);

        return "home";
    }

    @PostMapping("/favorites/toggle")
    public String toggleFavorite(
            @RequestParam String stationuuid,
            @RequestParam(defaultValue = "Brazil") String country,
            @RequestParam(required = false) String state) throws UnsupportedEncodingException {

        favoriteService.toggle(stationuuid);

        String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);
        String redirectUrl = "redirect:/?country=" + encodedCountry;

        if (StringUtils.hasText(state)) {
            redirectUrl += "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);
        }

        return redirectUrl;
    }
}