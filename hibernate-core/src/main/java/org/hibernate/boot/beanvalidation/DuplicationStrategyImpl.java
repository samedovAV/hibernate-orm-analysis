/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.beanvalidation;

import org.hibernate.event.service.spi.DuplicationStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DuplicationStrategyImpl implements DuplicationStrategy {
	public static final DuplicationStrategyImpl INSTANCE = new DuplicationStrategyImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areMatch(Object listener, Object original) {
		return listener.getClass().equals( original.getClass() )
			&& BeanValidationEventListener.class.equals( listener.getClass() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Action getAction() {
		return Action.KEEP_ORIGINAL;
	}
}
