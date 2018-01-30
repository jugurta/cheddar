package com.model.processors;

import com.model.abstractions.HardwareComponent;
import com.model.enumerations.Migration_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 *
 *         The entity Processor is specified by the following definitions: (1)
 *         It is a deployment unit for a software component. (2) It is composed
 *         by a set of Cores and Caches
 *
 *         We distinguish in Cheddar two separate cases: Mono−Core−Processor and
 *         Multi−Core−Processor.
 *
 *         Name : It is the unique name of Processor. 
 *         Network: It is a string,
 *         which corresponds to the name of entity Network connected to the
 *         Processor.
 *         Processor −Type: It is an enumeration, which defines the
 *         type of considered Processor.
 *         Migration−Type: It is an enumeration,
 *         which defines the type of Tasks migration between the Cores of the Processor
 *
 */
public abstract class Processor extends HardwareComponent {

	protected Migration_Type migration_type;

	public Processor(String id,String name,  Migration_Type migration_type) throws VariableValueException {
		super(id,name,Objects_Type.PROCESSOR_OBJECT_TYPE);
		this.migration_type = migration_type;

	}
	
	public String getId()
	{
		return super.id;
	}
	
	public String getName()
	{
		return super.name;
		
	}

	@Override
	public abstract String toXML();

}
