package com.model.resources;

import com.model.enumerations.Priority_Assignement;
import com.model.enumerations.Protocol;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 * Priority−Inheritance−Protocol: A Task which blocks a high priority task due to a critical section, 
 * sees its priority to be increased to the priority
	level of the blocked task. Priority−Inheritance−Protocol should not be used
	with more than one shared resource due to deadlock.
 *
 */

public class PIP_Resource extends Resource{
	
	int priority;
	Priority_Assignement priority_assignment;
	
	public PIP_Resource(String id,String name,  int state, int size, int address, String cpuName,
			String addressSpaceName, int priority, Priority_Assignement priority_assignment,Critical_Section critical_section[]) throws VariableValueException {
		super(id,name,  state, size, address, cpuName, addressSpaceName, critical_section);
		
		super.protocol=Protocol.PRIORITY_INHERITANCE_PROTOCOL;
		this.priority=priority;
		this.priority_assignment=priority_assignment;
	}

	

	@Override
	public String toXML() {
		String xml="<pip_resource id=\""+super.id+"\">\n"
				+"<object_type>"+super.object_type+"</object_type>\n"
				+"<name>"+super.name+"</name>\n"
				+"<state>"+ super.state+ "</state>\n" + 
				"<size>"+super.size +"</size>\n"
				+"<address>"+super.address+"</address>\n"
				+"<protocol>"+super.protocol+"</protocol>"
				+"<critical_sections>\n"
				;
				
		for (int i = 0; i < critical_section.length; i++) {
			xml+=critical_section[i].toXML();
			}
				
				xml+="</critical_sections>\n"
				+"<cpu_name>"+super.cpu_name+"</cpu_name>\n"
				+"<address_space_name>"+super.addressSpaceName+"</address_space_name>\n"
				+"<priority>"+this.priority+"</priority>"
				+"<priority_assignment>"+this.priority_assignment+ "</priority_assignment>"
				+"</pip_resource>\n";

				
		return xml;
	}

}
