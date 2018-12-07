package br.com.callink.bradesco.seguro.web.exception;

public class MissingParameterException extends Exception {

	private static final long serialVersionUID = 1L;

	public MissingParameterException() {
	}

	public MissingParameterException(String s) {
		super(s);
	}

	public MissingParameterException(Throwable throwable) {
		super(throwable);
	}
	
	public MissingParameterException(String s, Throwable throwable) {
		super(s,throwable);
	}

}