package ubo.plugin.cheddar.handlers;

import java.io.IOException;

import java.util.ArrayList;

import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.xtext.aadl2.properties.util.GetProperties;

import com.model.dependencies.Dependency;
import com.model.enumerations.Migration_Type;
import com.model.enumerations.Policy;
import com.model.enumerations.Preemptive_type;
import com.model.enumerations.Priority_Assignement;
import com.model.enumerations.Processor_Type;
import com.model.enumerations.Scheduler_Type;
import com.model.exceptions.VariableValueException;
import com.model.others.AddressSpace;
import com.model.others.Cheddar;
import com.model.others.Scheduling_Parameters;
import com.model.processors.Core;
import com.model.processors.Mono_Core_Processor;
import com.model.processors.Multi_Core_Processor;
import com.model.resources.Resource;
import com.model.processors.Processor;
import com.model.tasks.Aperiodic_Task;
import com.model.tasks.Periodic_Task;
import com.model.tasks.Task;

import ubo.aadl.model.Critical_AADL;
import ubo.aadl.model.Dependency_AADL;
import ubo.aadl.model.Resource_AADL;

/**
 * 
 * @author Jugurtha
 *
 */

public class Util {

	private int threadCount = 0;
	private int processCount = 0;
	private int processorCount = 0;
	private int dataCount = 0;

	public String toCheddar(SystemInstance si) throws VariableValueException, IOException {

		String path = "";
		// Counting devices
		parse(si);

		// Declaring Components

		Core cores[] = new Core[this.processorCount];
		Processor processors[] = new Processor[this.processorCount];
		Task tasks[] = new Task[this.threadCount];
		Resource resources[] = new Resource[this.dataCount];
		AddressSpace addressSpaces[] = new AddressSpace[this.processorCount];

		ArrayList<Critical_AADL> array_critical = new ArrayList<>();
		ArrayList<Dependency_AADL> array_dependency_aadl = new ArrayList<>();

		final Priority_Assignement priority_assignement = Priority_Assignement.AUTOMATIC_ASSIGNMENT;
		final Policy policy = Policy.SCHED_FIFO;

		// Getting the processors from the EMV
		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case PROCESSOR:
				cores[this.processorCount - 1] = new Core("id_core_" + component.getName(),
						"core_unit_cheddar.impl.instancied_" + component.getName(),
						(float) GetProperties.getProcessorMIPS(component),
						new Scheduling_Parameters(converterFromString(GetProperties.getSchedulingProtocol(component)),
								0, Preemptive_type.PREEMPTIVE, 0, 0, 1, null, 0));
				processors[this.processorCount - 1] = new Mono_Core_Processor("id_cpu", component.getName(),
						cores[this.processorCount - 1], Migration_Type.NO_MIGRATION_TYPE);
				this.processorCount--;

				break;
			default:
				break;
			}

		}
		// Creating the adressSpaces for each processor
		for (int i = 0; i < addressSpaces.length; i++)
			addressSpaces[i] = createAddressSpace("id_address_" + i, "cheddar_impl_addr_" + i, processors[i].getName());

		// Creating the Tasks
		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case THREAD:
				String name = component.getFullName().toString();
				int capacity = (int) GetProperties.getMaximumComputeExecutionTimeinMs(component);
				int deadline = (int) GetProperties.getDeadlineinMilliSec(component);
				int priority = (int) GetProperties.getPriority(component, 0);
				int period = (int) GetProperties.getPeriodinMS(component);
				;

				tasks[this.threadCount - 1] = createTask(this.threadCount - 1,
						GetProperties.getDispatchProtocol(component).getName(), name,
						GetProperties.getBoundProcessor(component).getName(),
						getAddressSpaceFromProcessor(GetProperties.getBoundProcessor(component).getName(),
								addressSpaces),
						capacity, deadline, 0, priority, 0, policy, 0, 0, 0, 0, period, 0);
				this.threadCount--;

				for (FeatureInstance featureInstance : component.getFeatureInstances()) {
					for (ConnectionInstance connection : featureInstance.getDstConnectionInstances()) {
						array_critical.add(new Critical_AADL(connection.getSource().getName(), component.getName(),
								(int) GetProperties.getMaximumComputeExecutionTimeinMs(component)));
						// Getting the dataports
						array_dependency_aadl.add(new Dependency_AADL(connection.getFullName(),
								GetProperties.getConnectionTiming(connection).getName()));

					}
				}

				break;
			default:
				break;
			}

		}

		// Creating the shared data resources

		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case DATA:
				int state = 1;
				int address = 0;
				resources[this.dataCount - 1] = new Resource_AADL(component.getName(),
						GetProperties.getConcurrencyControlProtocol(component),
						array_critical.toArray(new Critical_AADL[array_critical.size()])).toCheddarResource(state, 2,
								address, GetProperties.getBoundProcessor(component).getName(),
								addressSpaces[0].getName(), 1, priority_assignement);
				this.dataCount--;
				break;
			default:
				break;
			}
		}
		// Creating the dependencies
		Dependency[] dependencies = new Dependency[array_dependency_aadl.size()];
		for (int i = 0; i < dependencies.length; i++)
			dependencies[i] = array_dependency_aadl.get(i).toCheddarDependency(tasks);

		path = new Cheddar(cores, processors, addressSpaces, tasks, dependencies, resources).WriteXML("jugo");

		return path;
	}

	public String features(SystemInstance si) throws IOException {

		// Counting devices
		parse(si);

		String response = "";

		final String cpu_name = "cheddar.cpu1";
		final String address_space_name = "cheddar.impl.instancied_ea1";
		// Scheduling Attributes

		final Priority_Assignement priority_assignement = Priority_Assignement.AUTOMATIC_ASSIGNMENT;
		final Policy policy = Policy.SCHED_FIFO;
		// Task Attributes
		int capacity;
		int deadline;
		int priority;
		int period;
		int start_time = 0;

		int blocking_time = 0;
		// Cheddar System Components

		Core cores[] = new Core[this.processorCount];
		Task tasks[] = new Task[this.threadCount];

		ArrayList<Critical_AADL> array_critical = new ArrayList<>();

		ArrayList<Resource_AADL> array_resource_aadl = new ArrayList<>();

		for (ComponentInstance component : si.getAllComponentInstances()) {
			switch (component.getCategory()) {
			case THREAD:
				try {
					String name = component.getFullName().toString();
					capacity = (int) GetProperties.getMaximumComputeExecutionTimeinMs(component);
					deadline = (int) GetProperties.getDeadlineinMilliSec(component);
					priority = (int) GetProperties.getPriority(component, 0);
					period = (int) GetProperties.getPeriodinMS(component);

					tasks[this.threadCount - 1] = createTask(this.threadCount - 1,
							GetProperties.getDispatchProtocol(component).getName(), name, cpu_name, address_space_name,
							capacity, deadline, start_time, priority, blocking_time, policy, 0, 0, 0, 0, period, 0);
					this.threadCount--;

				} catch (VariableValueException e) {

					e.printStackTrace();
				}

				for (FeatureInstance featureInstance : component.getFeatureInstances())

					for (ConnectionInstance connection : featureInstance.getDstConnectionInstances()) {
						array_critical.add(new Critical_AADL(connection.getSource().getName(), component.getName(),
								(int) GetProperties.getMaximumComputeExecutionTimeinMs(component)));

					}
				break;
			case DATA:
				array_resource_aadl.add(
						new Resource_AADL(component.getName(), GetProperties.getConcurrencyControlProtocol(component)));
				break;
			case PROCESS:
				break;
			case PROCESSOR:
				try {
					cores[this.processorCount - 1] = new Core(
							"id" + (this.processorCount - 1) + component.getName() + (this.processorCount - 1),
							"core_unit_cheddar.impl.instancied_" + component.getName(),
							(float) GetProperties.getProcessorMIPS(component),
							new Scheduling_Parameters(
									converterFromString(GetProperties.getSchedulingProtocol(component)), 0,
									Preemptive_type.PREEMPTIVE, 0, 0, 1, null, 0));
					this.processorCount--;
				} catch (VariableValueException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

		}

		try {
			// Adding critical sections to resources
			for (Resource_AADL resource_aadl : array_resource_aadl) {
				for (Critical_AADL critical : array_critical)
					resource_aadl.add_critical(critical);
			}

			// Creating cheddar components
			Processor processor = createProcessor(cpu_name, cores);
			AddressSpace addressSpace = createAddressSpace("id_3", address_space_name, processor.getName());
			Resource[] resources = new Resource[array_resource_aadl.size()];
			Dependency dependencies[] = null;
			// Instanciating resources from the AADL
			int state = 1;
			int address = 0;
			for (int i = 0; i < array_resource_aadl.size(); i++) {
				resources[i] = array_resource_aadl.get(i).toCheddarResource(state, 2, address, cpu_name,
						address_space_name, 1, priority_assignement);
			}
			Processor[] processor_array = { processor };
			AddressSpace[] arrayAdress= {addressSpace};
			// Instanciating Cheddar Model and writing the XML File
			response = new Cheddar(cores, processor_array, arrayAdress, tasks, dependencies,
					resources).WriteXML("cheddarpluginV1");

		} catch (VariableValueException e) {

			e.printStackTrace();
		}

		return response;

	}

	/**
	 * 
	 * @param schedulerString
	 *            the parameter we get from parsing the AADL file
	 * @return Scheduler type we use to create a scheduler object
	 */
	private Scheduler_Type converterFromString(String schedulerString) {
		Scheduler_Type scheduler_type;
		switch (schedulerString) {
		case "EDF":
			scheduler_type = Scheduler_Type.EARLIEST_DEADLINE_FIRST_PROTOCOL;
			break;
		case "RMS":
			scheduler_type = Scheduler_Type.RATE_MONOTONIC_PROTOCOL;
			break;
		case "DMS":
			scheduler_type = Scheduler_Type.DEADLINE_MONOTONIC_PROTOCOL;
			break;
		case "POSIX_1003_HIGHEST_PRIORITY_FIRST_PROTOCOL":
			scheduler_type = Scheduler_Type.POSIX_1003_HIGHEST_PRIORITY_FIRST_PROTOCOL;
			break;
		default:
			scheduler_type = Scheduler_Type.POSIX_1003_HIGHEST_PRIORITY_FIRST_PROTOCOL;
			break;
		}

		return scheduler_type;

	}

	private String getAddressSpaceFromProcessor(String cpu_name, AddressSpace[] address_spaces) {
		String address_space_name = "";
		for (AddressSpace address_space : address_spaces)
			address_space_name = (cpu_name.equals(address_space.getCpuName())) ? address_space.getName()
					: address_space_name;

		return address_space_name;

	}

	private Processor createProcessor(String cpu_name, Core... cores) throws VariableValueException {
		String identifier = "id_cpu";
		Processor processor;

		processor = (cores.length > 1)
				? new Multi_Core_Processor(identifier, cpu_name, Migration_Type.JOB_LEVEL_MIGRATION_TYPE,
						Processor_Type.IDENTICAL_MULTICORES_TYPE, cores)
				: new Mono_Core_Processor(identifier, cpu_name, cores[cores.length - 1],
						Migration_Type.NO_MIGRATION_TYPE);

		return processor;

	}

	
private Task createTask(int i, String dispatch_protocol, String name, String cpu_name, String address_space_name,
			int capacity, int deadline, int start_time, int priority, int blocking_time, Policy policy,
			int text_memory_size, int stack_memory_size, int criticality, int context_switch_overhead, int period,
			int jitter) throws VariableValueException {
		Task task;

		switch (dispatch_protocol) {
		case "Periodic":
			task = new Periodic_Task("id_task_period_" + i, name, cpu_name, address_space_name, capacity, deadline,
					start_time, priority, blocking_time, policy, text_memory_size, stack_memory_size, criticality,
					context_switch_overhead, period, jitter);
			break;
		case "Aperiodic":
			task = new Aperiodic_Task("id_task_aperiod_" + i, name, cpu_name, address_space_name, capacity, deadline,
					start_time, priority, blocking_time, policy, text_memory_size, stack_memory_size, criticality,
					context_switch_overhead);
			break;
		default:
			task = new Periodic_Task("id_task_period_" + i, name, cpu_name, address_space_name, capacity, deadline,
					start_time, priority, blocking_time, policy, text_memory_size, stack_memory_size, criticality,
					context_switch_overhead, period, jitter);
			break;
		}

		return task;
	}

	
	private AddressSpace createAddressSpace(String id, String address_space_name, String cpu_name)
			throws VariableValueException {
		return new AddressSpace(id, address_space_name, cpu_name, new Scheduling_Parameters(
				Scheduler_Type.NO_SCHEDULING_PROTOCOL, 0, Preemptive_type.PREEMPTIVE, 0, 0, 0, null, 0), 0, 0, 0, 0);

	}

	public void parse(SystemInstance si) {

		this.threadCount = 0;
		this.processCount = 0;
		this.processorCount = 0;
		this.dataCount = 0;
		
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
			case DATA:
				dataCount++;
				break;
			default:
				break;
			}
		}
	}	

}
