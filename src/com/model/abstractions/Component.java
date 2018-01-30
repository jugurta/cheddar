package com.model.abstractions;

import com.jugurta.validation.isValid;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * 
 * @author Jugurtha
 *	
 *	
 *	Component class : Each Class from the projects extends this class 
 *  There are the reusable units. A component has a type, an
 *  unique name and attributes. It is a part of a system to analyse.
 * 
 * 
 */

public abstract class Component {
 

protected  String id;
protected String name;
protected Objects_Type object_type;

/**
 * 
 * @param name : the name of the component
 * @param Id : the Id of the component
 * @throws VariableValueException 
 */

public Component(String id,String name,Objects_Type object_type) throws VariableValueException
{
	isValid.isValidIdentifier(name);
	isValid.isValidIdentifier(id);
	

	this.id=id;
	this.name=name;
	this.object_type=object_type;
}


public String getName()
{
	return name;
	
}

public String getId()
{

return id;
}
/**
 * 
 * @return the XML representation of the component
 */
public abstract String toXML();
 
}
