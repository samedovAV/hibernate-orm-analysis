/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.SqlTypedMapping;
import org.hibernate.sql.ast.tree.expression.SqlTypedExpression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqlTypedMappingJdbcParameter extends AbstractJdbcParameter implements SqlTypedExpression {

	private final SqlTypedMapping sqlTypedMapping;

	public SqlTypedMappingJdbcParameter(SqlTypedMapping sqlTypedMapping) {
		super( sqlTypedMapping.getJdbcMapping() );
		this.sqlTypedMapping = sqlTypedMapping;
	}

	public SqlTypedMappingJdbcParameter(SqlTypedMapping sqlTypedMapping, @Nullable Integer parameterId) {
		super( sqlTypedMapping.getJdbcMapping(), parameterId );
		this.sqlTypedMapping = sqlTypedMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlTypedMapping getSqlTypedMapping() {
		return sqlTypedMapping;
	}
}
