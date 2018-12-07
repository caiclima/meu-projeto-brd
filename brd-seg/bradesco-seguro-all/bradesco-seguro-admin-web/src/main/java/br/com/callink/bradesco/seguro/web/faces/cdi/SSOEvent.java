package br.com.callink.bradesco.seguro.web.faces.cdi;

import javax.servlet.http.HttpSession;

import br.com.callink.sso.authlib.ws.session.WsSsoSession;

/**
 * Representa o Evento que indica que informações do SSO já estão
 * disponíveis para o usuário logado;
 * 
 * @author michael
 * 
 */
public class SSOEvent {
	private String username;
	private WsSsoSession wsSsoSession;
	private HttpSession session;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public WsSsoSession getWsSsoSession() {
		return wsSsoSession;
	}

	public void setWsSsoSession(WsSsoSession wsSsoSession) {
		this.wsSsoSession = wsSsoSession;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
}