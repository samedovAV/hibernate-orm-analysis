/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.jpa.internal;

import jakarta.persistence.EntityListenerRegistration;
import org.hibernate.event.jpa.spi.EntityCallbacks;
import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.jpa.event.spi.CallbackType;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Implementation of EntityCallbacks
///
/// @author Steve Ebersole
public class EntityCallbacksImpl<E> implements EntityCallbacks<E>, Serializable {
	private final Map<CallbackType, List<Callback<? super E>>> callbacks;

	private EntityCallbacksImpl(Map<CallbackType, List<Callback<? super E>>> callbacks) {
		this.callbacks = callbacks;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private List<Callback<? super E>> getCallbacks(CallbackType callbackType) {
		return callbacks.get( callbackType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasRegisteredCallbacks(CallbackType callbackType) {
		return isNotEmpty( getCallbacks( callbackType ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preCreate(S entity) {
		return callback( CallbackType.PRE_PERSIST, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postCreate(S entity) {
		return callback( CallbackType.POST_PERSIST, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preMerge(S entity) {
		return callback( CallbackType.PRE_MERGE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preInsert(S entity) {
		return callback( CallbackType.PRE_INSERT, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postInsert(S entity) {
		return callback( CallbackType.POST_INSERT, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preUpdate(S entity) {
		return callback( CallbackType.PRE_UPDATE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postUpdate(S entity) {
		return callback( CallbackType.POST_UPDATE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preUpsert(S entity) {
		return callback( CallbackType.PRE_UPSERT, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postUpsert(S entity) {
		return callback( CallbackType.POST_UPSERT, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preRemove(S entity) {
		return callback( CallbackType.PRE_REMOVE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postRemove(S entity) {
		return callback( CallbackType.POST_REMOVE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean preDelete(S entity) {
		return callback( CallbackType.PRE_DELETE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postDelete(S entity) {
		return callback( CallbackType.POST_DELETE, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> boolean postLoad(S entity) {
		return callback( CallbackType.POST_LOAD, entity );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean callback(CallbackType callbackType, E entity) {
		return callback( getCallbacks( callbackType ), entity );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean callback(List<Callback<? super E>> callbacks, E entity) {
		if ( isNotEmpty( callbacks ) ) {
			for ( var callback : callbacks ) {
				callback.performCallback( entity );
			}
			return true;
		}
		else {
			return false;
		}
	}

	/// @see jakarta.persistence.EntityManagerFactory#addListener
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityListenerRegistration addListener(CallbackType type, Consumer<? super E> listener) {
		final List<Callback<? super E>> callbacks = getCallbacks( type );
		var callback = new AddedCallbackImpl<>( type, listener );
		callbacks.add( callback );
		return new EntityListenerRegistrationImpl<>( this, type, callback );
	}

	/// @see jakarta.persistence.EntityManagerFactory#addListener
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityListenerRegistration addListener(CallbackType type, Callback<? super E> callback) {
		final List<Callback<? super E>> callbacks = getCallbacks( type );
		callbacks.add( callback );
		return new EntityListenerRegistrationImpl<>( this, type, callback );
	}

	/// package-protected access to remove a callback used to support
	/// JPA's [jakarta.persistence.EntityListenerRegistration].
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	void remove(CallbackType type, Callback<? super E> callback) {
		final List<Callback<? super E>> callbacks = getCallbacks( type );
		callbacks.remove( callback );
	}

	public static class Builder<E> {
		private final EnumMap<CallbackType, List<Callback<? super E>>> callbacks = new EnumMap<>( CallbackType.class );

		public Builder() {
			for ( var type : CallbackType.values() ) {
				callbacks.put( type, new CopyOnWriteArrayList<>() );
			}
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void registerCallback(Callback<? super E> callback) {
			getCallbacks( callback.getCallbackType() ).add( callback );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private List<Callback<? super E>> getCallbacks(CallbackType callbackType) {
			return callbacks.get( callbackType );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public EntityCallbacksImpl<E> build() {
			return new EntityCallbacksImpl<>( new EnumMap<>( callbacks ) );
		}
	}
}
