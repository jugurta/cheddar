package com.model.others;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.model.dependencies.Dependency;
import com.model.exceptions.VariableValueException;
import com.model.processors.Core;
import com.model.processors.Processor;
import com.model.resources.Resource;
import com.model.tasks.Task;


public class Cheddar {

	private Core cores[];
	private Processor[] processor;
	private AddressSpace addressSpaces[];
	private Task tasks[];
	private Resource resources[];
	private Dependency dependencies[];

	public Cheddar(Core cores[], Processor[] processor, AddressSpace addressSpaces[], Task tasks[], Resource... resources)
			throws VariableValueException {
		if ((cores == null) || (processor == null) || (addressSpaces == null) || (tasks == null))
			throw new VariableValueException("You have invalid parameters");

		if (cores != null)
			this.cores = cores;

		if (processor != null)
			this.processor = processor;

		if (addressSpaces != null)
			this.addressSpaces = addressSpaces;

		if (tasks != null)
			this.tasks = tasks;

		if (resources != null)
			this.resources = resources;

	}
	
	/**
	 *  Constructor with dependencies
	 * @param cores
	 * @param processor
	 * @param addressSpaces
	 * @param tasks
	 * @param dependencies
	 * @param resources
	 * @throws VariableValueException
	 */
	
	public Cheddar(Core cores[], Processor processor[], AddressSpace addressSpaces[], Task tasks[], Dependency dependencies[],Resource... resources)
			throws VariableValueException	
	{
		if ((cores == null) || (processor == null) || (addressSpaces == null) || (tasks == null))
			throw new VariableValueException("You have invalid parameters");
		
		if (cores != null)
			this.cores = cores;

		if (processor != null)
			this.processor = processor;

		if (addressSpaces != null)
			this.addressSpaces = addressSpaces;

		if (tasks != null)
			this.tasks = tasks;

		if (resources != null)
			this.resources = resources;
		
		if(dependencies!=null)
			this.dependencies=dependencies;
		
	}

	public String toXML() {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		xml += "<cheddar>\n";

		xml += "<core_units>\n";
		for (Core core : this.cores)
			xml += core.toXML();
		xml += "</core_units>\n";
		
		
		xml += "<processors>\n";
		for(Processor processor:this.processor)
			xml+=processor.toXML();
		xml+="</processors>\n";

		xml += "<address_spaces>\n";

		for (AddressSpace addressSpace : this.addressSpaces)
			xml += addressSpace.toXML();

		xml += "</address_spaces>\n";

		xml += "<tasks>\n";

		for (Task task : this.tasks)
			xml += task.toXML();

		xml += "</tasks>\n";
		if(this.resources!=null)
		if (this.resources.length > 0) {
			xml += "<resources>\n";

			for (Resource resource : this.resources)
				xml += resource.toXML();

			xml += "</resources>\n";
		}
		
		if(this.dependencies!=null)
		if (this.dependencies.length > 0) {
			xml += "<dependencies>\n";

			for (Dependency dependency: this.dependencies)
				xml += dependency.toXML();

			xml += "</dependencies>\n";
		}
		
		xml += "</cheddar>\n";
		
		
		return xml;

	}
	
	public String WriteXML(String file) throws IOException 
	{	
		String path="";
		String XML=this.toXML();
		new File("Cheddar/").mkdir();
        BufferedWriter out = new BufferedWriter(new FileWriter("Cheddar/"+file+".xml"));
		try {
			out.write(XML);
	        out.close();
	        path=new File("Cheddar/"+file+".xml").getAbsolutePath();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		return path;
        
	}
	


}
