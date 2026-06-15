/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.mutation.internal.temptable;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.temptable.TemporaryTable;
import org.hibernate.dialect.temptable.TemporaryTableStrategy;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.config.spi.StandardConverters;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.internal.MappingModelCreationProcess;

import java.util.Objects;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Strategy based on ANSI SQL's definition of a "local temporary table" (local to each db session).
 *
 * @author Steve Ebersole
 */
public class LocalTemporaryTableStrategy {

	public static final String SHORT_NAME = "local_temporary";
	public static final String DROP_ID_TABLES = "hibernate.query.mutation_strategy.local_temporary.drop_tables";

	private final TemporaryTable temporaryTable;
	private final SessionFactoryImplementor sessionFactory;

	private boolean dropIdTables;

	public LocalTemporaryTableStrategy(TemporaryTable temporaryTable, SessionFactoryImplementor sessionFactory) {
		this.temporaryTable = temporaryTable;
		this.sessionFactory = sessionFactory;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected static TemporaryTableStrategy requireLocalTemporaryTableStrategy(Dialect dialect) {
		return Objects.requireNonNull( dialect.getLocalTemporaryTableStrategy(),
				"Dialect does not define a local temporary table strategy: " + dialect.getClass().getSimpleName() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporaryTableStrategy getTemporaryTableStrategy() {
		return castNonNull( sessionFactory.getJdbcServices().getDialect().getLocalTemporaryTableStrategy() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare(MappingModelCreationProcess mappingModelCreationProcess, JdbcConnectionAccess connectionAccess) {
		final ConfigurationService configService =
				mappingModelCreationProcess.getCreationContext()
						.getBootstrapContext().getServiceRegistry()
						.requireService( ConfigurationService.class );
		dropIdTables = configService.getSetting( DROP_ID_TABLES, StandardConverters.BOOLEAN, false );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release(SessionFactoryImplementor sessionFactory, JdbcConnectionAccess connectionAccess) {
		// Nothing to do here. This happens through ExecuteWithTemporaryTableHelper.performAfterTemporaryTableUseActions
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporaryTable getTemporaryTable() {
		return temporaryTable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDropIdTables() {
		return dropIdTables;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryImplementor getSessionFactory() {
		return sessionFactory;
	}
}
