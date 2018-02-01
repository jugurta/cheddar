package ubo.aadl.model;

import com.model.resources.Critical_Section;

public class Critical_AADL {
	
	@Override
	public String toString() {
		return "Critical_AADL [resource_name=" + resource_name + ", task_name=" + task_name + ", start_critical="
				+ start_critical + ", end_critical=" + end_critical + "]";
	}

	private String resource_name;
	private String task_name;
	private int start_critical;
	private int end_critical;
	
	public Critical_AADL(String resource_name,String task_name,int end_critical)
	{
		this.resource_name=resource_name;
		this.task_name=task_name;
		this.start_critical=1;
		this.end_critical=end_critical;
		
	}
	
	
	public Critical_Section toCheddarCriticalSection()
	{
		
		return new Critical_Section(this.task_name, this.start_critical, this.end_critical);
	}

	public String getResource_name() {
		return resource_name;
	}

	public String getTask_name() {
		return task_name;
	}

	public int getStart_critical() {
		return start_critical;
	}

	public int getEnd_critical() {
		return end_critical;
	}
	
	
}
