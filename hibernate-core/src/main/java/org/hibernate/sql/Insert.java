/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.Internal;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.generator.OnExecutionGenerator;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A SQL {@code INSERT} statement.
 *
 * @author Gavin King
 */
@Internal
public class Insert {

	protected String tableName;
	protected String comment;

	protected Map<String,String> columns = new LinkedHashMap<>();

	private final Dialect dialect;
	private final ParameterMarkerStrategy parameterMarkerStrategy;
	private int parameterCount;

	public Insert(SessionFactoryImplementor sessionFactory) {
		this(
				sessionFactory.getJdbcServices().getJdbcEnvironment().getDialect(),
				sessionFactory.getParameterMarkerStrategy()
		);
	}

	public Insert(Dialect dialect, ParameterMarkerStrategy parameterMarkerStrategy) {
		this.dialect = dialect;
		this.parameterMarkerStrategy = parameterMarkerStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Dialect getDialect() {
		return dialect;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Insert setComment(String comment) {
		this.comment = comment;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, String> getColumns() {
		return columns;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Insert addColumn(String columnName) {
		return addColumn( columnName, "?" );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Insert addColumns(String[] columnNames) {
		for ( String columnName : columnNames ) {
			addColumn( columnName );
		}
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Insert addColumns(String[] columnNames, boolean[] insertable) {
		for ( int i=0; i<columnNames.length; i++ ) {
			if ( insertable[i] ) {
				addColumn( columnNames[i] );
			}
		}
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Insert addColumns(String[] columnNames, boolean[] insertable, String[] valueExpressions) {
		for ( int i=0; i<columnNames.length; i++ ) {
			if ( insertable[i] ) {
				addColumn( columnNames[i], valueExpressions[i] );
			}
		}
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Insert addColumn(String columnName, String valueExpression) {
		columns.put( columnName, valueExpression );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Insert addIdentityColumn(String columnName) {
		final IdentityColumnSupport identityColumnSupport = dialect.getIdentityColumnSupport();
		if ( identityColumnSupport.hasIdentityInsertKeyword() ) {
			addColumn( columnName, identityColumnSupport.getIdentityInsertString() );
		}
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Insert addGeneratedColumns(String[] columnNames, OnExecutionGenerator generator) {
		if ( generator.referenceColumnsInSql( dialect ) ) {
			String[] columnValues = generator.getReferencedColumnValues( dialect );
			if ( columnNames.length != columnValues.length ) {
				throw new MappingException("wrong number of generated columns"); //TODO!
			}
			for ( int i = 0; i < columnNames.length; i++ ) {
				addColumn( columnNames[i], columnValues[i] );
			}
		}
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Insert setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toStatementString() {
		final StringBuilder buf = new StringBuilder( columns.size()*15 + tableName.length() + 10 );

		if ( comment != null ) {
			buf.append( "/* " ).append( Dialect.escapeComment( comment ) ).append( " */ " );
		}

		buf.append( "insert into " ).append( tableName );

		if ( columns.isEmpty() ) {
			if ( dialect.supportsNoColumnsInsert() ) {
				buf.append( ' ' ).append( dialect.getNoColumnsInsertString() );
			}
			else {
				throw new MappingException(
						String.format(
								"The INSERT statement for table [%s] contains no column, and this is not supported by [%s]",
								tableName,
								dialect
						)
				);
			}
		}
		else {
			buf.append( " (" );
			renderInsertionSpec( buf );
			buf.append( ") values (" );
			renderRowValues( buf );
			buf.append( ')' );
		}
		return buf.toString();
	}


	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void renderInsertionSpec(StringBuilder buf) {
		final Iterator<String> itr = columns.keySet().iterator();
		while ( itr.hasNext() ) {
			buf.append( itr.next() );
			if ( itr.hasNext() ) {
				buf.append( ", " );
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void renderRowValues(StringBuilder buf) {
		final Iterator<String> itr = columns.values().iterator();
		while ( itr.hasNext() ) {
			buf.append( normalizeExpressionFragment( itr.next() ) );
			if ( itr.hasNext() ) {
				buf.append( ", " );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String normalizeExpressionFragment(String rhs) {
		return rhs.equals( "?" )
				? parameterMarkerStrategy.createMarker( ++parameterCount, null )
				: rhs;
	}
}
