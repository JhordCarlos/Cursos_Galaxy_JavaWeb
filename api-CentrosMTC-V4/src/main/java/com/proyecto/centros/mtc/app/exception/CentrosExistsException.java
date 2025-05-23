package com.proyecto.centros.mtc.app.exception;

public class CentrosExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CentrosExistsException() {
		
	}

	public CentrosExistsException(String message) {
		super(message);

	}

	public CentrosExistsException(Throwable cause) {
		super(cause);
		
	}

	public CentrosExistsException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CentrosExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	
}
