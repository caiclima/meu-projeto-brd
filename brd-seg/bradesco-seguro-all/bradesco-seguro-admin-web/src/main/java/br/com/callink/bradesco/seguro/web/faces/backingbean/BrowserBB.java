package br.com.callink.bradesco.seguro.web.faces.backingbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;

/**
 * 
 * @author michael
 * 
 */
@ManagedBean
@RequestScoped
public class BrowserBB {

	public boolean isInternetExplorer6(){
		String userAgent = JSFUtils.request().getHeader("user-agent");
		return userAgent != null && userAgent.toUpperCase().matches("^.*MSIE 6.0.*$");
	}
}