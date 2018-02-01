package ubo.aadl.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.model.enumerations.Priority_Assignement;
import com.model.exceptions.VariableValueException;
import com.model.resources.Critical_Section;
import com.model.resources.Np_Resource;
import com.model.resources.PCP_Resource;
import com.model.resources.PIP_Resource;
import com.model.resources.Resource;

public class Resource_AADL {

	private String resource_name;
	private String resource_procotol;
	ArrayList<Critical_AADL> criticals;

	public Resource_AADL(String resource_name, String resource_procotol, Critical_AADL... critical_AADLs) {

		this.resource_name = resource_name;
		this.resource_procotol = resource_procotol;
		this.criticals = new ArrayList<>();
		for (Critical_AADL critical : critical_AADLs)
			if (critical.getResource_name().equals(this.resource_name))
				this.criticals.add(critical);

	}

	public void add_critical(Critical_AADL... critical_aadl) {

		if ((critical_aadl != null) && (critical_aadl.length > 0)) {
			for (Critical_AADL critical : critical_aadl)
				if (critical.getResource_name().equals(this.resource_name))
					this.criticals.add(critical);
		}
		eliminate_duplicates();
	}

	public Resource toCheddarResource(int state, int size, int address, String cpu_name, String address_space_name,
			int priority, Priority_Assignement priority_assignment) throws VariableValueException {

		Resource resource;

		switch (this.resource_procotol) {
		case "Priority_Ceiling":
			resource = new PCP_Resource("id_pcp", this.resource_name, state, size, address, cpu_name,
					address_space_name, priority, priority_assignment, toCritical_Cheddar());
			break;
		case "Priority_Inheritance":

			resource = new PIP_Resource("id_pip", this.resource_name, state, size, address, cpu_name,
					address_space_name, priority, priority_assignment, toCritical_Cheddar());
			break;
		case "None_Specified":
			resource = new Np_Resource("id_np", this.resource_name, state, size, address, cpu_name, address_space_name,
					toCritical_Cheddar());
		default:
			resource = new Np_Resource("id_np", this.resource_name, state, size, address, cpu_name, address_space_name,
					toCritical_Cheddar());
			break;
		}

		return resource;

	}

	private Critical_Section[] toCritical_Cheddar() {
		eliminate_duplicates();

		Critical_Section[] critical_section = new Critical_Section[this.criticals.size()];

		for (int i = 0; i < this.criticals.size(); i++) {
			critical_section[i] = this.criticals.get(i).toCheddarCriticalSection();
		}

		return critical_section;

	}

	public ArrayList<Critical_AADL> getCritical() {
		return this.criticals;
	}

	@Override
	public String toString() {
		return "Resource_AADL [resource_name=" + resource_name + ", resource_procotol=" + resource_procotol
				+ ", criticals=" + criticals.toString() + "]";
	}

	private void eliminate_duplicates() {

		Set<Critical_AADL> hs = new HashSet<>();
		hs.addAll(this.criticals);
		this.criticals.clear();
		this.criticals.addAll(hs);
	}

}
