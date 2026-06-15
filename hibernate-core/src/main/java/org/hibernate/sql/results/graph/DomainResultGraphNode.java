/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import java.util.BitSet;

import org.hibernate.Incubating;
import org.hibernate.graph.spi.GraphImplementor;
import org.hibernate.metamodel.model.domain.JpaMetamodel;
import org.hibernate.spi.NavigablePath;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Marker for all object types that can be part of a result mapping
 * <p>
 * Both {@link DomainResult} and {@link Fetch} are subtypes.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface DomainResultGraphNode {
	/**
	 * Does this node contain any non-scalar (sub-)results?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsAnyNonScalarResults() {
		return false;
	}

	/**
	 * Collect the JDBC value indexes used by this domain result that should be cached.
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectValueIndexesToCache(BitSet valueIndexes);

	// todo (6.0) : result variable (selection alias)?  - even fetches can have alias

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<?> getResultJavaType();

	/**
	 * The NavigablePath for this node (if one!).  Certain nodes will not
	 * have a NavigablePath, namely those not associated with a Navigable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default NavigablePath getNavigablePath() {
		// by default these nodes would not have a path.  those that do explicitly
		// override this already to return it
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean appliesTo(GraphImplementor<?> graphImplementor, JpaMetamodel metamodel){
		return false;
	}

}
