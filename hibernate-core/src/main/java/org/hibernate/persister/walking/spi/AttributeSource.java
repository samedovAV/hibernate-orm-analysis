/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.walking.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
* @author Steve Ebersole
*/
public interface AttributeSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getPropertyIndex(String propertyName) {
		throw new UnsupportedOperationException();
	}
}
