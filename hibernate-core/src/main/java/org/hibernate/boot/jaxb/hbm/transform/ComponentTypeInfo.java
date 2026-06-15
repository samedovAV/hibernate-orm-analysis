/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import org.hibernate.mapping.Component;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ComponentTypeInfo extends ManagedTypeInfo {
	private final Component component;

	public ComponentTypeInfo(Component component) {
		super( component.getTable() );
		this.component = component;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Component getComponent() {
		return component;
	}
}
