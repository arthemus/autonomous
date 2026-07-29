package org.autonomous.tenaz.commons;

import java.io.Serializable;

/**
 * Common, routine operations that need to be performed constantly between
 * database entities and domain classes.
 *
 * @author arthemus
 * @since 25/09/2013
 */
public interface PersistentEntity extends Serializable {

	/**
	 * Checks whether the class in question is new or is being edited.
	 *
	 * @return {@code true} if the instance has not been persisted yet.
	 */
	boolean isNewInstance();

}
