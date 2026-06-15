/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import java.lang.reflect.Member;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for how we resolve the {@link Member} for a give attribute context.
 */
public interface MemberResolver {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Member resolveMember(AttributeContext attributeContext, MetadataContext metadataContext);
}
