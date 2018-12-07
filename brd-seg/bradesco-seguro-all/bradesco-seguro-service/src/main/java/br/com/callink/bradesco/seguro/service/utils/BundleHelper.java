package br.com.callink.bradesco.seguro.service.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 * Classe BundleHelper, classe para trabalhar com Resource Bundle.
 * 
 * @author Felipe A. Braga<br>
 *         felipea@swb.com.br<br>
 *         SWB Soluções Integradas - www.swb.com.br<br>
 *         24/09/2012
 */
public final class BundleHelper {

	private BundleHelper() {
		
	}
	
	/**
	 * Obtém mensagem do bundle, pelo chave enviado.
	 * 
	 * @param messageKey
	 * @return a mensagem contida na chave
	 */
	public static String getMessage(String resourceBundleKey, String resourceBundleName) throws MissingResourceException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, resourceBundleName);
		return bundle.getString(resourceBundleKey);
	}

	/**
	 * Obtém mensagem do bundle montando com parametros
	 * 
	 * @param messageKey
	 * @param parametros
	 * @return a mensagem contida na chave, montada com seus respectivos
	 *         parametros
	 */
	public static String getMessageWithParam(String messageKey, String resourceBundleKey, Object... parametros) {
		return MessageFormat.format(getMessage(messageKey, resourceBundleKey), parametros);
	}
}
