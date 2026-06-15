/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.hibernate.annotations.Parent;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;

import static org.hibernate.internal.util.ReflectHelper.getterMethodOrNull;
import static org.hibernate.property.access.internal.AccessStrategyHelper.fieldOrNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

final class ParentPropertyAccessHelper {

	private ParentPropertyAccessHelper() {
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static PropertyAccess parentPropertyAccess(
			String parentInjectionAttributeName,
			EmbeddableMappingType embeddableMappingType) {
		if ( parentInjectionAttributeName == null ) {
			return null;
		}

		final var embeddableJavaType = embeddableMappingType.getMappedJavaType().getJavaTypeClass();
		// For @Parent, the annotated member defines how the owner reference must be injected.
		// Falling back to generic mixed access would incorrectly prefer a field for getter-based mappings.
		if ( hasParentAnnotation( fieldOrNull( embeddableJavaType, parentInjectionAttributeName ) ) ) {
			return PropertyAccessStrategyFieldImpl.INSTANCE.buildPropertyAccess(
					embeddableJavaType,
					parentInjectionAttributeName,
					true
			);
		}
		else if ( hasParentAnnotation( getterMethodOrNull( embeddableJavaType, parentInjectionAttributeName ) ) ) {
			return PropertyAccessStrategyBasicImpl.INSTANCE.buildPropertyAccess(
					embeddableJavaType,
					parentInjectionAttributeName,
					true
			);
		}
		else {
			return PropertyAccessStrategyBasicImpl.INSTANCE.buildPropertyAccess(
					embeddableJavaType,
					parentInjectionAttributeName,
					true
			);
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean hasParentAnnotation(Field field) {
		return field != null && field.isAnnotationPresent( Parent.class );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean hasParentAnnotation(Method getterMethod) {
		return getterMethod != null && getterMethod.isAnnotationPresent( Parent.class );
	}
}
