package br.com.callink.bradesco.seguro.web;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.web.util.JSFUtil;



public abstract class GenericBean implements Serializable {

	private static final long serialVersionUID = 9051082066064154561L;
	private final Logger logger = Logger.getLogger(getClass());
	private final ResourceBundle i18n;
	
	public GenericBean() {
		i18n = ResourceBundle.getBundle("bundle.i18n");
	}

	 protected void log(String message) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if (context != null) {
	            getExternalContext().log(message);
	        } else {
	            logger.info(message);
	        }
	    }

	    protected void log(String message, Throwable throwable) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if (context != null) {
	            getExternalContext().log(message, throwable);
	        } else {
	            logger.info(message, throwable);
	        }
	    }

	    protected void info(String summary) {
	        logger.info(summary);
	        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	    }

	    protected void info(Exception exception) {
	        info(exception.getMessage());
	    }

	    protected void info(UIComponent component, String summary) {
	        logger.info(summary);
	        getFacesContext().addMessage(component.getClientId(getFacesContext()),
	                new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	    }

	    protected void info(UIComponent component, Exception exception) {
	        info(component, exception.getLocalizedMessage());
	    }

	    protected void warn(String summary) {
	        logger.warn(summary);
	        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
	    }

	    protected void warn(Exception exception) {
	        warn(exception.getMessage());
	    }

	    protected void warn(UIComponent component, String summary) {
	        logger.warn(summary);
	        getFacesContext().addMessage(component.getClientId(getFacesContext()),
	                new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
	    }

	    protected void warn(UIComponent component, Exception exception) {
	        warn(component, exception.getLocalizedMessage());
	    }

	    protected void error(String summary) {
	        logger.error(summary);
                getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	    }

	    protected void validationError(ValidationException exception) {
	        logger.error(exception);
	        for (String mensagem : exception.getErros()) {
	            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
	        }
	    }

	    protected void error(Exception exception) {
	        logger.error(exception);
	        error(exception.getMessage());
	    }
	    
	    protected void error(String summary, Exception e) {
	    	logger.error(e);
	        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	    }
	    
	    protected void error(UIComponent component, String summary) {
	        logger.error(summary);
	        getFacesContext().addMessage(component.getClientId(getFacesContext()),
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	    }

	    protected HttpServletRequest getRequest() {
	        return (HttpServletRequest) getExternalContext().getRequest();
	    }

	    protected void error(UIComponent component, Exception exception) {
	        logger.error(exception);
	        error(component, exception.getLocalizedMessage());
	    }

	    protected void fatal(String summary) {
	        logger.fatal(summary);
	        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
	    }

	    protected void fatal(Exception exception) {
	        logger.error(exception);
	        fatal(exception.getMessage());
	    }

	    protected void fatal(UIComponent component, String summary) {
	        logger.fatal(summary);
	        getFacesContext().addMessage(component.getClientId(getFacesContext()),
	                new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
	    }

	    protected void fatal(UIComponent component, Exception exception) {
	        logger.error(exception);
	        fatal(component, exception.getLocalizedMessage());
	    }
	
	    protected Application getApplication() {
	        return FacesContext.getCurrentInstance().getApplication();
	    }

	    protected ExternalContext getExternalContext() {
	        return FacesContext.getCurrentInstance().getExternalContext();
	    }

	    protected FacesContext getFacesContext() {
	        return FacesContext.getCurrentInstance();
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

	    @SuppressWarnings("rawtypes")
		protected Map getSessionMap() {
	        return getExternalContext().getSessionMap();
	    }

	    protected String getUsuarioHost() {
	    	
	    	StringBuilder host = new StringBuilder();
	    	host.append(getRequest().getServerName()).append(":").append(getRequest().getServerPort());
	        return host.toString();
	        
	    }

}
