/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A ResultMappingMementoNode that is a reference to some part of the user's
 * domain model
 *
 * @author Steve Ebersole
 */
public interface ModelPartReferenceMemento extends ResultMappingMementoNode {
	/**
	 * Path to the memento, relative to the result roots
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

}
