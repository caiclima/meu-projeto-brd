package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import br.com.callink.bradesco.seguro.web.faces.config.exception.DefaultExceptionHandler;
import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;
import br.com.callink.bradesco.seguro.web.faces.utils.exception.ViewValidationException;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse.Message;

/**
 * Managed-Bean genérico.
 * 
 * @author michael
 * 
 */
public class GenericBB implements Serializable {
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
						JSFUtils.addWarnMessage(msg.getMessage());
						break;
					case ERROR:
						JSFUtils.addErrorMessage(msg.getMessage());
						break;
					case INFO:
						JSFUtils.addInfoMessage(msg.getMessage());
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
		JSFUtils.addErrorMessage(format(key, args));
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
		JSFUtils.addErrorMessage(summary);
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
		JSFUtils.addInfoMessage(format(key, args));
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
		JSFUtils.addWarnMessage(format(key, args));
		return null;
	}

	protected String format(String key, Object... args) {
		String msg = i18n.getString(key);
		return MessageFormat.format(msg, args);
	}
}