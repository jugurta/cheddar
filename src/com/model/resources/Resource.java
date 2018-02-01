package com.model.resources;

import com.jugurta.validation.isValid;
import com.model.abstractions.SoftwareComponent;
import com.model.enumerations.Objects_Type;
import com.model.enumerations.Protocol;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 *         A Resource is specified by the following definitions: It models any
 *         synchronized data structure shared by tasks. It is statically defined
 *         in an Address space . It models asynchronous communication between
 *         tasks of the same Address space .
 * 
 *         Standards attributes State: It is the initial value of the resource
 *         component (similar to a semaphore initial value) Size: It defines the
 *         size of the Resource . Address: It is the location of the Resource .
 *         Protocol: It characterize how the Resource is locked and unlocked.
 *         Cpu−Name: Each shared resource has to be located on a given Processor
 *         . Address−Space−Name: Its stores the name of Address space which
 *         hosted the Resource .
 *
 * 
 * 
 */

public abstract class Resource extends SoftwareComponent {

	protected int state;
	protected int size;
	protected int address;
	protected Protocol protocol;
	Critical_Section critical_section[];


	protected String cpu_name;
	protected String addressSpaceName;

	public Resource(String id,String name,  int state, int size, int address, String cpu_name, String address_space_name,
			Critical_Section critical_section[]) throws VariableValueException {
		super(id,name,Objects_Type.RESOURCE_OBJECT_TYPE);
		isValid.isValidIdentifier(cpu_name);
		isValid.isValidIdentifier(address_space_name);
		
		if(critical_section!=null)
		this.critical_section = critical_section;
		
	

		if (state < 0)
			throw new VariableValueException("state must be greater than 0");
		else
			this.state = state;

		if (size < 0)
			throw new VariableValueException("size must be greater than 0");
		else
			this.size = size;

		if (address < 0)
			throw new VariableValueException("address must be greater than 0");
		else
			this.address = address;

		this.cpu_name = cpu_name;
		this.addressSpaceName = address_space_name;
	}

	@Override
	public abstract String toXML();

}
