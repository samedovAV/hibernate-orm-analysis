/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.exec.spi.JdbcCallFunctionReturn;
import org.hibernate.sql.exec.spi.JdbcCallParameterExtractor;
import org.hibernate.sql.exec.spi.JdbcCallParameterRegistration;
import org.hibernate.sql.exec.spi.JdbcCallRefCursorExtractor;
import org.hibernate.sql.exec.spi.JdbcOperationQueryCall;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.exec.spi.JdbcParameterBinding;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingProducer;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the actual call, allowing iterative building of the parts.
 *
 * @author Steve Ebersole
 */
public class JdbcCallImpl implements JdbcOperationQueryCall {
	private final String callableName;

	private final JdbcCallFunctionReturn functionReturn;
	private final List<JdbcCallParameterRegistration> parameterRegistrations;
	private final List<JdbcParameterBinder> parameterBinders;
	private final List<JdbcCallParameterExtractor<?>> parameterExtractors;
	private final List<JdbcCallRefCursorExtractor> refCursorExtractors;

	public JdbcCallImpl(Builder builder) {
		this(
				builder.callableName,
				builder.functionReturn,
				builder.parameterRegistrations == null
						? emptyList()
						: unmodifiableList( builder.parameterRegistrations ),
				builder.parameterBinders == null
						? emptyList()
						: unmodifiableList( builder.parameterBinders ),
				builder.parameterExtractors == null
						? emptyList()
						: unmodifiableList( builder.parameterExtractors ),
				builder.refCursorExtractors == null
						? emptyList()
						: unmodifiableList( builder.refCursorExtractors )
		);
	}

	protected JdbcCallImpl(
			String callableName,
			JdbcCallFunctionReturn functionReturn,
			List<JdbcCallParameterRegistration> parameterRegistrations,
			List<JdbcParameterBinder> parameterBinders,
			List<JdbcCallParameterExtractor<?>> parameterExtractors,
			List<JdbcCallRefCursorExtractor> refCursorExtractors) {
		this.callableName = callableName;
		this.functionReturn = functionReturn;
		this.parameterRegistrations = parameterRegistrations;
		this.parameterBinders = parameterBinders;
		this.parameterExtractors = parameterExtractors;
		this.refCursorExtractors = refCursorExtractors;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlString() {
		return callableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcCallFunctionReturn getFunctionReturn() {
		return functionReturn;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JdbcCallParameterRegistration> getParameterRegistrations() {
		return parameterRegistrations == null ? emptyList() : parameterRegistrations;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JdbcParameterBinder> getParameterBinders() {
		return parameterBinders == null ? emptyList() : parameterBinders;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getAffectedTableNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean dependsOnParameterBindings() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<JdbcParameter, JdbcParameterBinding> getAppliedParameters() {
		return emptyMap();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCompatibleWith(
			JdbcParameterBindings jdbcParameterBindings, QueryOptions queryOptions) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JdbcCallParameterExtractor<?>> getParameterExtractors() {
		return parameterExtractors == null ? emptyList() : parameterExtractors;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JdbcCallRefCursorExtractor> getCallRefCursorExtractors() {
		return refCursorExtractors == null ? emptyList() : refCursorExtractors;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValuesMappingProducer getJdbcValuesMappingProducer() {
		return null;
	}

	public static class Builder {
		private String callableName;
		private JdbcCallFunctionReturn functionReturn;

		private List<JdbcCallParameterRegistration> parameterRegistrations;
		private List<JdbcParameterBinder> parameterBinders;
		private List<JdbcCallParameterExtractor<?>> parameterExtractors;
		private List<JdbcCallRefCursorExtractor> refCursorExtractors;

		public Builder() {
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcOperationQueryCall buildJdbcCall() {
			return new JdbcCallImpl( this );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setCallableName(String callableName) {
			this.callableName = callableName;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setFunctionReturn(JdbcCallFunctionReturn functionReturn) {
			this.functionReturn = functionReturn;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void addParameterRegistration(JdbcCallParameterRegistration registration) {
			if ( parameterRegistrations == null ) {
				parameterRegistrations = new ArrayList<>();
			}

			// todo (6.0) : add validation based on ParameterStrategy

			parameterRegistrations.add( registration );

			switch ( registration.getParameterMode() ) {
				case REF_CURSOR: {
					addParameterBinder( JdbcParameterBinder.NOOP );
					addRefCursorExtractor( registration.getRefCursorExtractor() );
					break;
				}
				case IN: {
					addParameterBinder( registration.getParameterBinder() );
					break;
				}
				case INOUT: {
					addParameterBinder( registration.getParameterBinder() );
					addParameterExtractor( registration.getParameterExtractor() );
					break;
				}
				case OUT: {
					addParameterBinder( JdbcParameterBinder.NOOP );
					addParameterExtractor( registration.getParameterExtractor() );
					break;
				}
				default: {
					throw new HibernateException( "Unexpected ParameterMode : " + registration.getParameterMode() );
				}
			}
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private void addParameterBinder(JdbcParameterBinder binder) {
			if ( parameterBinders == null ) {
				parameterBinders = new ArrayList<>();
			}
			parameterBinders.add( binder );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private void addParameterExtractor(JdbcCallParameterExtractor<?> extractor) {
			if ( parameterExtractors == null ) {
				parameterExtractors = new ArrayList<>();
			}
			parameterExtractors.add( extractor );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private void addRefCursorExtractor(JdbcCallRefCursorExtractor extractor) {
			if ( refCursorExtractors == null ) {
				refCursorExtractors = new ArrayList<>();
			}
			refCursorExtractors.add( extractor );
		}

	}
}
