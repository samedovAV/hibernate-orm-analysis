/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.SizeSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of SizeSource
 *
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class SizeSourceImpl implements SizeSource {
	private final Integer length;
	private final Integer scale;
	private final Integer precision;

	public SizeSourceImpl(Integer length, Integer scale, Integer precision) {
		this.length = length;
		this.scale = scale;
		this.precision = precision;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getLength() {
		return length;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getPrecision() {
		return precision;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getScale() {
		return scale;
	}
}
