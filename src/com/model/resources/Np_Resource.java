package com.model.resources;

import com.model.enumerations.Protocol;
import com.model.exceptions.VariableValueException;


/**
 * 
 * @author Jugurtha
 *
 *No−Protocol: The Resource is accessed by a FIFO order and no priority inheritance is applied.
 */

public class Np_Resource extends Resource{

	public Np_Resource(String id,String name,  int state, int size, int address, String cpuName,
			String addressSpaceName, Critical_Section[] critical_section) throws VariableValueException {
		super(id,name, state, size, address, cpuName, addressSpaceName, critical_section);
		
		super.protocol=Protocol.NO_PROTOCOL;

	}

	
	

	@Override
	public String toXML() {
		String xml="<np_resource id=\""+super.id+"\">\n"
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
				+"</np_resource>\n"
				;
		
		
		return xml;
	}

}
