/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.sql.ast.tree.from.TableGroupProducer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A generator for new incremental SQL aliases based on a stem
 *
 * @author Steve Ebersole
 */
public interface SqlAliasBase {
	/**
	 * The stem for unique alias generation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAliasStem();

	/**
	 * Generates a new alias based on the stem
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String generateNewAlias();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static SqlAliasBase from(
			SqlAliasBase explicitBase,
			String explicitSourceAlias,
			TableGroupProducer producer,
			SqlAliasBaseGenerator generator) {
		if ( explicitBase != null ) {
			return explicitBase;
		}

		final String baseName;
//		if ( explicitSourceAlias != null && !Character.isDigit( explicitSourceAlias.charAt( 0 ) ) ) {
//			baseName = explicitSourceAlias;
//		}
//		else {
			baseName = producer.getSqlAliasStem();
//		}

		return generator.createSqlAliasBase( baseName );
	}
}
