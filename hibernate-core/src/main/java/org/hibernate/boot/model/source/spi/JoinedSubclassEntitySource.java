/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JoinedSubclassEntitySource extends SubclassEntitySource, ForeignKeyContributingSource {
	/**
	 * The {@code PrimaryKeyJoinColumns} mapping for the joined-subclass.
	 *
	 * @return The {@code PrimaryKeyJoinColumnSource} lists defined on the joined subclass or {@code null} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnSource> getPrimaryKeyColumnSources();
}
