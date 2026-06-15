/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.Map;

import org.hibernate.MappingException;
import org.hibernate.boot.spi.SecondPass;
import org.hibernate.mapping.PersistentClass;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Sharath Reddy
 */
public class SetBasicValueTypeSecondPass implements SecondPass {
	private final BasicValueBinder binder;

	public SetBasicValueTypeSecondPass(BasicValueBinder val) {
		binder = val;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void doSecondPass(Map<String, PersistentClass> persistentClasses) throws MappingException {
		binder.fillSimpleValue();
	}
}
