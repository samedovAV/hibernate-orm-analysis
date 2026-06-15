/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql;

import org.hibernate.Internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@Internal
public interface RestrictionRenderingContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String makeParameterMarker();
}
