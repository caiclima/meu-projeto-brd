package br.com.callink.bradesco.seguro.web.faces.backingbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author michael
 *
 */
@ManagedBean
@SessionScoped
public class LocaleBB {
	
	/*
	 * Locale default 'pt_BR'. O Locale da aplicacao pode ser alterado
	 * a qualquer momento apenas configurando esta variavel via LocaleBB#setLocale(String locale).
	 */
	private String locale = "pt_BR";

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}