package ubo.aadl.model;

import com.model.dependencies.Dependency;
import com.model.dependencies.Precedence_Dependency;
import com.model.dependencies.Time_Triggered_Communication_Dependency;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;
import com.model.tasks.Task;

public class Dependency_AADL {

	String Task_sink;
	String Task_src;

	String Timing;

	public Dependency_AADL(String string_source, String Timing) {

		this.Task_src = string_source.split("->")[0].split("\\.")[0].trim();
		this.Task_sink = string_source.split("->")[1].split("\\.")[0].trim();
		this.Timing = Timing;
	}

	public Dependency toCheddarDependency(Task tasks[]) throws VariableValueException {
		String id_Task_src = null;
		String id_Task_sink = null;
		Dependency dependency = null;

		for (Task task : tasks) {
			id_Task_src = (task.getName().equals(this.Task_src)) ? task.getId() : id_Task_src;
			id_Task_sink = (task.getName().equals(this.Task_sink)) ? task.getId() : id_Task_sink;

		}

		if ((id_Task_sink != null) && (id_Task_src != null)) {
			switch (this.Timing) {
			case "immediate":
				dependency = new Precedence_Dependency("id_depend", "precedence_dependency",
						Objects_Type.DEPLOYEMENT_TYPE, id_Task_sink, id_Task_src);
				break;

			case "delayed":
				dependency = new Time_Triggered_Communication_Dependency("id_depend_time", "time_trigered_dependency",
						Objects_Type.DEPLOYEMENT_TYPE, id_Task_sink, id_Task_src, "delayed");
				break;

			default:
				dependency = new Time_Triggered_Communication_Dependency("id_depend_time", "time_trigered_dependency",
						Objects_Type.DEPLOYEMENT_TYPE, id_Task_sink, id_Task_src, "sampled");
				break;

			}
		}

		return dependency;
	}

	@Override
	public String toString() {

		return this.Task_src + " " + this.Task_sink + " " + this.Timing;
	}

}
