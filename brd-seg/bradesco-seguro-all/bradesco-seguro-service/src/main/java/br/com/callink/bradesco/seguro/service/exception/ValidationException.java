package br.com.callink.bradesco.seguro.service.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsavel em exibir uma exception ou uma lista de erros, para
 * validacao de campos obrigatórios.
 * 
 * @author swb.thiagohenrique
 * 
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final List<String> errors = new ArrayList<String>();

	public ValidationException() {
	}

	public ValidationException(String s, Throwable throwable) {
		super(s, throwable);
		add(s);
	}

	public ValidationException(String s) {
		super(s);
		add(s);
	}

	public ValidationException(List<String> erros) {
		super(erros.toString());
		addAll(erros);
	}

	public List<String> getErros() {
		return Collections.unmodifiableList(errors);
	}

	public final void addAll(List<String> messages) {
			errors.addAll(messages);
	}

	public final void add(String message) {
		errors.add(message);
	}

	public boolean hasMessages() {
		if (getErros().isEmpty()) {
			return false;
		}
		return true;
	}
}
