/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface FetchCharacteristics {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchTiming getFetchTiming();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchStyle getFetchStyle();
}
