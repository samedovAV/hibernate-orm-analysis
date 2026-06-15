/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.internal;

import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The standard ParameterMarkerStrategy based on the standard JDBC {@code ?} marker
 *
 * @author Steve Ebersole
 */
public class ParameterMarkerStrategyStandard implements ParameterMarkerStrategy {
	/**
	 * Singleton access
	 */
	public static final ParameterMarkerStrategyStandard INSTANCE = new ParameterMarkerStrategyStandard();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String createMarker(int position, JdbcType jdbcType) {
		return "?";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isStandardRenderer(ParameterMarkerStrategy check) {
		return check == null || ParameterMarkerStrategyStandard.class.equals( check.getClass() );
	}
}
