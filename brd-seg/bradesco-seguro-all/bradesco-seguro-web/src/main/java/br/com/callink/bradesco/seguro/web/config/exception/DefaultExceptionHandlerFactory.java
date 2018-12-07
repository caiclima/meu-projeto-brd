package br.com.callink.bradesco.seguro.web.config.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Default {@link ExceptionHandlerFactory} utilizado pela aplicação.
 * 
 * @author michael
 * 
 */
public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory factory;

	public DefaultExceptionHandlerFactory(ExceptionHandlerFactory factory) {
		this.factory = factory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new DefaultExceptionHandler(factory.getExceptionHandler());
	}
}