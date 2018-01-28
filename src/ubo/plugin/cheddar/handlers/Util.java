package ubo.plugin.cheddar.handlers;

import java.io.ObjectInputStream.GetField;
import java.util.List;

import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.xtext.aadl2.properties.util.GetProperties;

public class Util {

	private static int threadCount = 0;
	private static int processCount = 0;
	private static int processorCount = 0;
	private static int busCount = 0;
	private static int deviceCount = 0;
	private static int memoryCount = 0;

	public static String parse(SystemInstance si) {

		for (ComponentInstance component : si.getAllComponentInstances()) {

			switch (component.getCategory()) {
			case THREAD:
				threadCount++;
				break;
			case PROCESS:
				processCount++;
				break;
			case PROCESSOR:
				processorCount++;
				break;
			case MEMORY:
				memoryCount++;
				break;
			case BUS:
				busCount++;
				break;
			case DEVICE:
				deviceCount++;
				break;
			default:
				break;
			}
		}

		return "Threads: " + threadCount + "\n" + "Process: " + processCount + "\n" + "Processor: " + processorCount
				+ "\n" + "Memory :" + memoryCount + "\n" + "Bus : " + busCount + "\n" + "Device :" + deviceCount;
	}
	
	
	
	public static String features(SystemInstance si)
	{
		String chaine="ok\n";
		for (ComponentInstance component : si.getAllComponentInstances()) 
		{
			if(component.getCategory()==ComponentCategory.THREAD)
			{	
				
				chaine+="Period: "+GetProperties.getPeriodinMS(component)+"\n";
				chaine+="Priority: "+GetProperties.getPriority(component, 0)+"\n";
				chaine+="Deadline: "+GetProperties.getDeadlineinMilliSec(component)+"\n";
				chaine+="Compute Execution time:"+GetProperties.getExecutionTimeInMS(component)+"\n";
				chaine+="Dispatch Protocol: "+GetProperties.getDispatchProtocol(component).getName()+"\n";
				
			}
			
		}
			
		
		return chaine;
	
		
	}

}
	

