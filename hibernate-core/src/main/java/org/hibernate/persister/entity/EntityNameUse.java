/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the kind of entity name use.
 */
@Incubating
public final class EntityNameUse {

	public static final EntityNameUse PROJECTION = new EntityNameUse( UseKind.PROJECTION, true );
	public static final EntityNameUse EXPRESSION = new EntityNameUse( UseKind.EXPRESSION, true );
	public static final EntityNameUse TREAT = new EntityNameUse( UseKind.TREAT, true );
	public static final EntityNameUse BASE_TREAT = new EntityNameUse( UseKind.TREAT, null );
	public static final EntityNameUse OPTIONAL_TREAT = new EntityNameUse( UseKind.TREAT, false );
	public static final EntityNameUse FILTER = new EntityNameUse( UseKind.FILTER, true );

	private final UseKind kind;
	private final Boolean requiresRestriction;

	private EntityNameUse(UseKind kind, Boolean requiresRestriction) {
		this.kind = kind;
		this.requiresRestriction = requiresRestriction;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static EntityNameUse get(UseKind kind) {
		switch ( kind ) {
			case PROJECTION:
				return PROJECTION;
			case EXPRESSION:
				return EXPRESSION;
			case TREAT:
				return TREAT;
			case FILTER:
				return FILTER;
		}
		throw new IllegalArgumentException( "Unknown kind: " + kind );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UseKind getKind() {
		return kind;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean requiresRestriction() {
		return requiresRestriction != Boolean.FALSE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityNameUse stronger(EntityNameUse other) {
		if ( other == null || kind.isStrongerThan( other.kind ) ) {
			return this;
		}
		if ( kind == other.kind && kind == UseKind.TREAT ) {
			return requiresRestriction == null ? other : this;
		}
		return other.kind.isStrongerThan( kind ) ? other : get( other.kind );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityNameUse weaker(EntityNameUse other) {
		if ( other == null || kind.isWeakerThan( other.kind ) ) {
			return this;
		}
		if ( kind == other.kind && kind == UseKind.TREAT ) {
			return requiresRestriction == null ? other : this;
		}
		return other.kind.isWeakerThan( kind ) ? other : get( other.kind );
	}

	public enum UseKind {
		/**
		 * An entity type is used through a path that appears in the {@code SELECT} clause somehow.
		 * This use kind is registered for top level select items or join fetches.
		 */
		PROJECTION,
		/**
		 * An entity type is used through a path expression, but doesn't match the criteria for {@link #PROJECTION}.
		 */
		EXPRESSION,
		/**
		 * An entity type is used through a treat expression.
		 */
		TREAT,
		/**
		 * An entity type is filtered for through a type restriction predicate i.e. {@code type(alias) = Subtype}.
		 */
		FILTER;

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isStrongerThan(UseKind other) {
			return ordinal() > other.ordinal();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UseKind stronger(UseKind other) {
			return other == null || isStrongerThan( other ) ? this : other;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isWeakerThan(UseKind other) {
			return ordinal() < other.ordinal();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UseKind weaker(UseKind other) {
			return other == null || isWeakerThan( other ) ? this : other;
		}
	}
}
