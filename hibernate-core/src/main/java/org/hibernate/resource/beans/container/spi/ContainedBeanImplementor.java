/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.container.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Releasable extension to {@link ContainedBean}. We make this split to clarify
 * that clients of {@link BeanContainer} are not usually responsible for calling
 * {@link #initialize()} and {@link #release()}.
 *
 * @author Steve Ebersole
 */
public interface ContainedBeanImplementor<B> extends ContainedBean<B> {
	/**
	 * Allow the container to force initialize the lifecycle-generated bean
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initialize();

	/**
	 * Release any resources
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();
}
