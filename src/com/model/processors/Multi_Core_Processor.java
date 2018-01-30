package com.model.processors;

import com.model.enumerations.Migration_Type;
import com.model.enumerations.Processor_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 * Multi−Core−Processor is a single computing component with two
	or more independent Cores , which are the units that read and execute program
	instructions It allows to schedule tasks globally with a set of cores
 *
 */

public class Multi_Core_Processor extends Processor {
	
	private Processor_Type processor_type;
	private String coreIds[];
	
	public Multi_Core_Processor(String id,String name,Migration_Type migration_type , Processor_Type processor_type,
			String ...coreIds) throws VariableValueException {
		super(id,name,migration_type);
		
		super.migration_type=migration_type;
		this.processor_type=processor_type;
		
		int size=coreIds.length;
		if(size<2)
			throw new VariableValueException("There must be at least 2 cores");
		else 
		{
		this.coreIds= new String[size];
		for (int i=0;i<size;i++)
			this.coreIds[i]=coreIds[i];
		}
		}
	
	
	@Override
	public String toXML() {
		String xml="<processors>\n"
				+"<multi_cores_processor id=\""+super.id+"\">\n"
				+"<object_type>"+super.object_type+ "</object_type>\n"
				+"<name>"+super.name+"</name>\n"
				+"<processor_type>"+this.processor_type+"</processor_type>\n"
				+"<migration_type>"+super.migration_type+"</migration_type>\n"
				+"<cores>\n"
				;
		for (int i=0;i<this.coreIds.length;i++)
			xml+="\t<core ref=\"" +coreIds[i]+"\"/>\n";
		
		xml+="</cores>\n"
			 +"</multi_cores_processor>\n"
			 +"</processors>\n"
				;
		return xml;
	}

}
