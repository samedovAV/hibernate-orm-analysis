/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import jakarta.persistence.Timeout;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Serves the role of [jakarta.persistence.Reference] which is
/// unfortunately sealed, and so we cannot directly extend.  But this
/// allows us to define an extension commonality.
///
/// @author Steve Ebersole
public interface JpaReference {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getTimeout();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, Object> getHints();
}
