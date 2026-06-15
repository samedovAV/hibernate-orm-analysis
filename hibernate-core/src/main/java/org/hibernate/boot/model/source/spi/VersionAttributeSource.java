/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface VersionAttributeSource
		extends SingularAttributeSource, RelationalValueSourceContainer, ImplicitBasicColumnNameSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUnsavedValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSource();
}
