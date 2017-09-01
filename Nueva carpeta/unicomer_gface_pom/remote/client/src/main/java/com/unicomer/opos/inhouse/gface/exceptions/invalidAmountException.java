package com.unicomer.opos.inhouse.gface.exceptions;

public class invalidAmountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public invalidAmountException() { super(); }
	  public invalidAmountException(String message) { super(message); }
	  public invalidAmountException(String message, Throwable cause) { super(message, cause); }
	  public invalidAmountException(Throwable cause) { super(cause); }
}
