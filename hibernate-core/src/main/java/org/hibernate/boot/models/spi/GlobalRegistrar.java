/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.MemberDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface GlobalRegistrar {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectIdGenerators(MemberDetails memberDetails);
}
