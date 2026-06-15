/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public class EventUtil {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String getLoggableName(String entityName, Object entity) {
		return entityName == null ? entity.getClass().getName() : entityName;
	}
}
