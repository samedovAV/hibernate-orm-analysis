/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public class QueryParameterJavaObjectType extends JavaObjectType {

	public static final QueryParameterJavaObjectType INSTANCE = new QueryParameterJavaObjectType();

	public QueryParameterJavaObjectType() {
		super();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return "QUERY_PARAMETER_JAVA_OBJECT";
	}
}
