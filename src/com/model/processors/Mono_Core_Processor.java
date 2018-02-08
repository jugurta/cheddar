package com.model.processors;

import com.model.enumerations.Migration_Type;
import com.model.enumerations.Processor_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * Mono−Core−Processor is defined by the following parameter:
	Core: It is the corresponding core to the Mono−Core−Processor.
	It is characterized by:
	Scheduling: See the Core description.
	Speed: See the Core description.
 *
 */
public class Mono_Core_Processor extends Processor{

	private Core core;
	
	
	public Mono_Core_Processor(String id,String name, Core core,Migration_Type migration_type) throws VariableValueException {	
		super(id,name,migration_type);
		if(core==null) throw new VariableValueException("Cores must not be empty");
		this.core=core;
		
	}
	
	@Override
	public String toXML()
	{
		String xml="<processors>\n";
		xml+="<mono_core_processor id=\""+super.id+"\">\n"
				+"<object_type>"+ super.object_type+ "</object_type>\n"
				+"<name>" +super.name+ "</name>\n"
				+"<processor_type>"+Processor_Type.MONOCORE_TYPE+ "</processor_type>\n"
				+"<migration_type>"+super.migration_type+"</migration_type>\n"
				+"<core ref=\""+core.getId()+"\">\n"
				+"</core>\n"
				+"</mono_core_processor>\n" + 
				"</processors>\n"
				;
		return xml;
	}

}
