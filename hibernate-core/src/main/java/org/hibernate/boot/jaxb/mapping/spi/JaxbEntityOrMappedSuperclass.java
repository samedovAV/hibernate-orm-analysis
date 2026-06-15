/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB binding interface for commonality between entity and mapped-superclass mappings
 *
 * @author Steve Ebersole
 */
public interface JaxbEntityOrMappedSuperclass extends JaxbManagedType, JaxbLifecycleCallbackContainer {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbIdClassImpl getIdClass();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setIdClass(@Nullable JaxbIdClassImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbEmptyTypeImpl getExcludeDefaultListeners();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setExcludeDefaultListeners(@Nullable JaxbEmptyTypeImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbEmptyTypeImpl getExcludeSuperclassListeners();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setExcludeSuperclassListeners(@Nullable JaxbEmptyTypeImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbEntityListenerContainerImpl getEntityListenerContainer();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setEntityListenerContainer(@Nullable JaxbEntityListenerContainerImpl value);
}
