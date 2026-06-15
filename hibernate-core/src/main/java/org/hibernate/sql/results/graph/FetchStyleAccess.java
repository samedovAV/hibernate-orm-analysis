/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.engine.FetchStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to a FetchStyle
 *
 * @author Steve Ebersole
 */
public interface FetchStyleAccess {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchStyle getStyle();
}
