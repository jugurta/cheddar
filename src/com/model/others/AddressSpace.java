package com.model.others;

import com.jugurta.validation.isValid;
import com.model.abstractions.SoftwareComponent;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 *         An Address space is the range of virtual addresses that the operating
 *         system assigns to a user or separately running program.
 * 
 *         attributes : Name : it is the unique name of the Address space .
 *         Cpu_name: It is the name of Processor which contain Address space.
 *         Text−Memory−Size: It is the size of text segment. A text segment
 *         contains the executable image of the program.
 *          Stack−Memory−Size: It 
 *         is the size of stack segment. A stack segment contains the
 *         function-call stack. Data−Memory−Size: It is the size of data
 *         segment.
 *          Heap−Memory−Size: It is the size of logical memory reserved
 *         for the heap. Scheduling: It defines all parameters of scheduling. It
 *         is the type of Scheduling−Parameters
 *
 */
public class AddressSpace extends SoftwareComponent {

	private String cpu_Name;
	private Scheduling_Parameters scheduling_parameters;
	private int text_memory_size;
	private int stack_memory_size;
	private int data_memory_size;
	private int heap_memory_size;

	public AddressSpace(String id, String name, String cpu_Name, Scheduling_Parameters scheduling_parameters,
			int text_memory_size, int stack_memory_size, int data_memory_size, int heap_memory_size) throws VariableValueException {
		super(id,name,  Objects_Type.ADDRESS_SPACE_OBJECT_TYPE);	
		isValid.isValidIdentifier(cpu_Name);
		this.cpu_Name = cpu_Name;
		
		this.scheduling_parameters = scheduling_parameters;
		if(text_memory_size<0)
			throw new VariableValueException("text_memory_size must be greater than 0");
		this.text_memory_size = text_memory_size;
		
		if(stack_memory_size<0)
			throw new VariableValueException("stack_memory_size must be greater than 0");
		this.stack_memory_size = stack_memory_size;

		if(data_memory_size<0)
			throw new VariableValueException("data_memory_size must be greater than 0");
		this.data_memory_size = data_memory_size;
		if(heap_memory_size<0)
			throw new VariableValueException("heap_memory_sizemust be greater than 0");
		this.heap_memory_size = heap_memory_size;
	}
	
	@Override
	public String toXML() {
	String xml="<address_space id=\""+super.id +"\">\n"
				+"<object_type>"+super.object_type+ "</object_type>\n"
				+"<name>" +super.name+ "</name>\n"
				+"<cpu_name>"+ this.cpu_Name + "</cpu_name>\n"
				+"<text_memory_size>"  +this.text_memory_size + "</text_memory_size>\n" 
				+ "<stack_memory_size>"+this.stack_memory_size+  "</stack_memory_size>r\n" 
				+ "<data_memory_size>" +this.data_memory_size +  "</data_memory_size>r\n" 
				+ "<heap_memory_size>" +this.heap_memory_size+  "</heap_memory_size>\n"
				+scheduling_parameters.toXML()
				+"</address_space>\n"
			;
		
		return xml;
	}
}
