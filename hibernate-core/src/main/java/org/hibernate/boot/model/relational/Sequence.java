/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.relational;

import org.hibernate.HibernateException;
import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a database {@code SEQUENCE}.
 *
 * @author Steve Ebersole
 */
public class Sequence implements ContributableDatabaseObject {
	public static class Name extends QualifiedNameParser.NameParts {
		public Name(
				Identifier catalogIdentifier,
				Identifier schemaIdentifier,
				Identifier nameIdentifier) {
			super( catalogIdentifier, schemaIdentifier, nameIdentifier );
		}
	}

	private final QualifiedSequenceName name;
	private final String exportIdentifier;
	private final String contributor;
	private final int initialValue;
	private final int incrementSize;
	private final String options;

	public Sequence(
			String contributor,
			Identifier catalogName,
			Identifier schemaName,
			Identifier sequenceName) {
		this( contributor, catalogName, schemaName, sequenceName, 1, 1, null );
	}

	public Sequence(
			String contributor,
			Identifier catalogName,
			Identifier schemaName,
			Identifier sequenceName,
			int initialValue,
			int incrementSize) {
		this( contributor, catalogName, schemaName, sequenceName, initialValue, incrementSize, null );
	}

	public Sequence(
			String contributor,
			Identifier catalogName,
			Identifier schemaName,
			Identifier sequenceName,
			int initialValue,
			int incrementSize,
			String options) {
		this.contributor = contributor;
		this.name = new QualifiedSequenceName( catalogName, schemaName, sequenceName );
		this.exportIdentifier = name.render();
		this.initialValue = initialValue;
		this.incrementSize = incrementSize;
		this.options = options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedSequenceName getName() {
		return name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExportIdentifier() {
		return exportIdentifier;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContributor() {
		return contributor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getInitialValue() {
		return initialValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getIncrementSize() {
		return incrementSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOptions() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void validate(int initialValue, int incrementSize) {
		if ( this.initialValue != initialValue ) {
			throw new HibernateException(
					String.format(
							"Multiple generators using the database sequence '%s' are defined, with conflicting 'initialValue' specifications: %s, %s",
							exportIdentifier,
							this.initialValue,
							initialValue
					)
			);
		}
		if ( this.incrementSize != incrementSize ) {
			throw new HibernateException(
					String.format(
							"Multiple generators using the database sequence '%s' are defined, with conflicting 'allocationSize' specifications: %s, %s",
							exportIdentifier,
							this.incrementSize,
							incrementSize
					)
			);
		}
	}
}
