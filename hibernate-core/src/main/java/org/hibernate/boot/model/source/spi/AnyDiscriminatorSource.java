/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Map;

import org.hibernate.boot.model.naming.ImplicitAnyDiscriminatorColumnNameSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Source information about the discriminator for an ANY mapping
 *
 * @author Steve Ebersole
 */
public interface AnyDiscriminatorSource extends ImplicitAnyDiscriminatorColumnNameSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateTypeSource getTypeSource();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RelationalValueSource getRelationalValueSource();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String,String> getValueMappings();
}
