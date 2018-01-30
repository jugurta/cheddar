package com.model.abstractions;

import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * This Class extends the Component class : it describes the software components
 * Software components: They model the design of the software. They are deployed
 *  onto hardware components. In Cheddar, we have Task , Resource
 *  Buffer , Dependency and Message 
 */

public abstract class SoftwareComponent extends Component{
	
	
	public SoftwareComponent( String id,String name,Objects_Type object_type) throws VariableValueException {
		super(id,name,object_type);
		

	}

}
