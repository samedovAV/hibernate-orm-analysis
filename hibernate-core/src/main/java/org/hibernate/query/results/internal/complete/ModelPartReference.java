/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.complete;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.query.results.spi.FetchBuilder;
import org.hibernate.query.results.spi.ResultBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link ResultBuilder} or {@link FetchBuilder} that refers to some part
 * of the user's domain model
 *
 * @author Steve Ebersole
 */
public interface ModelPartReference {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

	/**
	 * The part of the domain model that is referenced
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getReferencedPart();
}
