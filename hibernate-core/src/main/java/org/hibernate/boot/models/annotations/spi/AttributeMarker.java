/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import jakarta.persistence.FetchType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which identify attributes.
 *
 * @apiNote All the interesting bits are in the optional sub-interfaces.
 *
 * @author Steve Ebersole
 */
public interface AttributeMarker extends Annotation {
	interface Fetchable extends AttributeMarker {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		FetchType fetch();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void fetch(FetchType value);
	}

	interface Cascadeable extends AttributeMarker {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		jakarta.persistence.CascadeType[] cascade();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void cascade(jakarta.persistence.CascadeType[] value);
	}

	interface Optionalable extends AttributeMarker {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean optional();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void optional(boolean value);
	}

	interface Mappable extends AttributeMarker {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String mappedBy();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void mappedBy(String value);
	}
}
