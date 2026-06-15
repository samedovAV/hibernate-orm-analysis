/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import org.hibernate.boot.jaxb.mapping.spi.JaxbJoinColumnImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class TargetColumnAdapterJaxbJoinColumn implements TargetColumnAdapter {
	private final JaxbJoinColumnImpl jaxbColumn;

	public TargetColumnAdapterJaxbJoinColumn(ColumnDefaults columnDefaults) {
		this( new JaxbJoinColumnImpl(), columnDefaults );
	}

	public TargetColumnAdapterJaxbJoinColumn(JaxbJoinColumnImpl jaxbColumn, ColumnDefaults columnDefaults) {
		this.jaxbColumn = jaxbColumn;
		this.jaxbColumn.setNullable( columnDefaults.isNullable() );
		this.jaxbColumn.setUnique( columnDefaults.isUnique() );
		this.jaxbColumn.setInsertable( columnDefaults.isInsertable() );
		this.jaxbColumn.setUpdatable( columnDefaults.isUpdatable() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbJoinColumnImpl getTargetColumn() {
		return jaxbColumn;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setName(String value) {
		jaxbColumn.setName( value );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setTable(String value) {
		jaxbColumn.setTable( value );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setNullable(Boolean value) {
		if ( value != null ) {
			jaxbColumn.setNullable( value );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setUnique(Boolean value) {
		if ( value != null ) {
			jaxbColumn.setUnique( value );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setInsertable(Boolean value) {
		if ( value != null ) {
			jaxbColumn.setInsertable( value );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setUpdatable(Boolean value) {
		if ( value != null ) {
			jaxbColumn.setUpdatable( value );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLength(Integer value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setPrecision(Integer value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setScale(Integer value) {
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setColumnDefinition(String value) {
		jaxbColumn.setColumnDefinition( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDefault(String value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCheck(String value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setComment(String value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRead(String value) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setWrite(String value) {
	}
}
