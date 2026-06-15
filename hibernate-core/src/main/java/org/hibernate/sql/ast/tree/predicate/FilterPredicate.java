/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Filter;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;

import static org.hibernate.internal.util.collections.CollectionHelper.arrayList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Collection of {@link FilterFragmentPredicate} sub-predicates, each
 * representing one {@linkplain org.hibernate.Filter enabled filter} restriction.
 *
 * @author Steve Ebersole
 * @author Nathan Xu
 */
public class FilterPredicate implements Predicate {
	private final List<FilterFragmentPredicate> fragments = new ArrayList<>();

//	private List<FilterJdbcParameter> parameters;

	public FilterPredicate() {
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyFragment(FilterFragmentPredicate predicate) {
		fragments.add( predicate );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyFragment(String processedFragment, Filter filter, List<String> parameterNames) {
		applyFragment( new FilterFragmentPredicate( processedFragment, filter, parameterNames ) );
	}

//	public void applyParameter(FilterJdbcParameter parameter) {
//		if ( parameters == null ) {
//			parameters = new ArrayList<>();
//		}
//		parameters.add( parameter );
//	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<FilterFragmentPredicate> getFragments() {
		return fragments;
	}

//	public List<FilterJdbcParameter> getParameters() {
//		return parameters;
//	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEmpty() {
		return fragments.isEmpty();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitFilterPredicate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return null;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static List<FilterFragmentParameter> fragmentParameters(Filter filter, List<String> parameterNames) {
		if ( CollectionHelper.isEmpty( parameterNames ) ) {
			return null;
		}
		else {
			final int parameterCount = parameterNames.size();
			final List<FilterFragmentParameter> parameters = arrayList( parameterCount );
			for ( int i = 0; i < parameterCount; i++ ) {
				final String paramName = parameterNames.get( i );
				final Object paramValue = filter.getParameterValue( paramName );
				final var jdbcMapping = filter.getFilterDefinition().getParameterJdbcMapping( paramName );
				parameters.add( new FilterFragmentParameter( filter.getName(), paramName, jdbcMapping, paramValue ) );
			}
			return parameters;
		}
	}

	public static class FilterFragmentParameter {
		private final String filterName;
		private final String parameterName;
		private final JdbcMapping valueMapping;
		private final Object value;

		FilterFragmentParameter(String filterName, String parameterName, JdbcMapping valueMapping, Object value) {
			this.filterName = filterName;
			this.parameterName = parameterName;
			this.valueMapping = valueMapping;
			this.value = value;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getFilterName() {
			return filterName;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getParameterName() {
			return parameterName;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcMapping getValueMapping() {
			return valueMapping;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object getValue() {
			return value;
		}
	}

	public static class FilterFragmentPredicate implements Predicate {
		private final Filter filter;
		private final String sqlFragment;
		private final List<FilterFragmentParameter> parameters;

		FilterFragmentPredicate(String sqlFragment, Filter filter, List<String> parameterNames) {
			this.filter = filter;
			this.sqlFragment = sqlFragment;
			this.parameters = fragmentParameters( filter, parameterNames );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Filter getFilter() {
			return filter;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getFilterName() {
			return filter.getName();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getSqlFragment() {
			return sqlFragment;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public List<FilterFragmentParameter> getParameters() {
			return parameters;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void accept(SqlAstWalker sqlTreeWalker) {
			sqlTreeWalker.visitFilterFragmentPredicate( this );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcMappingContainer getExpressionType() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isEmpty() {
			return false;
		}
	}
}
