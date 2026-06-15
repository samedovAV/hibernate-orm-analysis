/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import java.util.Collection;

import org.hibernate.cache.spi.TimestampsCache;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * TimestampsRegionAccess implementation for cases where query results caching
 * (or second level caching overall) is disabled.
 *
 * @author Steve Ebersole
 */
public class TimestampsCacheDisabledImpl implements TimestampsCache {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimestampsRegion getRegion() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void preInvalidate(String[] spaces, SharedSessionContractImplementor session) {
		//noop
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void invalidate(String[] spaces, SharedSessionContractImplementor session) {
		//noop
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpToDate(
			String[] spaces,
			Long timestamp,
			SharedSessionContractImplementor session) {
		//noop
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpToDate(
			Collection<String> spaces,
			Long timestamp,
			SharedSessionContractImplementor session) {
		//noop
		return false;
	}
}
