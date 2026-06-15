/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SingularAttributeSourceOneToOne extends SingularAttributeSourceToOne {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<DerivedValueSource> getFormulaSources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isConstrained();
}
