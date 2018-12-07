package br.com.callink.bradesco.seguro.web.faces.utils.exception;

/**
 * Erros em Validações na Camada de View
 * 
 * @author michael
 * 
 */
public class ViewValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ViewValidationException() {
		super();
	}

	public ViewValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViewValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewValidationException(String message) {
		super(message);
	}

	public ViewValidationException(Throwable cause) {
		super(cause);
	}
}