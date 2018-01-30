package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;


/**
 * 
 * @author Jugurtha
 * 
 * Precedence−Dependency : this dependency models a precedency
relationship between two Task components. With this dependency, the sink
task must wait for completion of the source task before being released by the
scheduler.
 *
 */
public class Precedence_Dependency extends Dependency{

	String precedence_sink ;
	String precedence_source ;
	public Precedence_Dependency( String id,String name, Objects_Type object_type, String precedence_sink, String precedence_source) throws VariableValueException {
		super( id,name, object_type, Dependency_Type.PRECEDENCE_DEPENDENCY);
		this.precedence_sink=precedence_sink;
		this.precedence_source=precedence_source;
		
	}
	
	
	@Override
	public String toXML() {
		String xml="<dependency>\n"
				+"<type_of_dependency>"
				+super.type_of_dependency
				+"</type_of_dependency>\n"
				+"<precedence_sink ref=\""+this.precedence_sink+"\" />\n"
				+"<precedence_source ref=\""+this.precedence_source+"\" />\n"
				+"</dependency>\n";

		return xml;
	}

}
