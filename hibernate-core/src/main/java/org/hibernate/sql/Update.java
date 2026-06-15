/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Internal;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A SQL {@code UPDATE} statement.
 *
 * @author Gavin King
 */
@Internal
public class Update implements RestrictionRenderingContext {
	protected String tableName;
	protected String comment;
	protected Map<String,String> assignments = new LinkedHashMap<>();
	protected List<Restriction> restrictions = new ArrayList<>();

	private final ParameterMarkerStrategy parameterMarkerStrategy;
	private int parameterCount;

	public Update(SessionFactoryImplementor factory) {
		this( factory.getParameterMarkerStrategy() );
	}

	public Update(ParameterMarkerStrategy parameterMarkerStrategy) {
		this.parameterMarkerStrategy = parameterMarkerStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableName() {
		return tableName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update setComment(String comment) {
		this.comment = comment;
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Update addAssignments(String... columnNames) {
		for ( String columnName : columnNames ) {
			addAssignment( columnName );
		}
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Update addAssignment(String columnName) {
		return addAssignment( columnName, "?" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addAssignment(String columnName, String valueExpression) {
		assignments.put( columnName, valueExpression );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addAssignments(Collection<String> columns) {
		columns.forEach( this::addAssignment );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addRestriction(String column) {
		restrictions.add( new ComparisonRestriction( column ) );
		return this;
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Update addRestriction(String... columns) {
		for ( int i = 0; i < columns.length; i++ ) {
			final String columnName = columns[ i ];
			if ( columnName != null ) {
				addRestriction( columnName );
			}
		}
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addRestriction(String column, String value) {
		restrictions.add( new ComparisonRestriction( column, value ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addRestriction(String column, ComparisonRestriction.Operator op, String value) {
		restrictions.add( new ComparisonRestriction( column, op, value ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String normalizeExpressionFragment(String rhs) {
		return rhs.equals( "?" )
				? parameterMarkerStrategy.createMarker( ++parameterCount, null )
				: rhs;
	}

	@SuppressWarnings("UnusedReturnValue")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addColumnIsNullRestriction(String columnName) {
		restrictions.add( new NullnessRestriction( columnName ) );
		return this;
	}

	@SuppressWarnings("UnusedReturnValue")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Update addColumnIsNotNullRestriction(String columnName) {
		restrictions.add( new NullnessRestriction( columnName, false ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toStatementString() {
		final StringBuilder buf = new StringBuilder( ( assignments.size() * 15) + tableName.length() + 10 );

		applyComment( buf );
		buf.append( "update " ).append( tableName );
		applyAssignments( buf );
		applyRestrictions( buf );

		return buf.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void applyComment(StringBuilder buf) {
		if ( comment != null ) {
			buf.append( "/* " ).append( Dialect.escapeComment( comment ) ).append( " */ " );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void applyAssignments(StringBuilder buf) {
		buf.append( " set " );

		final Iterator<Map.Entry<String,String>> entries = assignments.entrySet().iterator();
		while ( entries.hasNext() ) {
			final Map.Entry<String,String> entry = entries.next();
			buf.append( entry.getKey() )
					.append( '=' )
					.append( normalizeExpressionFragment( entry.getValue() ) );
			if ( entries.hasNext() ) {
				buf.append( ", " );
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void applyRestrictions(StringBuilder buf) {
		if ( restrictions.isEmpty() ) {
			return;
		}

		buf.append( " where " );

		for ( int i = 0; i < restrictions.size(); i++ ) {
			if ( i > 0 ) {
				buf.append( " and " );
			}

			final Restriction restriction = restrictions.get( i );
			restriction.render( buf, this );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String makeParameterMarker() {
		return parameterMarkerStrategy.createMarker( ++parameterCount, null );
	}
}
