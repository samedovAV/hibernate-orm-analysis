/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.DialectOverride;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.models.spi.AnnotationDescriptor;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for {@linkplain DialectOverrider} annotations
 *
 * @author Steve Ebersole
 */
public abstract class AbstractOverrider<O extends Annotation> implements DialectOverrider<O> {
	private Class<? extends Dialect> dialect;
	private DialectOverride.Version before;
	private DialectOverride.Version sameOrAfter;

	public AbstractOverrider() {
	}

	public AbstractOverrider(
			Map<String, Object> attributeValues,
			AnnotationDescriptor<?> descriptor,
			ModelsContext modelContext) {
		dialect( (Class<? extends Dialect>) attributeValues.get( "dialect" ) );
		before( (DialectOverride.Version) attributeValues.get( "before" ) );
		sameOrAfter( (DialectOverride.Version) attributeValues.get( "sameOrAfter" ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Dialect> dialect() {
		return dialect;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void dialect(Class<? extends Dialect> dialect) {
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DialectOverride.Version before() {
		return before;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void before(DialectOverride.Version before) {
		this.before = before;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DialectOverride.Version sameOrAfter() {
		return sameOrAfter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sameOrAfter(DialectOverride.Version sameOrAfter) {
		this.sameOrAfter = sameOrAfter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean matches(Dialect dialectToMatch) {
		if ( !dialect().isAssignableFrom( dialectToMatch.getClass() ) ) {
			return false;
		}

		final DatabaseVersion versionToMatch = dialectToMatch.getVersion();
		return versionToMatch.isBefore( before().major(), before().minor() )
				&& versionToMatch.isSameOrAfter( sameOrAfter().major(), sameOrAfter().minor() );
	}
}
