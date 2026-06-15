/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi;

import java.lang.annotation.Annotation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public interface UnloadedClass {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAnnotation(Class<? extends Annotation> annotationType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
}
