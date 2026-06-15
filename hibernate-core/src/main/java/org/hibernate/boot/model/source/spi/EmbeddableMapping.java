/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTuplizerType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Unifying contract for consuming JAXB types which describe an embeddable (in JPA terms).
 *
 * @author Steve Ebersole
 */
public interface EmbeddableMapping {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getClazz();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmTuplizerType> getTuplizer();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getParent();
}
