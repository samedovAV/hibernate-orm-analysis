/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface Optionable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String options();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void options(String value);
}
