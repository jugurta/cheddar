package com.model.processors;

import com.model.abstractions.HardwareComponent;
import com.model.enumerations.Objects_Type;
import com.model.enumerations.Scheduler_Type;
import com.model.exceptions.VariableValueException;
import com.model.others.Scheduling_Parameters;

/**
 * 
 * @author Jugurtha
 *
 *         A Core is a deployment unit for a software component. It is the unit
 *         that read and execute program instructions.
 * 
 *         it has the following attributes : Name : It is the unique name of
 *         Core . Speed: It is a real, which gives the exchange rate of flow
 * 
 *         Scheduling: It defines all parameters of scheduling. It is the type
 *         of Scheduling−Parameters.
 */

public class Core extends HardwareComponent {

	private float speed;
	private Scheduling_Parameters scheduling_parameters;

	public Core(String id, String name, float speed, Scheduling_Parameters scheduling_parameters)
			throws VariableValueException {
		super(id,name,  Objects_Type.CORE_OBJECT_TYPE);

		if (speed < 0)
			throw new VariableValueException("Speed must be greater than 0");
		this.speed = speed;
		if(scheduling_parameters==null)
			throw new VariableValueException("Scheduling protocol must be present");
		else
		if (scheduling_parameters.getScheduler_type() == Scheduler_Type.NO_SCHEDULING_PROTOCOL)
			throw new VariableValueException("Scheduling protocol must not be No Scheduling Protocol");
		else if ((scheduling_parameters.getScheduler_type() == Scheduler_Type.PIPELINE_USER_DEFINED_PROTOCOL)
				&& (scheduling_parameters.getuser_defined_scheduler_source_file_name() == null))
			throw new VariableValueException("Cannot have simultaneous (A−Scheduler = Pipeline−User−Defined−Protocol)\r\n"
					+ "and (File−Name Empty).");
		else if ((scheduling_parameters.getuser_defined_scheduler_source_file_name() != null) && ((scheduling_parameters
				.getScheduler_type() != Scheduler_Type.AUTOMATA_USER_DEFINED_PROTOCOL)
				|| (scheduling_parameters.getScheduler_type() != Scheduler_Type.PIPELINE_USER_DEFINED_PROTOCOL)))
			throw new VariableValueException("when specifying filename Scheduling protocol"
					+ "must be Automata_User_Defined Protocol or Pipleline_User_Defined Protocol");
		else if ((scheduling_parameters.getQuantum() < 0) || (scheduling_parameters.getCapacity() < 0)
				|| (scheduling_parameters.getPeriod() < 0))
			throw new VariableValueException("Quantum , Period and Capacity must be superior to 0");
		else if ((scheduling_parameters.getQuantum() > 0) && ((scheduling_parameters
				.getScheduler_type() != Scheduler_Type.POSIX_1003_HIGHEST_PRIORITY_FIRST_PROTOCOL)
				|| (scheduling_parameters.getScheduler_type() != Scheduler_Type.ROUND_ROBIN_PROTOCOL)
				|| (scheduling_parameters.getScheduler_type() != Scheduler_Type.HIERARCHICAL_ROUND_ROBIN_PROTOCOL)
				|| (scheduling_parameters.getScheduler_type() != Scheduler_Type.HIERARCHICAL_CYCLIC_PROTOCOL)))

			throw new VariableValueException("Scheduling protocol is incompatibe with the quantum");

		this.scheduling_parameters = scheduling_parameters;

	}
	
	
	public String getId()
	{
		return super.id;
	}

	@Override
	public String toXML() {
		String xml = "";
		xml += "<core_unit id=\"" + super.id + "\">\n" + "<object_type>" + super.object_type + "</object_type>\n"
				+ "<name>" + super.name + "</name>\n" + scheduling_parameters.toXML() + "<speed>" + speed + "</speed>\n"
				+ "</core_unit>\n";
		return xml;
	}

}
