package br.com.callink.bradesco.task.exception;

public class TaskException extends Exception {

	private static final long serialVersionUID = 4299215105830314812L;

	public TaskException(Throwable thrwbl) {
        super(thrwbl);
    }

    public TaskException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public TaskException(String string) {
        super(string);
    }

    public TaskException() {
    }
    
}