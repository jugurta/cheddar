package com.model.dependencies;

import com.model.abstractions.SoftwareComponent;
import com.model.enumerations.Dependency_Type;
import com.model.enumerations.Objects_Type;
import com.model.exceptions.VariableValueException;

/**
 * 
 * 
 * @author Jugurtha
 * 
 *
 *         A Dependency is specified by the following definitions: (1) It allows
 *         to model an interaction between two software entities which has an
 *         impact upon the scheduling of the system. One of those entity is
 *         called the sink entity and the other is the source entity. (2)
 *         Software entities handled in a Dependency component can either be
 *         Tasks , and or Resources and or Buffers
 */
public abstract class Dependency extends SoftwareComponent {

	Dependency_Type type_of_dependency;

	public Dependency(String id,String name, Objects_Type object_type, Dependency_Type dependency_Type)
			throws VariableValueException {
		super( id,name, null);
		this.type_of_dependency = dependency_Type;
	}

}
