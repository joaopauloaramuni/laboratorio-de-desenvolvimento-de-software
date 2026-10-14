package com.example.WakaTimeAPI.util;

import com.example.WakaTimeAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Formatação usada nos templates (pt-BR). No Thymeleaf, o bean se chama
 * "fmt": ${@fmt.duration(stats.totalSeconds)} → "127 h 8 min".
 */
@Component("fmt")
public class WakaTimeFormat {

	private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

	@Autowired
	private ApiConfig apiConfig;

	/* =====================================================================
	   Números e datas
	   ===================================================================== */

	/** 457722 s → "127 h 8 min" (arredonda para baixo, como o WakaTime). */
	public String duration(double seconds) {
		long minutes = (long) Math.floor(Math.max(0, seconds) / 60);
		long h = minutes / 60;
		long m = minutes % 60;
		if (h == 0) {
			return m + " min";
		}
		return m > 0 ? h + " h " + m + " min" : h + " h";
	}

	/** 31.76 → "31,8%". */
	public String percent(double percent) {
		if (percent > 0 && percent < 0.1) {
			return "<0,1%";
		}
		NumberFormat format = NumberFormat.getPercentInstance(PT_BR);
		format.setMinimumFractionDigits(1);
		format.setMaximumFractionDigits(1);
		return format.format(percent / 100).replace('\u00a0', ' ');
	}

	/** 1234 → "1.234". */
	public String number(long value) {
		return NumberFormat.getIntegerInstance(PT_BR).format(value);
	}

	/** "2025-09-01" ou "2025-09-01T03:00:00Z" → "1 de set. de 2025" (null se inválida). */
	public String date(String value) {
		if (value == null || value.length() < 10) {
			return null;
		}
		try {
			LocalDate date = LocalDate.parse(value.substring(0, 10));
			return DateTimeFormatter.ofPattern("d 'de' MMM 'de' yyyy", PT_BR).format(date);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/** timestamp em ms → "14:32", no fuso da conta do WakaTime. */
	public String time(long ms) {
		return DateTimeFormatter.ofPattern("HH:mm").withZone(apiConfig.getZoneId()).format(Instant.ofEpochMilli(ms));
	}

	/** Quanto tempo atrás: "agora há pouco" | "há 3 min" | "há 2 h". */
	public String ago(long ms) {
		long minutes = Math.max(0, (System.currentTimeMillis() - ms) / 60000);
		if (minutes < 1) {
			return "agora há pouco";
		}
		if (minutes < 60) {
			return "há " + minutes + " min";
		}
		return "há " + (minutes / 60) + " h";
	}

	/** Largura de uma barra: 30 de 120 → "25.0%" (com ponto, para o CSS). */
	public String width(double value, double max) {
		double percent = max > 0 ? Math.min(100, value * 100 / max) : 0;
		return String.format(Locale.ROOT, "%.1f%%", percent);
	}

	/** Minibarras (editores, sistemas, categorias): no mínimo 2% para aparecer. */
	public String miniWidth(double percent) {
		return width(Math.max(percent, 2), 100);
	}

	/* =====================================================================
	   Nomes que o WakaTime manda em inglês → português
	   ===================================================================== */

	private static final Map<String, String> CATEGORY_NAMES = Map.ofEntries(
			Map.entry("Coding", "Programando"),
			Map.entry("AI Coding", "Programando com IA"),
			Map.entry("Writing Docs", "Escrevendo documentação"),
			Map.entry("Writing Tests", "Escrevendo testes"),
			Map.entry("Debugging", "Depurando"),
			Map.entry("Building", "Compilando"),
			Map.entry("Browsing", "Navegando"),
			Map.entry("Code Reviewing", "Revisando código"),
			Map.entry("Designing", "Design"),
			Map.entry("Meeting", "Reuniões"),
			Map.entry("Researching", "Pesquisando"),
			Map.entry("Learning", "Aprendendo"),
			Map.entry("Planning", "Planejando"));

	public String categoryName(String name) {
		return CATEGORY_NAMES.getOrDefault(name, name);
	}

	public String osName(String name) {
		return "Mac".equals(name) ? "macOS" : name;
	}

	// Nome do editor como o WakaTime manda (user_agents) → como aparece na tela
	private static final Map<String, String> EDITOR_NAMES = Map.ofEntries(
			Map.entry("vscode", "VS Code"),
			Map.entry("cursor", "Cursor"),
			Map.entry("windsurf", "Windsurf"),
			Map.entry("vscodium", "VSCodium"),
			Map.entry("zed", "Zed"),
			Map.entry("vim", "Vim"),
			Map.entry("neovim", "Neovim"),
			Map.entry("emacs", "Emacs"),
			Map.entry("sublime", "Sublime Text"),
			Map.entry("intellij", "IntelliJ IDEA"),
			Map.entry("idea", "IntelliJ IDEA"),
			Map.entry("pycharm", "PyCharm"),
			Map.entry("webstorm", "WebStorm"),
			Map.entry("androidstudio", "Android Studio"),
			Map.entry("xcode", "Xcode"),
			Map.entry("eclipse", "Eclipse"),
			Map.entry("visualstudio", "Visual Studio"),
			Map.entry("jupyter", "Jupyter"));

	public String editorName(String editor) {
		if (editor == null || editor.isBlank()) {
			return null;
		}
		String key = editor.toLowerCase().replaceAll("[\\s_-]", "");
		return EDITOR_NAMES.getOrDefault(key, Character.toUpperCase(editor.charAt(0)) + editor.substring(1));
	}

	/* =====================================================================
	   Cor de cada linguagem (as mesmas do GitHub Linguist)
	   ===================================================================== */

	private static final Map<String, String> LANGUAGE_COLORS = new HashMap<>();

	static {
		String[][] colors = {
				{"Python", "#3572A5"}, {"JavaScript", "#f1e05a"}, {"TypeScript", "#3178c6"},
				{"Java", "#b07219"}, {"Kotlin", "#A97BFF"}, {"C", "#555555"}, {"C++", "#f34b7d"},
				{"C#", "#178600"}, {"Go", "#00ADD8"}, {"Rust", "#dea584"}, {"PHP", "#4F5D95"},
				{"Ruby", "#701516"}, {"Swift", "#F05138"}, {"Dart", "#00B4AB"}, {"HTML", "#e34c26"},
				{"CSS", "#663399"}, {"SCSS", "#c6538c"}, {"Sass", "#a53b70"}, {"Vue", "#41b883"},
				{"Vue.js", "#41b883"}, {"JSX", "#61dafb"}, {"TSX", "#3178c6"}, {"Markdown", "#083fa1"},
				{"JSON", "#cbcb41"}, {"YAML", "#cb171e"}, {"XML", "#0060ac"}, {"SQL", "#e38c00"},
				{"Bash", "#89e051"}, {"Shell", "#89e051"}, {"PowerShell", "#012456"},
				{"Docker", "#384d54"}, {"Dockerfile", "#384d54"}, {"PlantUML", "#fbbd16"},
				{"TeX", "#3D6117"}, {"LaTeX", "#3D6117"}, {"Java Properties", "#2A6277"},
				{"Properties", "#2A6277"}, {"Groovy", "#4298b8"}, {"Gradle", "#02303a"},
				{"Text", "#94a3b8"}, {"Other", "#cbd5e1"},
		};
		for (String[] pair : colors) {
			LANGUAGE_COLORS.put(pair[0], pair[1]);
		}
	}

	/** Linguagem que não estiver na lista ganha uma cor estável gerada pelo nome. */
	public String color(String language) {
		if (language == null) {
			return "#94a3b8";
		}
		String known = LANGUAGE_COLORS.get(language);
		if (known != null) {
			return known;
		}
		int hash = 0;
		for (char c : language.toCharArray()) {
			hash = hash * 31 + c;
		}
		return "hsl(" + (Math.abs(hash % 360)) + ", 55%, 50%)";
	}
}
