package br.com.callink.bradesco.seguro.web.faces.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author michael
 * 
 */
public final class JSFUtils {
	public static FacesContext context() {
		return FacesContext.getCurrentInstance();
	}
	
	public static void renderResponse(){
		context().renderResponse();
	}
	
	public static void redirect(String view) throws IOException{
		context().getExternalContext().redirect(view);
	}
	
	public static Object getContextAttribute(String name){
		return request().getServletContext().getInitParameter(name);
	}
	
	public static void responseComplete(){
		context().responseComplete();
	}
	
	public static HttpServletRequest request(){
		return (HttpServletRequest) context().getExternalContext().getRequest();
	}
	
	public static HttpServletResponse response(){
		return (HttpServletResponse) context().getExternalContext().getResponse();
	}

	public static void addMessage(Severity severity, String summary) {
		addMessage(severity, summary, null);
	}

	public static void addErrorMessage(String summary) {
		addMessage(FacesMessage.SEVERITY_ERROR, summary, null);
	}

	public static void addInfoMessage(String summary) {
		addMessage(FacesMessage.SEVERITY_INFO, summary, null);
	}

	public static void addWarnMessage(String summary) {
		addMessage(FacesMessage.SEVERITY_WARN, summary, null);
	}

	public static void addMessage(Severity severity, String summary, String detail) {
		context().addMessage(null, new FacesMessage(severity, summary, detail));
	}
	
	@SuppressWarnings("rawtypes")
	public static List<SelectItem> toSelectItem(final List itens) {
		List<SelectItem> retorno = new ArrayList<SelectItem>();
		if (itens != null) {
			for (Object item : itens) {
				SelectItem selectItem = new SelectItem(item, item.toString());
				retorno.add(selectItem);
			}
		}
		return retorno;
	}

	@SuppressWarnings("rawtypes")
	public static List<SelectItem> toSelectItemConsulta(final List itens) {
		return toSelectItemConsulta(itens, "Selecione...");
	}

	@SuppressWarnings("rawtypes")
	public static List<SelectItem> toSelectItemConsulta(final List itens, String firstItemText) {
		List<SelectItem> retorno = new ArrayList<SelectItem>();
		if (itens != null) {
			retorno.add(new SelectItem(null, firstItemText));
			for (Object item : itens) {
				SelectItem selectItem = new SelectItem(item, item.toString());
				retorno.add(selectItem);
			}
		}
		return retorno;
	}
}