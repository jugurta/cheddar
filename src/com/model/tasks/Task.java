package com.model.tasks;

import com.jugurta.validation.isValid;
import com.model.abstractions.SoftwareComponent;
import com.model.enumerations.Objects_Type;
import com.model.enumerations.Policy;
import com.model.enumerations.Task_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * @author Jugurtha
 * 
 *         A task, named Generic−Task is specified by the following definitions:
 *         Run any type of program (including any operating system function such
 *         as a scheduler). Statically defined in an Address space .
 * 
 *         Name : It is the unique name of the Task. Task−Type: It defines the
 *         type of the task.
 * 
 *         Cpu−Name: It is a string, which defined the Processor where is
 *         running the Task . Address−Space−Name: It is a string, which defines
 *         the name of the Address space hosting the task. Capacity: It is a
 *         natural, and it corresponds to the worst case execution time of the
 *         task. Deadline: The task must end its activation before its deadline.
 *         Start−Time: It is a natural, which defines the first release time of
 *         a Task . Priority: It is a priority range. It allows the scheduler to
 *         choose the Task to run. Blocking−Time: It’s the worst case shared
 *         resource waiting time of the task. Policy: It defines the scheduling
 *         policy of a task. Policy can be SCHED−RR, or SCHED−FIFO or
 *         SCHED−OTHERS Offsets: An offset stores two information : an
 *         activation number and a value. It allows to change the wake up time
 *         of a task on a given activation number. Text−Memory−Size: Size of the
 *         text segment of the task in order to perform memory requirement
 *         analysis. Stack−Memory−Size: Size of the memory stack of the task in
 *         order to perform memory requirement analy
 */
public abstract class Task extends SoftwareComponent {

	protected Task_Type task_type;

	protected String cpu_name;
	protected String address_space_name;

	protected int capacity;

	protected int deadline;
	protected int start_time;


	protected int priority;
	protected int blocking_time;
	protected Policy policy;

	protected int text_memory_size;
	protected int stack_memory_size;
	protected int criticality;
	protected int context_switch_overhead;

	public  Task(String id,String name,  String cpu_name, String address_space_name, int capacity, int deadline,
			int start_time, int priority, int blocking_time, Policy policy, int text_memory_size, int stack_memory_size,
			int criticality, int context_switch_overhead) throws VariableValueException {
		super(id,name,  Objects_Type.TASK_OBJECT_TYPE);
		isValid.isValidIdentifier(cpu_name);
		this.cpu_name = cpu_name;
		isValid.isValidIdentifier(address_space_name);
		this.address_space_name = address_space_name;

		if (capacity <= 0)
			throw new VariableValueException("Capacity must be greater than 0");
		else
			this.capacity = capacity;
		if (deadline < 0)
			throw new VariableValueException("deadline must be greater than 0");
		else
			this.deadline = deadline;
		if (start_time < 0)
			throw new VariableValueException("start time must be greater than 0");
		else
			this.start_time = start_time;

		if (blocking_time < 0)
			throw new VariableValueException("blocking time must be greater than 0");
		else
			this.blocking_time = blocking_time;

		if (text_memory_size < 0)
			throw new VariableValueException("text memory size must be greater than 0");
		else
			this.text_memory_size = text_memory_size;

		if (stack_memory_size < 0)
			throw new VariableValueException("stack memory size must be greater than 0");
		else
			this.stack_memory_size = stack_memory_size;

		if (criticality < 0)
			throw new VariableValueException("Criticality must be greater than 0");
		else
			this.criticality = criticality;

		if (context_switch_overhead < 0)
			throw new VariableValueException("context_switch_overhead must be greater than 0");
		else
			this.context_switch_overhead = context_switch_overhead;

		if ((priority != 0) && (policy == Policy.SCHED_OTHERS))
			throw new VariableValueException("cannot have priority <>0 and policy = Sched OTHERS");
		else {
			this.priority = priority;
			this.policy = policy;
		}
	}

	public Task_Type getTask_type() {
		return task_type;
	}

	public String getCpu_name() {
		return cpu_name;
	}

	public String getAddress_space_name() {
		return address_space_name;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getDeadline() {
		return deadline;
	}

	public int getPriority() {
		return priority;
	}

	public int getBlocking_time() {
		return blocking_time;
	}

	public Policy getPolicy() {
		return policy;
	}

	public int getText_memory_size() {
		return text_memory_size;
	}

	public int getStack_memory_size() {
		return stack_memory_size;
	}

	public int getCriticality() {
		return criticality;
	}

	public int getContext_Switch_Overhead() {
		return context_switch_overhead;
	}
	
	public int getStart_time() {
		return start_time;
	}
}
