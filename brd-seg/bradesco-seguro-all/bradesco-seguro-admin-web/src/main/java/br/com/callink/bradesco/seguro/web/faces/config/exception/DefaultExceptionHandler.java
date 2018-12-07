package br.com.callink.bradesco.seguro.web.faces.config.exception;

import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.persistence.OptimisticLockException;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;
import br.com.callink.bradesco.seguro.web.faces.utils.exception.ViewValidationException;

/**
 * {@link ExceptionHandler} global 'padrão' utilizado pela aplicação. Efetua tratativa centralizada
 * de Exceptions
 * 
 * @author michael
 * 
 */
public class DefaultExceptionHandler extends ExceptionHandlerWrapper {
	private static final int OPTIMISTIC_LOCKING_EXCEPTION_CAUSE_DEEP = 5;
	private static final int STALE_OBJECT_EXCEPTION_CAUSE_DEEP = 6;
	private static Logger logger = Logger.getLogger(DefaultExceptionHandler.class);
	private ExceptionHandler wrapped;

	public DefaultExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	/**
	 * Efetua tratativa centralizada de exceptions
	 * 
	 * @param e
	 */
	public static void handle(Throwable e) {
		// log
		String throwableMsg = e.getMessage();
		
		if(!(e instanceof ViewValidationException)){
			logger.error(throwableMsg, e);
		}

		String error = "Erro inesperado. Tente novamente. Caso o erro persista, contacte o Administrador do Sistema.";

		// extrai a mensagem da exception.
		throwableMsg = throwableMsg.replaceAll("(.*\\:\\s)(.*)", "$2");
		if(!StringUtils.isEmpty(throwableMsg)){
			error = throwableMsg;
		}
		
		// adiciona mensagem
		JSFUtils.addErrorMessage(error);
	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent eqe = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) eqe.getSource();
			Throwable throwable = context.getException();

			try {
				
				if(is404(throwable)){
					try {
						JSFUtils.redirect("404.xhtml");
					} catch (IOException e) {
						throw new FacesException(e);
					}
				}else if (isViewExpired(throwable)) {
					try {
						JSFUtils.redirect("login.xhtml");
					} catch (IOException e) {
						throw new FacesException(e);
					}
				}else if (isOptimisticLockException(throwable)){ 
					String errorMsg = ResourceBundle.getBundle("bundle.i18n").getString("Erro_Locking_Otimista");
					JSFUtils.addWarnMessage(errorMsg);
				} else {
					handle(throwable);
				}

				JSFUtils.renderResponse();
			} finally {
				i.remove();
			}
		}
	}
	
	private boolean is404(Throwable e){
		if(e.getMessage() != null){
			return Pattern.compile(".*FacesFileNotFoundException.*").matcher(e.getMessage()).find();
		}
		return false;
	}
	
	private boolean isOptimisticLockException(Throwable e){
		Throwable optimisticLockingException = getThrowableCauseDeeply(e, OPTIMISTIC_LOCKING_EXCEPTION_CAUSE_DEEP);
		if(optimisticLockingException != null && optimisticLockingException instanceof OptimisticLockException){
			return true;
		}
		
		Throwable staleObjectException = getThrowableCauseDeeply(e, STALE_OBJECT_EXCEPTION_CAUSE_DEEP);
		if(staleObjectException != null && staleObjectException instanceof StaleObjectStateException){
			return true;
		}
		
		return false;
	}
	
	private boolean isViewExpired(Throwable e){
		if(e instanceof ViewExpiredException || e.getCause() instanceof ViewExpiredException){
			return true;
		}
		
		// by message
		if(e.getMessage() != null){
			return Pattern.compile(".*ViewExpiredException.*").matcher(e.getMessage()).find();
		}
		return false;
	}
	
	private Throwable getThrowableCauseDeeply(Throwable root, int deep) {
		Throwable out = null;
		while (deep > 0) {
			deep--;
			if (out == null) {
				out = root.getCause();
			} else {
				out = out.getCause();
			}
		}
		return out;
	}
}