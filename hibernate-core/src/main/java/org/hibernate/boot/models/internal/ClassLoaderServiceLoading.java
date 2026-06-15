/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.internal;

import java.net.URL;
import java.util.Collection;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import org.hibernate.models.spi.ClassLoading;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Adapts {@linkplain ClassLoaderService} to the {@linkplain ClassLoading} contract
 *
 * @author Steve Ebersole
 */
public class ClassLoaderServiceLoading implements ClassLoading {
	private final ClassLoaderService classLoaderService;

	public ClassLoaderServiceLoading(ClassLoaderService classLoaderService) {
		this.classLoaderService = classLoaderService;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <T> Class<T> classForName(String name) {
		return classLoaderService.classForName( name );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> Class<T> findClassForName(String name) {
		try {
			return classLoaderService.classForName( name );
		}
		catch (ClassLoadingException e) {
			return null;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public URL locateResource(String resourceName) {
		return classLoaderService.locateResource( resourceName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <S> Collection<S> loadJavaServices(Class<S> serviceType) {
		return classLoaderService.loadJavaServices( serviceType );
	}
}
