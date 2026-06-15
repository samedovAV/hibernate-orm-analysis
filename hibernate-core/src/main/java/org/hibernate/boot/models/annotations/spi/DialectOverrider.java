/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import org.hibernate.annotations.DialectOverride;
import org.hibernate.dialect.Dialect;
import org.hibernate.models.spi.AnnotationDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract for {@linkplain DialectOverride.OverridesAnnotation override} annotations defined in {@linkplain DialectOverride}
 *
 * @author Steve Ebersole
 */
public interface DialectOverrider<O extends Annotation> extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends Dialect> dialect();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DialectOverride.Version before();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DialectOverride.Version sameOrAfter();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	O override();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AnnotationDescriptor<O> getOverriddenDescriptor();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean matches(Dialect dialectToMatch);
}
