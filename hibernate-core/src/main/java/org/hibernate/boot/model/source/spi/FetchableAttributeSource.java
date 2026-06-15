/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes source for attributes which can be fetched.
 *
 * @author Steve Ebersole
 */
public interface FetchableAttributeSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchCharacteristics getFetchCharacteristics();
}
