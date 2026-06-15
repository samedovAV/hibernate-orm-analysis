/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.generator.internal;

import org.hibernate.AnnotationException;
import org.hibernate.annotations.Generated;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.generator.OnExecutionGenerator;

import java.util.EnumSet;

import static org.hibernate.generator.EventTypeSets.fromArray;
import static org.hibernate.internal.util.StringHelper.isEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A fairly generic {@link OnExecutionGenerator} which marks a property as generated in the
 * database with semantics given explicitly by a {@link Generated @Generated} annotation.
 *
 * @see Generated
 *
 * @author Steve Ebersole
 * @author Gunnar Morling
 */
public class GeneratedGeneration implements OnExecutionGenerator {

	private final EnumSet<EventType> eventTypes;
	private final boolean writable;
	private final String[] sql;
	private Class<?> generatedType;

	public GeneratedGeneration(EnumSet<EventType> eventTypes) {
		this.eventTypes = eventTypes;
		writable = false;
		sql = null;
	}

	public GeneratedGeneration(Generated annotation) {
		eventTypes = fromArray( annotation.event() );
		sql = isEmpty( annotation.sql() ) ? null : new String[] { annotation.sql() };
		writable = annotation.writable();
		if ( sql != null && writable ) {
			throw new AnnotationException( "A field marked '@Generated(writable=true)' may not specify explicit 'sql'" );
		}
	}

	public GeneratedGeneration(Generated annotation, GeneratorCreationContext context) {
		this( annotation );
		generatedType = context.getType().getReturnedClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EnumSet<EventType> getEventTypes() {
		return eventTypes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getGeneratedType() {
		return generatedType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean referenceColumnsInSql(Dialect dialect) {
		// include the column in when the field is writable,
		// or when there is an explicit SQL expression
		return writable || sql != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getReferencedColumnValues(Dialect dialect) {
		return sql;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean writePropertyValue() {
		// include a ? parameter when the field is writable,
		// but there is no explicit SQL expression
		return writable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean generatedOnExecution(Object entity, SharedSessionContractImplementor session) {
		if ( writable ) {
			// When this is the identifier generator and writable is true, allow pre-assigned identifiers
			final var entityPersister = session.getEntityPersister( null, entity );
			return entityPersister.getGenerator() != this
				|| entityPersister.getIdentifier( entity, session ) == null;
		}
		else {
			return true;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean allowAssignedIdentifiers() {
		return writable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean allowMutation() {
		// the user may specify @Immutable if mutation should be disallowed
		return true;
	}
}
