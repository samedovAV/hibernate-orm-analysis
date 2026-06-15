/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.mapping.MetaAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ToolingHint {
	private final String name;
	private final boolean inheritable;
	private final MetaAttribute metaAttribute;

	public ToolingHint(String name, boolean inheritable) {
		this.name = name;
		this.inheritable = inheritable;

		this.metaAttribute = new MetaAttribute( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInheritable() {
		return inheritable;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public java.util.List getValues() {
		return metaAttribute.getValues();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addValue(String value) {
		metaAttribute.addValue( value );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getValue() {
		return metaAttribute.getValue();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMultiValued() {
		return metaAttribute.isMultiValued();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "ToolingHint{" +
				"name='" + name + '\'' +
				", inheritable=" + inheritable +
				", values=" + metaAttribute.getValues() +
				'}';
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MetaAttribute asMetaAttribute() {
		return metaAttribute;
	}
}
