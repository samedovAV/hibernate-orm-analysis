/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;


import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common interface for things that can handle meta attributes.
 *
 * @since 3.0.1
 */
public interface MetaAttributable {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, MetaAttribute> getMetaAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMetaAttributes(Map<String, MetaAttribute> metas);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetaAttribute getMetaAttribute(String name);

}
