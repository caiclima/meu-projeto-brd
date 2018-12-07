package br.com.callink.bradesco.seguro.service.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa retorno de execução de serviço (Camada de Serviços).
 * 
 * @author michael
 * 
 */
public final class ServiceResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Message> messages = new ArrayList<ServiceResponse.Message>();
	private Object data;

	public static class Message implements Serializable {
		private static final long serialVersionUID = 1L;
		private Level level;
		private String message;

		public Message(Level level, String message) {
			this.level = level;
			this.message = message;
		}

		public Level getLevel() {
			return level;
		}

		public void setLevel(Level level) {
			this.level = level;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Message [level=" + level + ", message=" + message + "]";
		}
		
	}

	public enum Level {
		WARNING, INFO, ERROR;
	}

	/**
	 * Configura os dados a serem retornados.
	 * 
	 * @param data
	 */
	public ServiceResponse setData(Object data) {
		this.data = data;
		return this;
	}
	
	public Object getData() {
		return this.data;
	}

	/**
	 * Adiciona uma mensagem contendo o {@link Level} informado.
	 * 
	 * @param message
	 * @param args
	 */
	public ServiceResponse addMessage(Level level, String message, Object... args) {
		add(level, message, args);
		return this;
	}
	
	/**
	 * Adiciona uma mensagem de error
	 * 
	 * @param message
	 * @param args
	 */
	public ServiceResponse addError(String message, Object... args) {
		add(Level.ERROR, message, args);
		return this;
	}

	/**
	 * Adiciona uma mensagem de warning
	 * 
	 * @param message
	 * @param args
	 */
	public ServiceResponse addWarning(String message, Object... args) {
		add(Level.WARNING, message, args);
		return this;
	}

	/**
	 * Adiciona uma mensagem informativa
	 * 
	 * @param message
	 * @param args
	 */
	public ServiceResponse addInfo(String message, Object... args) {
		add(Level.INFO, message, args);
		return this;
	}

	private void add(Level level, String message, Object... args){
		messages.add(new Message(level, MessageFormat.format(message, args)));
	}
	
	public List<Message> getMessages(){
		return Collections.unmodifiableList(messages);
	}
}