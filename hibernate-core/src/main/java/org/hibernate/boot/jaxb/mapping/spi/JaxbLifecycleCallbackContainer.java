/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB binding interface for commonality between things which
 * allow callback declarations.  This includes <ul>
 *     <li>
 *         entities and mapped-superclasses
 *     </li>
 *     <li>
 *         entity-listener classes
 *     </li>
 * </ul>
 *
 * @author Steve Ebersole
 */
public interface JaxbLifecycleCallbackContainer {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreMerge();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreMerge(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPrePersist();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPrePersist(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostPersist();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostPersist(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreRemove();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreRemove(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostRemove();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostRemove(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreUpdate(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostUpdate(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreUpsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreUpsert(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostUpsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostUpsert(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreInsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreInsert(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostInsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostInsert(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPreDelete();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPreDelete(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostDelete();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostDelete(@Nullable JaxbLifecycleCallbackImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbLifecycleCallbackImpl getPostLoad();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPostLoad(@Nullable JaxbLifecycleCallbackImpl value);
}
