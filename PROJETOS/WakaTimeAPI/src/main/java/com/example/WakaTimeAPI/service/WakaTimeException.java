package com.example.WakaTimeAPI.service;

/**
 * Erro de configuração ou de permissão do WakaTime, com uma mensagem pronta
 * para mostrar na página.
 */
public class WakaTimeException extends RuntimeException {

	public enum Reason {
		/** O access token não foi configurado (ou ainda é SEU_..._AQUI). */
		MISSING_TOKEN,
		/** Token inválido, revogado ou expirado sem renovação (HTTP 401). */
		INVALID_TOKEN,
		/** O token não tem o escopo que o endpoint exige (HTTP 403). */
		FORBIDDEN,
		/** Perfil público: privado, período indisponível ou usuário inexistente. */
		PUBLIC_PROFILE
	}

	private final Reason reason;

	public WakaTimeException(Reason reason, String message) {
		this(reason, message, null);
	}

	public WakaTimeException(Reason reason, String message, Throwable cause) {
		super(message, cause);
		this.reason = reason;
	}

	public Reason getReason() {
		return reason;
	}
}
