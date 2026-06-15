/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.support.RegionNameQualifier;
import org.hibernate.cache.spi.support.SimpleTimestamper;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import static org.hibernate.cache.spi.SecondLevelCacheLogger.L2CACHE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractRegionFactory implements RegionFactory {

	private final AtomicBoolean started = new AtomicBoolean( false );

	/**
	 * Legacy names that used to be the default for the query results region.
	 */
	public static final List<String> LEGACY_QUERY_RESULTS_REGION_UNQUALIFIED_NAMES = List.of(
			"org.hibernate.cache.spi.QueryResultsRegion",
			"org.hibernate.cache.internal.StandardQueryCache"
	);

	/**
	 * Legacy names that used to be the default for the update timestamps region.
	 */
	public static final List<String> LEGACY_UPDATE_TIMESTAMPS_REGION_UNQUALIFIED_NAMES = List.of(
			"org.hibernate.cache.spi.TimestampsRegion",
			"org.hibernate.cache.spi.UpdateTimestampsCache"
	);

	private Exception startingException;

	private SessionFactoryOptions options;


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean isStarted() {
		if ( started.get() ) {
			assert options != null;
			return true;
		}
		else {
			assert options == null;
			throw new IllegalStateException( "Cache provider not started", startingException );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void verifyStarted() {
		if ( ! verifiedStartStatus() ) {
			throw new IllegalStateException( "Cache provider not started", startingException );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean verifiedStartStatus() {
		if ( started.get() ) {
			assert options != null;
			return true;
		}
		else {
			assert options == null;
			return false;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SessionFactoryOptions getOptions() {
		verifyStarted();
		return options;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final void start(SessionFactoryOptions settings, Map<String,Object> configValues) throws CacheException {
		if ( started.compareAndSet( false, true ) ) {
			synchronized (this) {
				this.options = settings;
				try {
					prepareForUse( settings, configValues );
					startingException = null;
				}
				catch ( Exception e ) {
					options = null;
					started.set( false );
					startingException = e;
				}
			}
		}
		else {
			L2CACHE_LOGGER.attemptToStartAlreadyStartedCacheProvider();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract void prepareForUse(SessionFactoryOptions settings, Map<String,Object> configValues);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final void stop() {
		if ( started.compareAndSet( true, false ) ) {
			synchronized ( this ) {
				try {
					releaseFromUse();
				}
				finally {
					options = null;
					startingException = null;
				}
			}
		}
		else {
			L2CACHE_LOGGER.attemptToStopAlreadyStoppedCacheProvider();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract void releaseFromUse();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getDefaultAccessType() {
		return AccessType.READ_WRITE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String qualify(String regionName) {
		return RegionNameQualifier.INSTANCE.qualify( regionName, options );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheTransactionSynchronization createTransactionContext(SharedSessionContractImplementor session) {
		return new StandardCacheTransactionSynchronization( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long nextTimestamp() {
		return SimpleTimestamper.next();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getTimeout() {
		return SimpleTimestamper.timeOut();
	}
}
