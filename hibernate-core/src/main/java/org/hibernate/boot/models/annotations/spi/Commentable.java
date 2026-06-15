/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which contain SQL comments
 *
 * @author Steve Ebersole
 */
public interface Commentable extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String comment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void comment(String value);
}
