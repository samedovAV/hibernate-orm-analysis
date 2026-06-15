/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi.interceptor;

import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SessionAssociableInterceptor extends PersistentAttributeInterceptor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getLinkedSession();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSession(SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unsetSession();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean allowLoadOutsideTransaction();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSessionFactoryUuid();
}
