package com.model.dependencies;

import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

public class Time_Triggered_Communication_Dependency extends Dependency {
	private String time_triggered_communication_sink;
	private String time_triggered_communication_source;
	private String timing_property ;
	
	public Time_Triggered_Communication_Dependency(String id, String name, Objects_Type object_type,
			String time_triggered_communication_sink, String time_triggered_communication_source,String timing_property)
			throws VariableValueException {
		super(id, name, object_type, Dependency_Type.TIME_TRIGGERED_COMMUNICATION_DEPENDENCY);
		this.time_triggered_communication_sink = time_triggered_communication_sink;
		this.time_triggered_communication_source = time_triggered_communication_source;
		this.timing_property=timing_property;
	}

	@Override
	public String toXML() {
		String xml="<dependency>\n"+ 
				"<type_of_dependency>\n" +
				 super.type_of_dependency+
				"</type_of_dependency>\n"
				 +"<time_triggered_communication_sink ref=\"" +this.time_triggered_communication_sink +"\" />\n"
				 +"<time_triggered_communication_source ref=\""+this.time_triggered_communication_source+"\"/>\n"
				 +"</dependency>\n";
		
		return xml;
	}

}
