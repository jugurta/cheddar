package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

public class Black_Board_Buffer_Dependency extends Dependency {

	public Black_Board_Buffer_Dependency( String id,String name, Objects_Type object_type) throws VariableValueException {
		super(id,name,  object_type,Dependency_Type.BLACK_BOARD_BUFFER_DEPENDENCY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
