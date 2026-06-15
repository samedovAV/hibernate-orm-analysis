/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;

import org.hibernate.boot.model.naming.ImplicitAnyKeyColumnNameSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface AnyKeySource extends ImplicitAnyKeyColumnNameSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateTypeSource getTypeSource();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<RelationalValueSource> getRelationalValueSources();
}
