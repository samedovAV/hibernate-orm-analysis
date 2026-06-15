/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query.internal;

import jakarta.persistence.Timeout;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.FlushMode;
import org.hibernate.boot.query.NamedHqlQueryDefinition;
import org.hibernate.boot.query.NamedMutationDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.models.spi.AnnotationTarget;
import org.hibernate.query.named.NamedSqmQueryMemento;
import org.hibernate.query.named.internal.HqlMutationMementoImpl;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a {@linkplain jakarta.persistence.NamedStatement}.
 *
 * @author Steve Ebersole
 */
public class NamedHqlMutationDefinitionImpl<T>
	extends AbstractNamedQueryDefinition<T>
		implements NamedHqlQueryDefinition<T>, NamedMutationDefinition<T> {
	private final String hql;
	private final @Nullable Class<T> targetType;

	public NamedHqlMutationDefinitionImpl(
			String name, String location,
			@Nonnull String hql, @Nullable Class<T> targetType,
			FlushMode flushMode, Timeout timeout, String comment, Map<String, Object> hints) {
		super( name, location, flushMode, timeout, comment, hints );
		this.hql = hql;
		this.targetType = targetType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getHqlString() {
		return hql;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getStatementString() {
		return getHqlString();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSqmQueryMemento<T> resolve(SessionFactoryImplementor factory) {
		return new HqlMutationMementoImpl<>(
				getRegistrationName(),
				hql, targetType, Map.of(),
				flushMode, timeout, comment, hints
		);
	}


	/// Build a definition from JPA's [jakarta.persistence.NamedStatement] annotation.
	///
	/// @param annotation The annotation.
	/// @param target Where the annotation was found.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static NamedHqlMutationDefinitionImpl<?> from(jakarta.persistence.NamedStatement annotation, AnnotationTarget target) {
		return new NamedHqlMutationDefinitionImpl<>(
				annotation.name(),
				target == null ? null : target.getName(),
				annotation.statement(),
				null,
				null,
				null,
				null,
				Helper.extractHints( annotation.hints() )
		);
	}

}
