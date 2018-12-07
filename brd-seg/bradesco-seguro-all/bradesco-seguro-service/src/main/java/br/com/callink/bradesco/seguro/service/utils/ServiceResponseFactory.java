package br.com.callink.bradesco.seguro.service.utils;

import br.com.callink.bradesco.seguro.service.utils.ServiceResponse.Level;


/**
 * Factory para objetos de resposta a camada de serviços {@link ServiceResponse}
 * 
 * @author michael
 * 
 */
public final class ServiceResponseFactory {
	public static ServiceResponse create() {
		return new ServiceResponse();
	}

	public static ServiceResponse createWithData(Object data) {
		return create().setData(data);
	}
	
	public static ServiceResponse createWithMessage(Level level, String message, Object... args) {
		return create().addMessage(level, message, args);
	}
	
	public static ServiceResponse createWithMessageAndData(Object data, Level level, String message, Object... args) {
		return create().setData(data).addMessage(level, message, args);
	}
	
}
