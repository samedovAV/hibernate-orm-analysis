/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common information between {@linkplain PersistentClass} and {@linkplain Component}
 * used while transforming {@code hbm.xml}
 *
 * @author Steve Ebersole
 */
public class ManagedTypeInfo {
	private final Table table;
	private final Map<String, PropertyInfo> propertyInfoMap = new HashMap<>();

	/**
	 *
	 */
	public ManagedTypeInfo(Table table) {
		this.table = table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table table() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, PropertyInfo> propertyInfoMap() {
		return propertyInfoMap;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean equals(Object obj) {
		if ( obj == this ) {
			return true;
		}
		if ( obj == null || obj.getClass() != this.getClass() ) {
			return false;
		}
		var that = (ManagedTypeInfo) obj;
		return Objects.equals( this.table, that.table ) &&
				Objects.equals( this.propertyInfoMap, that.propertyInfoMap );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode() {
		return Objects.hash( table, propertyInfoMap );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "ManagedTypeInfo[" +
				"table=" + table + ", " +
				"propertyInfoMap=" + propertyInfoMap + ']';
	}

}
