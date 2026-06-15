/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.bytecode.enhance.internal.tracker.DirtyTracker;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A self dirtiness tracker that declares additional methods that are intended for
 * internal communication. This interface can be implemented optionally instead of
 * the plain {@link SelfDirtinessTracker}.
 */
public interface ExtendedSelfDirtinessTracker extends SelfDirtinessTracker {

	String REMOVE_DIRTY_FIELDS_NAME = "$$_hibernate_removeDirtyFields";

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_getCollectionFieldDirtyNames(DirtyTracker dirtyTracker);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean $$_hibernate_areCollectionFieldsDirty();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_clearDirtyCollectionNames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_removeDirtyFields(LazyAttributeLoadingInterceptor lazyInterceptor);
}
