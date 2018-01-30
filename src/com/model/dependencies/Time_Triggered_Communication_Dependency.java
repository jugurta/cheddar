package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

public class Time_Triggered_Communication_Dependency extends Dependency {

	public Time_Triggered_Communication_Dependency(String id,String name,  Objects_Type object_type)
			throws VariableValueException {
		super(id,name, object_type, Dependency_Type.TIME_TRIGGERED_COMMUNICATION_DEPENDENCY);
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
