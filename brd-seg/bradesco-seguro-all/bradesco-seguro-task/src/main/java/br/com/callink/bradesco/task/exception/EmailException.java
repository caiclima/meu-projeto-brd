package br.com.callink.bradesco.task.exception;

public class EmailException extends Exception {

	private static final long serialVersionUID = -103093342124753291L;

	public EmailException(Throwable thrwbl) {
        super(thrwbl);
    }

    public EmailException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public EmailException(String string) {
        super(string);
    }

    public EmailException() {
    }
    
}
