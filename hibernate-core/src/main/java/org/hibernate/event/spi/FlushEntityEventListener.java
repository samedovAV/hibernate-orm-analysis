/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public interface FlushEntityEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onFlushEntity(FlushEntityEvent event) throws HibernateException;
}
