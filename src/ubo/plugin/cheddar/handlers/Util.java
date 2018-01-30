package ubo.plugin.cheddar.handlers;

import java.io.IOException;

import java.util.ArrayList;

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
import com.model.enumerations.Priority_Assignement;
import com.model.enumerations.Scheduler_Type;
import com.model.exceptions.VariableValueException;
import com.model.others.AddressSpace;
import com.model.others.Cheddar;
import com.model.others.Scheduling_Parameters;
import com.model.processors.Core;
import com.model.processors.Mono_Core_Processor;
import com.model.resources.Critical_Section;
import com.model.resources.PCP_Resource;
import com.model.resources.Resource;
import com.model.processors.Processor;
import com.model.tasks.Periodic_Task;
import com.model.tasks.Task;

import org.osate.xtext.aadl2.properties.util.InstanceModelUtil;

/**
 * 
 * @author Jugurtha
 *
 */

public class Util {

	private int threadCount = 0;
	private int processCount = 0;
	private int processorCount = 0;
	private int busCount = 0;
	private int deviceCount = 0;
	private int memoryCount = 0;
	private int dataCount = 0;

	public String features(SystemInstance si) throws IOException {

		// Counting devices
		parse(si);

		String response = "";
		String ResourceName = "";
		String ResourceProtocol = "";
		final String cpu_name = "cheddar.cpu1";
		final String address_space_name = "cheddar.impl.instancied_ea1";
		// Scheduling Attributes

		final Priority_Assignement priority_assignement = Priority_Assignement.AUTOMATIC_ASSIGNMENT;
		final Policy policy = Policy.SCHED_FIFO;
		// Task Attributes
		int capacity;
		int deadline;
		int priority;
		int text_memory_size;
		int stack_memory_size;
		int period;
		int context_switch_overhead = 0;
		int start_time = 0;
		int criticality = 0;
		int jitter = 0;
		int blocking_time = 0;
		// Cheddar System Components
		Processor mono_core_processor;
		AddressSpace addressSpace;
		Core cores[] = new Core[this.processorCount];
		Task tasks[] = new Task[this.threadCount];
		int size = (this.dataCount > 0) ? 1 : 0;
		Resource[] arrayresource = new Resource[size];

		ArrayList<Critical_Section> array_critical = new ArrayList<>();

		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case THREAD:
				try {
					String name = component.getName();
					capacity = (int) GetProperties.getMaximumComputeExecutionTimeinMs(component);
					deadline = (int) GetProperties.getDeadlineinMilliSec(component);
					priority = (int) GetProperties.getPriority(component, 0);
					text_memory_size = (int) GetProperties.getSourceCodeSizeInBytes(component);
					stack_memory_size = (int) GetProperties.getStackSizeInBytes(component);
					period = (int) GetProperties.getPeriodinMS(component);
					context_switch_overhead = 0;
					start_time = 0;
					criticality = 0;
					jitter = 0;
					blocking_time = 0;

					tasks[this.threadCount - 1] = new Periodic_Task("Task", name, cpu_name, address_space_name,
							capacity, deadline, start_time, priority, blocking_time, policy, text_memory_size,
							stack_memory_size, criticality, context_switch_overhead, period, jitter);
					this.threadCount--;

				} catch (VariableValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (FeatureInstance feat : component.getFeatureInstances())
					for (ConnectionInstance conn : feat.getDstConnectionInstances()) {
						array_critical.add(new Critical_Section(component.getName(), 1,
								(int) GetProperties.getMaximumComputeExecutionTimeinMs(component)));
						response += conn.getSource().getName();
					}
				break;
			case DATA:
				ResourceName = component.getName();
				ResourceProtocol = GetProperties.getConcurrencyControlProtocol(component);
				break;
			case PROCESS:
				break;
			case PROCESSOR:
				try {
					cores[this.processorCount - 1] = new Core("id" + component.getName(), component.getName(),
							(float) GetProperties.getProcessorMIPS(component),
							new Scheduling_Parameters(
									Scheduler_Type.valueOf(GetProperties.getSchedulingProtocol(component)), 0,
									Preemptive_type.PREEMPTIVE, 0, 0, 1, null, 0));
				} catch (VariableValueException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

		}

		try {

			mono_core_processor = new Mono_Core_Processor("idcpu", cpu_name, cores[cores.length - 1].getId(),
					Migration_Type.NO_MIGRATION_TYPE);
			addressSpace = new AddressSpace("id_3", address_space_name, mono_core_processor.getName(),
					new Scheduling_Parameters(Scheduler_Type.NO_SCHEDULING_PROTOCOL, 0, Preemptive_type.PREEMPTIVE, 0,
							0, 0, null, 0),
					0, 0, 0, 0);

			AddressSpace[] arrayAddress = { addressSpace };

			if ((array_critical.size() > 0) && (array_critical != null))
				arrayresource[0] = new PCP_Resource("pcp", ResourceName, 1, 2, 0, cpu_name, address_space_name, 1,
						priority_assignement, array_critical.toArray(new Critical_Section[array_critical.size()]));

			if ((arrayresource != null) && (arrayresource.length > 0))
				response = new Cheddar(cores, mono_core_processor, arrayAddress, tasks, arrayresource)
						.WriteXML("cheddarpluginV2");
			else
				response = new Cheddar(cores, mono_core_processor, arrayAddress, tasks).WriteXML("cheddarpluginV1");

		} catch (VariableValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

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
			case DATA:
				dataCount++;
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
}
