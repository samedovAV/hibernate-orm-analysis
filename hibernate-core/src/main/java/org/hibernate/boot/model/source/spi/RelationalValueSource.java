/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Unifying interface for {@link ColumnSource} and {@link DerivedValueSource}.
 *
 * @author Steve Ebersole
 *
 * @see ColumnSource
 * @see DerivedValueSource
 */
public interface RelationalValueSource {
	/**
	 * @return returns the name of the table that contains this value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getContainingTableName();

	/**
	 * Retrieve the nature of this relational value.  Is it a column?  Or is it a derived value (formula)?
	 *
	 * @return The nature.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Nature getNature();

	enum Nature {
		COLUMN( ColumnSource.class ),
		DERIVED( DerivedValueSource.class );

		private final Class<? extends RelationalValueSource> specificContractClass;

		Nature(Class<? extends RelationalValueSource> specificContractClass) {
			this.specificContractClass = specificContractClass;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<? extends RelationalValueSource> getSpecificContractClass() {
			return specificContractClass;
		}
	}
}
