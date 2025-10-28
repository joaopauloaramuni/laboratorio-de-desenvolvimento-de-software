package com.example.IBGE_Locations.controller;

import com.example.IBGE_Locations.service.IbgeLocationsService;
import com.example.IBGE_Locations.model.Uf;
import com.example.IBGE_Locations.model.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class IbgeLocationsController {

    @Autowired
    private IbgeLocationsService ibgeLocationsService;

    @GetMapping("/")
    public String home(Model model) {
        List<Uf> ufs = ibgeLocationsService.obterUfs();
        model.addAttribute("ufs", ufs);
        return "home";
    }

    @GetMapping("/municipios/{ufSigla}")
    @ResponseBody
    public List<Municipio> getMunicipios(@PathVariable String ufSigla) {
        return ibgeLocationsService.obterMunicipiosPorUf(ufSigla);
    }
}
