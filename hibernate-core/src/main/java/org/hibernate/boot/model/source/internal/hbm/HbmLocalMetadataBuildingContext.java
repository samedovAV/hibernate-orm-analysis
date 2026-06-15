/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.hbm.spi.EntityInfo;
import org.hibernate.boot.model.source.spi.LocalMetadataBuildingContext;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.mapping.PersistentClass;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to contextual information specific to a {@code hbm.xml} mapping.
 *
 * @author Steve Ebersole
 */
public interface HbmLocalMetadataBuildingContext extends LocalMetadataBuildingContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ToolingHintContext getToolingHintContext();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String determineEntityName(EntityInfo entityElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String determineEntityName(String entityName, String clazz);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String qualifyClassName(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentClass findEntityBinding(String entityName, String clazz);
}
