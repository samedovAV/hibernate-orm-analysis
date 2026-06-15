/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Non-id, non-version singular attribute
 *
 * @author Steve Ebersole
 */
public interface JaxbLockableAttribute extends JaxbPersistentAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isOptimisticLock();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOptimisticLock(Boolean value);
}
