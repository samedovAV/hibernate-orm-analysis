/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.CacheRegionDefinition;
import org.hibernate.cache.spi.access.AccessType;

import static org.hibernate.internal.util.StringHelper.isEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the caching options for an entity, natural id, or collection.
 *
 * @author Steve Ebersole
 * @author Hardy Ferentschik
 */
public class Caching {
	private Boolean requested;
	private String region;
	private AccessType accessType;
	private boolean cacheLazyProperties;

	public Caching() {}

	public Caching(String region, AccessType accessType, boolean cacheLazyProperties) {
		this.region = region;
		this.accessType = accessType;
		this.cacheLazyProperties = cacheLazyProperties;
	}

	public Caching(String region, AccessType accessType, boolean cacheLazyProperties, boolean requested) {
		this.requested = requested;
		this.region = region;
		this.accessType = accessType;
		this.cacheLazyProperties = cacheLazyProperties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getRegion() {
		return region;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRegion(String region) {
		this.region = region;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getAccessType() {
		return accessType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCacheLazyProperties() {
		return cacheLazyProperties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCacheLazyProperties(boolean cacheLazyProperties) {
		this.cacheLazyProperties = cacheLazyProperties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isRequested() {
		return requested == Boolean.TRUE;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isRequested(boolean defaultValue) {
		return requested == null ? defaultValue : isRequested();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRequested(boolean requested) {
		this.requested = requested;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void overlay(CacheRegionDefinition overrides) {
		if ( overrides != null ) {
			requested = true;
			accessType = AccessType.fromExternalName( overrides.usage() );
			if ( !isEmpty( overrides.region() ) ) {
				region = overrides.region();
			}
			cacheLazyProperties = overrides.cacheLazy();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void overlay(Caching overrides) {
		if ( overrides != null ) {
			this.requested = overrides.requested;
			this.accessType = overrides.accessType;
			this.region = overrides.region;
			this.cacheLazyProperties = overrides.cacheLazyProperties;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "Caching{region='" + region + '\''
				+ ", accessType=" + accessType
				+ ", cacheLazyProperties=" + cacheLazyProperties
				+ ", requested=" + requested + '}';
	}
}
