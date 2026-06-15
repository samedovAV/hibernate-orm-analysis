/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;

import org.hibernate.boot.model.source.spi.AttributePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Dmytro Bondar
 */
public interface ImplicitIndexColumnNameSource extends ImplicitNameSource {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributePath getPluralAttributePath();

}
