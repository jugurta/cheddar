package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;


/**
 * 
 * -@author Jugurtha
 * 
 * Resource−Dependency: this dependency models a shared memory
access between several Task components and a Resource component.
 *
 */
public class Resource_Dependency extends Dependency{
	
	private String resource_dependency_resource;
	private String resource_dependency_task;
	public Resource_Dependency(String id,String name,  Objects_Type object_type, String resource_dependency_resource,String resource_dependency_task) throws VariableValueException {
		super(id,name,  object_type, Dependency_Type.RESOURCE_DEPENDENCY);
		this.resource_dependency_resource=resource_dependency_resource;
		this.resource_dependency_task=resource_dependency_task;
	}

	
	@Override
	public String toXML()
	{
		String xml="<dependency>\n"+ 
				"<type_of_dependency>\n" +
				 super.type_of_dependency+
				"</type_of_dependency>\n"
				 +"<resource_dependency_resource ref=\"" +this.resource_dependency_resource +"\" />\n"
				 +"<resource_dependency_task ref=\""+this.resource_dependency_task+"\"/>\n"
				 +"</dependency>\n"
				;
		
		return xml;
	}
}
