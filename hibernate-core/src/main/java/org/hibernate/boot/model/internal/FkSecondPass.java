/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import org.hibernate.boot.spi.SecondPass;
import org.hibernate.mapping.Value;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Emmanuel Bernard
 */
public interface FkSecondPass extends SecondPass {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Value getValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInPrimaryKey();
}
