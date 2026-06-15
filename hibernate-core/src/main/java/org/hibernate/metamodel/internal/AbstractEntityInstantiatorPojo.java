/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;


import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor.EntityRelatedState;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.spi.EntityInstantiator;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.descriptor.java.JavaType;

import static org.hibernate.engine.internal.ManagedTypeHelper.asPersistentAttributeInterceptable;
import static org.hibernate.engine.internal.ManagedTypeHelper.isPersistentAttributeInterceptableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for instantiating entity values as POJO representation
 *
 * @author Steve Ebersole
 */
public abstract class AbstractEntityInstantiatorPojo extends AbstractPojoInstantiator implements EntityInstantiator {

	private final Class<?> proxyInterface;
	private final boolean applyBytecodeInterception;
	private final EntityRelatedState loadingInterceptorState;

	public AbstractEntityInstantiatorPojo(
			EntityPersister persister,
			PersistentClass persistentClass,
			JavaType<?> javaType) {
		super( javaType.getJavaTypeClass() );
		proxyInterface = persistentClass.getProxyInterface();
		//TODO this PojoEntityInstantiator appears to not be reused ?!
		applyBytecodeInterception =
				isPersistentAttributeInterceptableType( persistentClass.getMappedClass() );
		loadingInterceptorState =
				applyBytecodeInterception
						? new EntityRelatedState(
								persister.getEntityName(),
								persister.getBytecodeEnhancementMetadata()
										.getLazyAttributesMetadata()
										.getLazyAttributeNames()
						)
						: null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Object applyInterception(Object entity) {
		if ( applyBytecodeInterception ) {
			asPersistentAttributeInterceptable( entity )
					.$$_hibernate_setInterceptor( new LazyAttributeLoadingInterceptor(
							loadingInterceptorState,
							null,
							null
					) );
		}
		return entity;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInstance(Object object) {
		return super.isInstance( object )
			// this one needed only for guessEntityMode()
			|| proxyInterface!=null && proxyInterface.isInstance(object);
	}

	/*
	 * Used by Hibernate Reactive
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean isApplyBytecodeInterception() {
		return applyBytecodeInterception;
	}

	/*
	 * Used by Hibernate Reactive
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected LazyAttributeLoadingInterceptor.EntityRelatedState getLoadingInterceptorState() {
		return loadingInterceptorState;
	}
}
