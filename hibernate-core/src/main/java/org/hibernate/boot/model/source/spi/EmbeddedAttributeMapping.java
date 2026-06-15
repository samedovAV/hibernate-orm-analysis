/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.jaxb.hbm.spi.SingularAttributeInfo;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Unifying contract for any JAXB types which describe an embedded (in JPA terms).
 * <p>
 * Essentially this presents a unified contract over the {@code <component/>},
 * {@code <composite-id/>}, {@code <dynamic-component/>} and
 * {@code <nested-dynamic-component/>} elements
 *
 * @author Steve Ebersole
 */
public interface EmbeddedAttributeMapping extends SingularAttributeInfo {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUnique();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableMapping getEmbeddableMapping();
}
