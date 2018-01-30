package com.model.resources;

public class Critical_Section {
	
	private String task_name;
	private int task_begin;
	private int task_end;
	
	
	public int getTask_begin() {
		return task_begin;
	}


	public int getTask_end() {
		return task_end;
	}


	public Critical_Section(String task_name,int task_begin, int task_end) {
		super();
		this.task_name=task_name;
		this.task_begin = task_begin;
		this.task_end = task_end;
	}
	
	
	public String toXML()
	{
		String xml="<task_name>"+this.task_name+ "</task_name>\n"
				+"<critical_section>\n"
				+" <task_begin>"+this.task_begin+"</task_begin>\n"
				+"<task_end>"+ this.task_end+"</task_end>\n"
				+"</critical_section>\n"
				;
		return xml;
	}
	
	
}
