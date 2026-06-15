/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface RepeatableContainer<R extends Annotation> extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R[] value();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void value(R[] value);
}
