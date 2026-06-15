/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.Status;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.jpa.event.spi.CallbackType;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PostUpdateEventListenerStandardImpl implements PostUpdateEventListener {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void onPostUpdate(PostUpdateEvent event) {
		handlePostUpdate( event.getEntity(), event.getPersister(), event.getSession() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void handlePostUpdate(Object entity, EntityPersister persister, SharedSessionContractImplementor source) {
		// mimic the preUpdate filter
		if ( source.isStateless()
				|| source.getPersistenceContextInternal().getEntry( entity ).getStatus() != Status.DELETED ) {
			source.runEntityLifecycleCallback( () -> persister.getEntityCallbacks().postUpdate( entity ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean requiresPostCommitHandling(EntityPersister persister) {
		return persister.getEntityCallbacks().hasRegisteredCallbacks( CallbackType.POST_UPDATE );
	}
}
