package com.model.exceptions;

public class VariableValueException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VariableValueException()
	{
		super();
	}
	
	public VariableValueException (String exceptionMessage)
	{
		super(exceptionMessage);
	}
	

}
