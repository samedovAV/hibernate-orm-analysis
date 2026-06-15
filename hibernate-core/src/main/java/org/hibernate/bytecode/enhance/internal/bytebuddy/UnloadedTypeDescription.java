/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.bytebuddy;

import java.lang.annotation.Annotation;

import org.hibernate.bytecode.enhance.spi.UnloadedClass;

import net.bytebuddy.description.type.TypeDescription;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

class UnloadedTypeDescription implements UnloadedClass {

	private final TypeDescription typeDescription;

	UnloadedTypeDescription(TypeDescription typeDescription) {
		this.typeDescription = typeDescription;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return typeDescription.getDeclaredAnnotations().isAnnotationPresent( annotationType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return typeDescription.getName();
	}
}
