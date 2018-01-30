package com.model.tasks;

import com.model.enumerations.Policy;
import com.model.enumerations.Task_Type;
import com.model.exceptions.VariableValueException;


/**
 * 
 * 
 * @author Jugurtha
 *
 *Aperiodic_Task Class : it represents an Aperiodic Task , it extends from the class Task 
 * An aperiodic task is only activated once
 *
 *
 */
public class Aperiodic_Task  extends Task{
	
	public Aperiodic_Task
	(String id,String name, String cpu_name,String address_space_name,int capacity,int deadline, int start_time
			,int priority, int blocking_time, Policy policy, int text_memory_size, int stack_memory_size ,int criticality,int context_switch_overhead) throws VariableValueException {
		super(id,name, cpu_name,address_space_name,capacity,deadline,start_time,priority,
				blocking_time,policy,text_memory_size,stack_memory_size,criticality,context_switch_overhead);
		
		this.task_type=Task_Type.APERIODIC_TYPE;
	}

	@Override
	public String toXML() {
		String xml="<aperiodic_task id=\""+super.id+"\"\n"
					+"<object_type>"+ super.object_type+"</object_type>\n"
					+"<name>"+super.name+"</name>\n"
					+"<task_type>"+super.task_type  +"</task_type>\n"
					+"<cpu_name>"+ super.cpu_name+ "</cpu_name>\n"
					+"<address_space_name>"+ super.address_space_name+ "</address_space_name>\n"
					+"<capacity>"+ super.capacity+"</capacity>\n" 
					+ "<deadline>"+ super.deadline+"</deadline>\n"
					+ "<start_time>"+super.start_time +"</start_time>\n" 
					+ "<priority>"+super.priority+"</priority>\n"
					+ "<blocking_time>"+ super.blocking_time+ "</blocking_time>\n" 
					+ "<policy>"+super.policy+ "</policy>\n" + 
					"<text_memory_size>"+super.text_memory_size+ "</text_memory_size>\n"
					+ "<stack_memory_size>"+ super.stack_memory_size+ "</stack_memory_size>\n" 
					+ "<criticality>"+super.criticality+ "</criticality>\n" 
					+ "<context_switch_overhead >"+ super.context_switch_overhead+ "</context_switch_overhead>\n"
					+" </aperiodic_task>\n"
				;
		return xml;
	}

}

