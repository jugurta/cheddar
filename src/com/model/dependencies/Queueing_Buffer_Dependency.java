package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

public class Queueing_Buffer_Dependency extends Dependency{

	public Queueing_Buffer_Dependency(String id,String name, Objects_Type object_type) throws VariableValueException {
		super(id,name,  object_type, Dependency_Type.QUEUEING_BUFFER_DEPENDENCY);
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
