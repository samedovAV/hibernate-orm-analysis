/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.internal;

import org.hibernate.engine.spi.EntityEntryExtraState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contains optional state from {@link org.hibernate.engine.spi.EntityEntry}.
 *
 * @author Emmanuel Bernard
 */
class EntityEntryExtraStateHolder implements EntityEntryExtraState {
	private EntityEntryExtraState next;
	private Object[] deletedState;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object[] getDeletedState() {
		return deletedState;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDeletedState(Object[] deletedState) {
		this.deletedState = deletedState;
	}

	//the following methods are handling extraState contracts.
	//they are not shared by a common superclass to avoid alignment padding
	//we are trading off duplication for padding efficiency
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addExtraState(EntityEntryExtraState extraState) {
		if ( next == null ) {
			next = extraState;
		}
		else {
			next.addExtraState( extraState );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <T extends EntityEntryExtraState> T getExtraState(Class<T> extraStateType) {
		if ( next == null ) {
			return null;
		}
		if ( extraStateType.isInstance( next ) ) {
			return extraStateType.cast( next );
		}
		else {
			return next.getExtraState( extraStateType );
		}
	}
}
