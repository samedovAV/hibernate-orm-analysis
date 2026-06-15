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
public interface FetchCharacteristicsPluralAttribute extends FetchCharacteristics {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getBatchSize();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isExtraLazy();
}
