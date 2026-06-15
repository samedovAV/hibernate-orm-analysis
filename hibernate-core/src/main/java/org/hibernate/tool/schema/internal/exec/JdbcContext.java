/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.service.ServiceRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to JDBC context for schema tooling activity.
 *
 * @author Steve Ebersole
 */
public interface JdbcContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcConnectionAccess getJdbcConnectionAccess();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Dialect getDialect();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlStatementLogger getSqlStatementLogger();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlExceptionHelper getSqlExceptionHelper();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();
}
