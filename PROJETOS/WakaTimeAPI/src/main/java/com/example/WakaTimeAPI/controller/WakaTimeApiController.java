package com.example.WakaTimeAPI.controller;

import com.example.WakaTimeAPI.config.ApiConfig;
import com.example.WakaTimeAPI.model.CodingNow;
import com.example.WakaTimeAPI.model.StatsView;
import com.example.WakaTimeAPI.service.CodingNowService;
import com.example.WakaTimeAPI.service.WakaTimeApiService;
import com.example.WakaTimeAPI.service.WakaTimeException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Controller
public class WakaTimeApiController {

	@Autowired
	private WakaTimeApiService wakaTimeApiService;

	@Autowired
	private CodingNowService codingNowService;

	@Autowired
	private ApiConfig apiConfig;

	/**
	 * Página com o painel. GET /?range=last_7_days troca o período
	 * (last_7_days, last_30_days, last_6_months, last_year, all_time).
	 */
	@GetMapping("/")
	public String home(@RequestParam(required = false) String range, Model model) {
		String selected = wakaTimeApiService.validRange(range);
		model.addAttribute("range", selected);
		model.addAttribute("ranges", WakaTimeApiService.RANGES);
		model.addAttribute("profileUrl", apiConfig.getProfileUrl());
		model.addAttribute("refreshSeconds", apiConfig.getRefreshSeconds());

		try {
			StatsView stats = wakaTimeApiService.getStats(selected);
			if (stats.isPending()) {
				// HTTP 202: o WakaTime ainda está calculando as estatísticas
				model.addAttribute("aviso", "O WakaTime ainda está calculando este período. Recarregue em alguns segundos.");
			} else {
				model.addAttribute("stats", stats);
			}
		} catch (WakaTimeException e) {
			// Token faltando, inválido ou sem o escopo read_stats
			model.addAttribute("erro", e.getMessage());
		} catch (RestClientException e) {
			model.addAttribute("erro", "Não foi possível falar com o WakaTime agora.");
		}
		return "home";
	}

	/**
	 * Cartão "Codando agora" em HTML (fragmento do Thymeleaf). A página busca
	 * este endereço a cada minuto (static/js/codando-agora.js).
	 *   200 → o cartão
	 *   204 → sem token ou sem o escopo read_heartbeats: a página para de perguntar
	 *   503 → erro passageiro: a página esconde o cartão e tenta de novo
	 */
	@GetMapping("/fragmentos/codando-agora")
	public String codingNowFragment(Model model, HttpServletResponse response) {
		try {
			model.addAttribute("now", codingNowService.getCodingNow());
			return "fragments/codando-agora :: card";
		} catch (WakaTimeException e) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
		} catch (RestClientException e) {
			response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		}
		return null; // com o HttpServletResponse, null = resposta já pronta (sem corpo)
	}

	/**
	 * O mesmo "Codando agora" em JSON, para testar ou usar em outro front-end:
	 * GET /api/codando → { "active": true, "todaySeconds": 15600, "last": {...} }
	 */
	@GetMapping("/api/codando")
	@ResponseBody
	public ResponseEntity<Object> codingNowJson() {
		try {
			CodingNow now = codingNowService.getCodingNow();
			return ResponseEntity.ok(now);
		} catch (WakaTimeException e) {
			HttpStatus status = switch (e.getReason()) {
				case MISSING_TOKEN -> HttpStatus.SERVICE_UNAVAILABLE;
				case INVALID_TOKEN -> HttpStatus.UNAUTHORIZED;
				case FORBIDDEN -> HttpStatus.FORBIDDEN;
				case PUBLIC_PROFILE -> HttpStatus.SERVICE_UNAVAILABLE; // o Codando agora não usa o perfil público
			};
			return ResponseEntity.status(status).body(Map.of("error", e.getReason().name().toLowerCase(), "message", e.getMessage()));
		} catch (RestClientException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(Map.of("error", "upstream_error", "message", "Não foi possível falar com o WakaTime agora."));
		}
	}
}
