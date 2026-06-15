/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import java.lang.reflect.Constructor;

import org.hibernate.InstantiationException;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Support for instantiating embeddables as POJO representation through a constructor
 */
public class EmbeddableInstantiatorPojoIndirecting
		extends AbstractPojoInstantiator
		implements EmbeddableInstantiator {
	protected final Constructor<?> constructor;
	protected final int[] index;

	protected EmbeddableInstantiatorPojoIndirecting(Constructor<?> constructor, int[] index) {
		super( constructor.getDeclaringClass() );
		this.constructor = constructor;
		this.index = index;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static EmbeddableInstantiatorPojoIndirecting of(
			String[] propertyNames,
			Constructor<?> constructor,
			String[] componentNames) {
		if ( componentNames == null ) {
			throw new IllegalArgumentException( "Can't determine field assignment for constructor: " + constructor );
		}
		final var index = new int[componentNames.length];
		return EmbeddableHelper.resolveIndex( propertyNames, componentNames, index )
				? new EmbeddableInstantiatorPojoIndirectingWithGap( constructor, index )
				: new EmbeddableInstantiatorPojoIndirecting( constructor, index );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object instantiate(ValueAccess valuesAccess) {
		try {
			final var originalValues = valuesAccess.getValues();
			final var values = new Object[originalValues.length];
			for ( int i = 0; i < values.length; i++ ) {
				values[i] = originalValues[index[i]];
			}
			return constructor.newInstance( values );
		}
		catch ( Exception e ) {
			throw new InstantiationException( "Could not instantiate entity", getMappedPojoClass(), e );
		}
	}

	// Handles gaps, by leaving the value null for that index
	private static class EmbeddableInstantiatorPojoIndirectingWithGap extends EmbeddableInstantiatorPojoIndirecting {

		public EmbeddableInstantiatorPojoIndirectingWithGap(Constructor<?> constructor, int[] index) {
			super( constructor, index );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Object instantiate(ValueAccess valuesAccess) {
			try {
				final var originalValues = valuesAccess.getValues();
				final var values = new Object[index.length];
				for ( int i = 0; i < values.length; i++ ) {
					final int index = this.index[i];
					if ( index >= 0 ) {
						values[i] = originalValues[index];
					}
				}
				return constructor.newInstance( values );
			}
			catch ( Exception e ) {
				throw new InstantiationException( "Could not instantiate entity", getMappedPojoClass(), e );
			}
		}
	}
}
