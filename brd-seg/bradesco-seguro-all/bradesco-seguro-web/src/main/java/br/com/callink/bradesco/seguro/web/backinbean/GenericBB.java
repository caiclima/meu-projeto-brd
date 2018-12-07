package br.com.callink.bradesco.seguro.web.backinbean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse.Message;
import br.com.callink.bradesco.seguro.web.GenericBean;
import br.com.callink.bradesco.seguro.web.config.exception.DefaultExceptionHandler;
import br.com.callink.bradesco.seguro.web.util.JSFUtil;
import br.com.callink.bradesco.seguro.web.util.exception.ViewValidationException;




/**
 * Managed-Bean genérico.
 * 
 * @author michael
 * 
 */
public class GenericBB extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ResourceBundle i18n;

	public GenericBB() {
		i18n = ResourceBundle.getBundle("bundle.i18n");
	}

	/**
	 * Tratativa genérica de exceptions geradas para a view atual
	 * 
	 * @param throwable
	 */
	public void fail(Throwable throwable) {
		DefaultExceptionHandler.handle(throwable);
	}

	/**
	 * Falha com uma {@link ViewValidationException} baseada em
	 * internacionalização pela key e argumentos informados.
	 * 
	 * @param key
	 * @param args
	 */
	public void fail(String key, Object... args) {
		throw new ViewValidationException(format(key, args));
	}
	
	public void failMsg(String msg) {
		throw new ViewValidationException(msg);
	}

	/**
	 * Adiciona todas as mensagens a partir da resposta de requisição de serviço
	 * informada.
	 * 
	 * @param response
	 */
	public void addMsgs(ServiceResponse response) {
		if (response != null) {
			List<Message> msgs = response.getMessages();
			if (!CollectionUtils.isEmpty(msgs)) {
				for (Message msg : msgs) {
					switch (msg.getLevel()) {
					case WARNING:
						JSFUtil.addWarnMessage(msg.getMessage());
						break;
					case ERROR:
						JSFUtil.addErrorMessage(msg.getMessage());
						break;
					case INFO:
						JSFUtil.addInfoMessage(msg.getMessage());
						break;
					}
				}
			}
		}
	}

	/**
	 * Adiciona uma mensagem de 'erro' a partir da key do resource bundle
	 * informada.
	 * 
	 * @param key
	 * @param args
	 * @return - null outcome para manter na mesma pagina
	 */
	public String addErrorMessage(String key, Object... args) {
		JSFUtil.addErrorMessage(format(key, args));
		return null;
	}

	/**
	 * Adiciona mensagem de erro sem internacionalizar.
	 * 
	 * @param severity
	 * @param summary
	 * @param detail
	 */
	public static void addExceptionMessage(String summary) {
		JSFUtil.addErrorMessage(summary);
	}

	/**
	 * Adiciona uma mensagem 'informativa' a partir da key do resource bundle
	 * informada.
	 * 
	 * @param key
	 * @param args
	 * @return - null outcome para manter na mesma pagina
	 */
	public String addInfoMessage(String key, Object... args) {
		JSFUtil.addInfoMessage(format(key, args));
		return null;
	}

	/**
	 * Adiciona uma mensagem de 'warning' a partir da key do resource bundle
	 * informada.
	 * 
	 * @param key
	 * @param args
	 * @return - null outcome para manter na mesma pagina
	 */
	public String addWarnMessage(String key, Object... args) {
		JSFUtil.addWarnMessage(format(key, args));
		return null;
	}

	protected String format(String key, Object... args) {
		String msg = i18n.getString(key);
		return MessageFormat.format(msg, args);
	}
	
	protected ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
	
	protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }
	
	protected String getUsuarioHost() {
    	
    	StringBuilder host = new StringBuilder();
    	host.append(getRequest().getServerName()).append(":").append(getRequest().getServerPort());
        return host.toString();
        
    }
}