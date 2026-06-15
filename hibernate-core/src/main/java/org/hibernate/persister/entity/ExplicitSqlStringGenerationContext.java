/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.env.spi.QualifiedObjectNameFormatter;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SqlStringGenerationContext implementation with support for overriding the
 * default catalog and schema
 *
 * @author Steve Ebersole
 */
public class ExplicitSqlStringGenerationContext implements SqlStringGenerationContext {
	private final SessionFactoryImplementor factory;
	private final Identifier defaultCatalog;
	private final Identifier defaultSchema;

	public ExplicitSqlStringGenerationContext(
			String defaultCatalog,
			String defaultSchema,
			SessionFactoryImplementor factory) {
		this.factory = factory;
		this.defaultCatalog = defaultCatalog != null
				? toIdentifier( defaultCatalog )
				: toIdentifier( factory.getSessionFactoryOptions().getDefaultCatalog() );
		this.defaultSchema = defaultSchema != null
				? toIdentifier( defaultSchema )
				: toIdentifier( factory.getSessionFactoryOptions().getDefaultSchema() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private JdbcEnvironment getJdbcEnvironment() {
		return factory.getJdbcServices().getJdbcEnvironment();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Dialect getDialect() {
		return factory.getJdbcServices().getDialect();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Identifier toIdentifier(String text) {
		return getJdbcEnvironment().getIdentifierHelper().toIdentifier( text );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getDefaultCatalog() {
		return defaultCatalog;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getDefaultSchema() {
		return defaultSchema;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String format(QualifiedTableName qualifiedName) {
		return nameFormater().format( withDefaults( qualifiedName ), getDialect() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private QualifiedObjectNameFormatter nameFormater() {
		//noinspection deprecation
		return getJdbcEnvironment().getQualifiedObjectNameFormatter();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String format(QualifiedSequenceName qualifiedName) {
		return nameFormater().format( withDefaults( qualifiedName ), getDialect() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String format(QualifiedName qualifiedName) {
		return nameFormater().format( withDefaults( qualifiedName ), getDialect() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String formatWithoutCatalog(QualifiedSequenceName qualifiedName) {
		return nameFormater().format( nameToFormat( qualifiedName ), getDialect() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private QualifiedSequenceName nameToFormat(QualifiedSequenceName qualifiedName) {
		if ( qualifiedName.getCatalogName() != null
				|| qualifiedName.getSchemaName() == null && defaultSchema != null ) {
			return new QualifiedSequenceName(
					null,
					schemaWithDefault( qualifiedName.getSchemaName() ),
					qualifiedName.getSequenceName()
			);
		}
		else {
			return qualifiedName;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMigration() {
		return false;
	}
}
