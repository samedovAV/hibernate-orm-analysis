/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import org.hibernate.engine.FetchStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Any mapping with an outer-join attribute
 *
 * @author Gavin King
 */
public interface Fetchable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchStyle getFetchStyle();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetchStyle(FetchStyle fetchStyle);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isLazy();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLazy(boolean lazy);
}
