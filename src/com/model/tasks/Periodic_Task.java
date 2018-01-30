package com.model.tasks;

	
import com.model.enumerations.Policy;
import com.model.enumerations.Task_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 * Periodic_Task: In this case, the Task is periodic, and we have
	two more attributes in order to characterize the Task :
	Period: It is the time between two task activations. The
	period is a constant delay for a periodic task. 

	Jitter: The Jitter of Task is an upper bound on the delay
	that Task may suffer between the time it is supposed to be released and the
	time that it is actually released.
	The jitter is a maximum lateness on the task wake up time.
 *
 */
public class Periodic_Task extends Task {
	private int period;
	private int jitter;

	public Periodic_Task(String id,String name,  String cpu_name, String address_space_name, int capacity, int deadline,
			int start_time, int priority, int blocking_time, Policy policy, int text_memory_size, int stack_memory_size,
			int criticality,int context_switch_overhead,int period, int jitter) throws VariableValueException {
		super(id,name,  cpu_name, address_space_name, capacity, deadline, start_time, priority, blocking_time, policy,
				text_memory_size, stack_memory_size,criticality ,context_switch_overhead);

		super.task_type = Task_Type.PERIODIC_TYPE;
		if (period<=0)
			throw new VariableValueException("period must be greater than 0");
		else
		this.period=period;
		
		if(jitter<0)
			throw new VariableValueException("jitter must be greater or equal than 0");
		this.jitter=jitter;

	}
	
	@Override
	public String toXML()
	{	String xml="<periodic_task id=\""+super.id + "\">\n"
		    +"<object_type>"+ super.object_type+ "</object_type>\n"
			+"<task_type>"+task_type+"</task_type>\n"
			+"<name>"+super.name+"</name>\n"
			+"<cpu_name>"+super.cpu_name+"</cpu_name>\n"
			+"<address_space_name>"+super.address_space_name+ "</address_space_name>\n"
			+"<capacity>" +super.capacity+"</capacity>\n" 
			+ "<deadline>"+ super.deadline+"</deadline>\n"
			+ "<start_time>"+ super.start_time+"</start_time>\n" 
			+ "<priority >"+ super.priority+ "</priority>\n" 
			+ "<blocking_time>"  +super.blocking_time+ "</blocking_time>\n"
			+ "<policy>" + super.policy+ "</policy>\n"
			+ "<text_memory_size>"+ super.text_memory_size +"</text_memory_size>\n" 
			+ "<stack_memory_size>"+ super.stack_memory_size+ "</stack_memory_size>\n" 
			+ "<criticality>"+super.criticality+ "</criticality>\n" 
			+ "<context_switch_overhead >"+ super.context_switch_overhead+ "</context_switch_overhead>\n"
			+"<period>"+ this.period +"</period>\n" 
			+ "<jitter >"+ this.jitter+ "</jitter>\n"
			+"</periodic_task>\n"
	;		
		return xml;
	}

}
