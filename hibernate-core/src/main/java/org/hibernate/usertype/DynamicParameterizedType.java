/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.usertype;

import org.hibernate.Incubating;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Properties;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Types which implement this interface will have
 * {@link ParameterizedType#setParameterValues(Properties)} called with an
 * instance of the class {@link DynamicParameterizedType.ParameterType}
 * instead of the key {@value PARAMETER_TYPE}.
 *
 * @author Janario Oliveira
 * @author Yanming Zhou
 *
 * @deprecated This very old approach was never properly implemented in all
 * contexts, and never actually achieved the type safety it aimed for. Just
 * use {@link ParameterizedType} for now.
 */
@Deprecated(since = "7.0", forRemoval = true)
public interface DynamicParameterizedType extends ParameterizedType {
	String PARAMETER_TYPE = "org.hibernate.type.ParameterType";

	String IS_DYNAMIC = "org.hibernate.type.ParameterType.dynamic";

	String RETURNED_CLASS = "org.hibernate.type.ParameterType.returnedClass";
	String IS_PRIMARY_KEY = "org.hibernate.type.ParameterType.primaryKey";
	String ENTITY = "org.hibernate.type.ParameterType.entityClass";
	String PROPERTY = "org.hibernate.type.ParameterType.propertyName";
	String ACCESS_TYPE = "org.hibernate.type.ParameterType.accessType";
	String XPROPERTY = "org.hibernate.type.ParameterType.xproperty";

	interface ParameterType {

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Class<?> getReturnedClass();

		@Incubating
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default Type getReturnedJavaType() {
			return getReturnedClass();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Annotation[] getAnnotationsMethod();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getCatalog();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getSchema();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getTable();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean isPrimaryKey();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String[] getColumns();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Long[] getColumnLengths();
	}
}
