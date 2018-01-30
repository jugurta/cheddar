package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

public class Asynchronous_Communication_Dependency extends Dependency{

	public Asynchronous_Communication_Dependency( String id,String name, Objects_Type object_type)
			throws VariableValueException {
		super(id,name,  object_type,Dependency_Type.ASYNCHRONOUS_COMMUNICATION_DEPENDENCY);
			}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
