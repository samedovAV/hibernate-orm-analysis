/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.hibernate.HibernateError;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeConstructor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The following class provides some convenience methods for accessing JdbcType instance,
 * that are loaded into the app class loader, where they have access to the JDBC driver classes.
 *
 * @author Christian Beikov
 */
public final class PgJdbcHelper {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isUsable(ServiceRegistry serviceRegistry) {
		final ClassLoaderService classLoaderService = serviceRegistry.requireService( ClassLoaderService.class );
		try {
			classLoaderService.classForName( "org.postgresql.util.PGobject" );
			return true;
		}
		catch (ClassLoadingException ex) {
			return false;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getStructJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcType( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLStructPGObjectJdbcType" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getIntervalJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcType( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLIntervalSecondJdbcType" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getInetJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcType( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLInetJdbcType" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getJsonJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcType( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLJsonPGObjectJsonType" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getJsonbJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcType( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLJsonPGObjectJsonbType" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcTypeConstructor getJsonArrayJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcTypeConstructor( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLJsonArrayPGObjectJsonJdbcTypeConstructor" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcTypeConstructor getJsonbArrayJdbcType(ServiceRegistry serviceRegistry) {
		return createJdbcTypeConstructor( serviceRegistry, "org.hibernate.dialect.type.PostgreSQLJsonArrayPGObjectJsonbJdbcTypeConstructor" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType createJdbcType(ServiceRegistry serviceRegistry, String className) {
		final ClassLoaderService classLoaderService = serviceRegistry.requireService( ClassLoaderService.class );
		try {
			final Class<?> clazz = classLoaderService.classForName( className );
			final Constructor<?> constructor = clazz.getConstructor();
			return (JdbcType) constructor.newInstance();
		}
		catch (NoSuchMethodException e) {
			throw new HibernateError( "Class does not have an empty constructor", e );
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new HibernateError( "Could not construct JdbcType", e );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcTypeConstructor createJdbcTypeConstructor(ServiceRegistry serviceRegistry, String className) {
		final ClassLoaderService classLoaderService = serviceRegistry.requireService( ClassLoaderService.class );
		try {
			final Class<?> clazz = classLoaderService.classForName( className );
			final Constructor<?> constructor = clazz.getConstructor();
			return (JdbcTypeConstructor) constructor.newInstance();
		}
		catch (NoSuchMethodException e) {
			throw new HibernateError( "Class does not have an empty constructor", e );
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new HibernateError( "Could not construct JdbcTypeConstructor", e );
		}
	}
}
