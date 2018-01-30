package ubo.plugin.cheddar.handlers;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.xtext.aadl2.properties.util.GetProperties;

import com.model.dependencies.Dependency;
import com.model.enumerations.Migration_Type;
import com.model.enumerations.Policy;
import com.model.enumerations.Preemptive_type;
import com.model.enumerations.Scheduler_Type;
import com.model.exceptions.VariableValueException;
import com.model.others.AddressSpace;
import com.model.others.Cheddar;
import com.model.others.Scheduling_Parameters;
import com.model.processors.Core;
import com.model.processors.Mono_Core_Processor;
import com.model.processors.Processor;
import com.model.resources.Critical_Section;
import com.model.resources.Resource;
import com.model.tasks.Periodic_Task;
import com.model.tasks.Task;


import org.osate.xtext.aadl2.properties.util.InstanceModelUtil;

/**
 * 
 * @author Jugurtha
 *
 */

public class Util {

	private  int threadCount = 0;
	private  int processCount = 0;
	private  int processorCount = 0;
	private  int busCount = 0;
	private  int deviceCount = 0;
	private  int memoryCount = 0;

	public final  String identifier = "cheddar.impl";


	public String parse(SystemInstance si) {

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

	public  String features(SystemInstance si) throws IOException {
		String chaine = "";
		String cpuName="cheddar.cpu1";
		final String address_space_name="cheddar.impl.instancied_ea1";
		ArrayList<Task> arrayTask=new ArrayList<>();
		ArrayList<Core> arrayCore=new ArrayList<>();
		ArrayList<Critical_Section>array_critical=new ArrayList<>();
		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case THREAD:
				try {
					arrayTask.add(new Periodic_Task("id_T"+component.getName(), component.getName(), cpuName,
							address_space_name, (int) GetProperties.getMaximumComputeExecutionTimeinMs(component),
							(int) GetProperties.getDeadlineinMilliSec(component), 0,
							(int) GetProperties.getPriority(component, 0), 0, Policy.SCHED_FIFO,
							(int) GetProperties.getSourceCodeSizeInBytes(component),
							(int) GetProperties.getStackSizeInBytes(component), 0, 0,
							(int) GetProperties.getPeriodinMS(component), 0));
				} catch (VariableValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for(FeatureInstance feat:component.getFeatureInstances())
					for(ConnectionInstance conn:feat.getDstConnectionInstances())
					{array_critical.add(new Critical_Section(component.getName(), 1, (int) GetProperties.getMaximumComputeExecutionTimeinMs(component)));
					 chaine+=conn.getSource().getName();
					}
				break;
			case ABSTRACT:
				break;
			case BUS:
				break;
			case DATA:
				chaine+=component.getName()+"\n";
				chaine+=GetProperties.getConcurrencyControlProtocol(component)+"\n";
				chaine+=GetProperties.getConnectionTiming(component).getName()+"\n";	

				
				break;
				
			case DEVICE:
				break;
			case MEMORY:
				break;
			case PROCESS:
				chaine+="Process : \n" ;
							break;
			case PROCESSOR:
				 try {
					arrayCore.add(new Core("id"+component.getName(), component.getName(), (float) GetProperties.getProcessorMIPS(component), new 
							Scheduling_Parameters(Scheduler_Type.valueOf(GetProperties.getSchedulingProtocol(component)), 0,Preemptive_type.PREEMPTIVE, 0, 0, 1, null, 0))); 
				} catch (VariableValueException e) {
					e.printStackTrace();
				}
				 break;
			case SUBPROGRAM:
				break;
			case SUBPROGRAM_GROUP:
				break;
			case SYSTEM:
				break;
			case THREAD_GROUP:
				break;
			case VIRTUAL_BUS:
				break;
			case VIRTUAL_PROCESSOR:
				break;
			default:
				break;
			}

		}
		
		try {
			Mono_Core_Processor mono_core_processor;
			Core cores[]=arrayCore.toArray(new Core[arrayCore.size()]);
			Task tasks[]=arrayTask.toArray(new Task[arrayTask.size()]);
			mono_core_processor=new Mono_Core_Processor("idcpu", cpuName, arrayCore.get(0).getId(), Migration_Type.NO_MIGRATION_TYPE);
			AddressSpace addressSpace = new AddressSpace("id_3", address_space_name, mono_core_processor.getName(),
					new Scheduling_Parameters(Scheduler_Type.NO_SCHEDULING_PROTOCOL, 0,
							Preemptive_type.PREEMPTIVE, 0, 0, 0, null, 0), 0, 0, 0, 0);
			AddressSpace[] arrayAddress = { addressSpace };
			
			Cheddar cheddar = new Cheddar(cores, mono_core_processor, arrayAddress, tasks);
			
			//chaine+=cheddar.WriteXML("cheddarplugin");
		} catch (VariableValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chaine;

	}

}
