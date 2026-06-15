/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.persistence.FetchType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between non-id, non-version and non-embedded.  Basically attributes that JPA
 * defines as fetchable or not.
 *
 * @author Steve Ebersole
 */
public interface JaxbStandardAttribute extends JaxbPersistentAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchType getFetch();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetch(FetchType value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isOptional();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOptional(Boolean optional);
}
