package com.model.tasks;

import com.model.enumerations.Policy;
import com.model.exceptions.VariableValueException;

public class Parametric_Task extends Task{

	public Parametric_Task(String id,String name, String cpu_name,String address_space_name,int capacity,int deadline, int start_time
			,int priority, int blocking_time, Policy policy, int text_memory_size, int stack_memory_size,int criticality,int Context_Switch_Overhead) throws VariableValueException {
		super(id,name, cpu_name,address_space_name,capacity,deadline,start_time,priority,
				blocking_time,policy,text_memory_size,stack_memory_size,criticality, Context_Switch_Overhead);
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
