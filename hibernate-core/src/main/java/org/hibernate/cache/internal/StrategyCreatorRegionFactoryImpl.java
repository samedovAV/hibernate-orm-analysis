/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import org.hibernate.boot.registry.selector.spi.StrategyCreator;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.service.spi.ServiceException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class StrategyCreatorRegionFactoryImpl implements StrategyCreator<RegionFactory> {

	private final Properties properties;

	public StrategyCreatorRegionFactoryImpl(Properties properties) {
		this.properties = properties;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RegionFactory create(Class<? extends RegionFactory> strategyClass) {
		assert RegionFactory.class.isAssignableFrom( strategyClass );

		// first look for a constructor accepting Properties
		final var regionFactoryWithProperties = instantiateWithProperties( strategyClass, Properties.class );
		if ( regionFactoryWithProperties != null ) {
			return regionFactoryWithProperties;
		}
		// next try Map
		final var regionFactoryWithMap = instantiateWithProperties( strategyClass, Map.class );
		if ( regionFactoryWithMap != null ) {
			return regionFactoryWithMap;
		}
		// finally try no-arg
		try {
			return strategyClass.newInstance();
		}
		catch (IllegalAccessException | InstantiationException e) {
			throw new ServiceException( "Unable to call constructor of RegionFactory impl [" + strategyClass.getName() + "]", e );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private RegionFactory instantiateWithProperties(Class<? extends RegionFactory> strategyClass, Class<?> propertiesClass) {
		try {
			return strategyClass.getConstructor( propertiesClass ).newInstance( properties );
		}
		catch ( NoSuchMethodException e ) {
			return null;
		}
		catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new ServiceException( "Unable to call constructor of RegionFactory impl [" + strategyClass.getName() + "]", e );
		}
	}
}
