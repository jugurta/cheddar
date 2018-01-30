package com.model.abstractions;

import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * This Class extends the Component class : it describes the hardware components
 * Hardware components: They model resources provided by the execution 
 * environment. We have Processor , Cache , Core , Memory and Network
 */
public abstract class HardwareComponent extends Component {

	public HardwareComponent(String id,String name, Objects_Type object_type) throws VariableValueException {
		super( id,name, object_type);
		
	}



}
