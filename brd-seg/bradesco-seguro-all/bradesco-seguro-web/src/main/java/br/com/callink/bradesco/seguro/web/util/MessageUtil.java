package br.com.callink.bradesco.seguro.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Classe MessageUtil
 * 
 * @author Felipe A. Braga <br>
 *         17/06/2012 03:54:11<br>
 * 
 */
public abstract class MessageUtil {

	private static void addMessage(String clientId, Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
	}

	public static void addErrorMessage(String summary, String detail) {
		addMessage(null, FacesMessage.SEVERITY_ERROR, summary, detail);
	}

	public static void addInfoMessage(String summary, String detail) {
		addMessage(null, FacesMessage.SEVERITY_INFO, summary, detail);
	}

	public static void addWarnMessage(String summary, String detail) {
		addMessage(null, FacesMessage.SEVERITY_WARN, summary, detail);
	}

	public static void addFatalMessage(String summary, String detail) {
		addMessage(null, FacesMessage.SEVERITY_FATAL, summary, detail);
	}

	public static void addErrorMessage(String summary) {
		addMessage(null, FacesMessage.SEVERITY_ERROR, summary, null);
	}

	public static void addInfoMessage(String summary) {
		addMessage(null, FacesMessage.SEVERITY_INFO, summary, null);
	}

	public static void addWarnMessage(String summary) {
		addMessage(null, FacesMessage.SEVERITY_WARN, summary, null);
	}

	public static void addFatalMessage(String summary) {
		addMessage(null, FacesMessage.SEVERITY_FATAL, summary, null);
	}
}
