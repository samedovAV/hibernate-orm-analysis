/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface FetchCharacteristicsSingularAssociation extends FetchCharacteristics {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUnwrapProxies();
}
