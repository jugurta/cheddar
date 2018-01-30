package com.model.others;

import com.model.enumerations.Preemptive_type;
import com.model.enumerations.Scheduler_Type;
import com.model.exceptions.VariableValueException;

public class Scheduling_Parameters {

	private Scheduler_Type scheduler_type;
	private float quantum;
	private Preemptive_type premptive_type;
	private float capacity;
	private float period;
	private int priority;
	private int start_time;
	private String user_defined_scheduler_source_file_name;

	public Scheduler_Type getScheduler_type() {
		return scheduler_type;
	}

	public float getQuantum() {
		return quantum;
	}

	public Preemptive_type getPremptive_type() {
		return premptive_type;
	}

	public float getCapacity() {
		return capacity;
	}

	public float getPeriod() {
		return period;
	}

	public int getPriority() {
		return priority;
	}

	public int getStart_time() {
		return start_time;
	}
	public String getuser_defined_scheduler_source_file_name()
	{
		return this.user_defined_scheduler_source_file_name;
	}

	public Scheduling_Parameters (Scheduler_Type scheduler_Type, float quantum, Preemptive_type preemptive_type, float capacity,
			float period, int priority,String user_defined_scheduler_source_file_name, int start_time) {
		
		

		this.scheduler_type=scheduler_Type;
		this.quantum=quantum;
		this.premptive_type=preemptive_type;
		this.capacity=capacity;
		this.period=period;
		this.priority=priority;
		this.user_defined_scheduler_source_file_name=user_defined_scheduler_source_file_name;
		this.start_time=start_time;
	}
	
	public String toXML()
	 {
		 String xml="<scheduling>\n"
				 +"<scheduling_parameters>\n"
				 +"<scheduler_type>" + this.scheduler_type+"</scheduler_type>\n"
				 +"<quantum>"+this.quantum +"</quantum>\n"
				 +"<preemptive_type>" + this.premptive_type+"</preemptive_type>\n"
				 +"<capacity>" +this.capacity+"</capacity>\n" + 
				 "<period>" +this.period+ "</period>\n"
				 +"<priority>"+ this.priority+"</priority>\n"
				 ;
		 			
		 		if(this.user_defined_scheduler_source_file_name!=null)
		 			{
		 			xml+="<user_defined_scheduler_source_file_name>"+
		 			 this.user_defined_scheduler_source_file_name
		 					+ "</user_defined_scheduler_source_file_name>\n";
		 			}
		 
				 xml+="<start_time>"+this.start_time+ "</start_time>\n"
				 +"</scheduling_parameters>\n"
				 +"</scheduling>\n"
				 ;
		 return xml;
	 }
}

