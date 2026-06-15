/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.loader.ast.spi.AfterLoadAction;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.sql.exec.spi.Callback;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class CallbackImpl implements Callback {

	private final List<AfterLoadAction> afterLoadActions;

	public CallbackImpl() {
		afterLoadActions = new ArrayList<>( 1 );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerAfterLoadAction(AfterLoadAction afterLoadAction) {
		afterLoadActions.add( afterLoadAction );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void invokeAfterLoadActions(Object entity, EntityMappingType entityMappingType, SharedSessionContractImplementor session) {
		for ( int i = 0; i < afterLoadActions.size(); i++ ) {
			afterLoadActions.get( i ).afterLoad( entity, entityMappingType, session );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAfterLoadActions() {
		return !afterLoadActions.isEmpty();
	}
}
