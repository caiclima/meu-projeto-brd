package br.com.callink.bradesco.seguro.dao.exception;

/**
 * Representa {@link RuntimeException} para camada de persistencia/acesso a
 * dados
 * 
 * @author michael
 * 
 */
public class DataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}
}