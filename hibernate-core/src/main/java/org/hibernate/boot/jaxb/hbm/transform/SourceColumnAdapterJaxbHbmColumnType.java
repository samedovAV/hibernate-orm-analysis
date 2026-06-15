/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmColumnType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SourceColumnAdapterJaxbHbmColumnType implements SourceColumnAdapter {
	private final JaxbHbmColumnType hbmColumn;

	public SourceColumnAdapterJaxbHbmColumnType(JaxbHbmColumnType hbmColumn) {
		this.hbmColumn = hbmColumn;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return hbmColumn.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isNotNull() {
		return hbmColumn.isNotNull();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isUnique() {
		return hbmColumn.isUnique();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getLength() {
		return hbmColumn.getLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getPrecision() {
		return hbmColumn.getPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getScale() {
		return hbmColumn.getScale();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSqlType() {
		return hbmColumn.getSqlType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getComment() {
		return hbmColumn.getComment();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCheck() {
		return hbmColumn.getCheck();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getDefault() {
		return hbmColumn.getDefault();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getIndex() {
		return hbmColumn.getIndex();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getUniqueKey() {
		return hbmColumn.getUniqueKey();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getRead() {
		return hbmColumn.getRead();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getWrite() {
		return hbmColumn.getWrite();
	}
}
