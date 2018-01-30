package com.jugurta.validation;


import com.model.exceptions.VariableValueException;

public class isValid {

	public static void isValidIdentifier(String identifier) throws VariableValueException
	{		
		
		if (identifier.equals(null))
			throw new VariableValueException("Not valid name or Id");
		else 
			if (identifier.trim().length()==0)
				throw new VariableValueException("Must be non empty");
		
	}	
		
	
}
