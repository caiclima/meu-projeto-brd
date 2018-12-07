package br.com.callink.bradesco.seguro.web.faces.cdi;

import javax.servlet.http.HttpSession;

import br.com.callink.sso.authlib.ws.session.WsSsoSession;

/**
 * Representa sinalização de que o logout do usuário deve acontecer
 * 
 * @author michael
 * 
 */
public class LogoutEvent {
	private final HttpSession session;
	private final WsSsoSession wsSsoSession;

	public HttpSession getSession() {
		return session;
	}

	public LogoutEvent(HttpSession session, WsSsoSession wsSsoSession) {
		this.session = session;
		this.wsSsoSession = wsSsoSession;
	}

	public WsSsoSession getWsSsoSession() {
		return wsSsoSession;
	}
}