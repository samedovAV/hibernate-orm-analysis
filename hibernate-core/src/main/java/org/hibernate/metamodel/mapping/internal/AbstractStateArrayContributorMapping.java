/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.metamodel.mapping.AttributeMetadata;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.sql.results.graph.FetchOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractStateArrayContributorMapping
		extends AbstractAttributeMapping
		implements FetchOptions {

	private final FetchTiming fetchTiming;
	private final FetchStyle fetchStyle;

	public AbstractStateArrayContributorMapping(
			String name,
			AttributeMetadata attributeMetadata,
			FetchTiming fetchTiming,
			FetchStyle fetchStyle,
			int stateArrayPosition,
			int fetchableIndex,
			ManagedMappingType declaringType,
			PropertyAccess propertyAccess) {
		super( name, fetchableIndex, declaringType, attributeMetadata, stateArrayPosition, propertyAccess );
		this.fetchTiming = fetchTiming;
		this.fetchStyle = fetchStyle;
	}

	public AbstractStateArrayContributorMapping(
			String name,
			AttributeMetadata attributeMetadata,
			FetchOptions mappedFetchOptions,
			int stateArrayPosition,
			int fetchableIndex,
			ManagedMappingType declaringType,
			PropertyAccess propertyAccess) {
		this(
				name,
				attributeMetadata,
				mappedFetchOptions.getTiming(),
				mappedFetchOptions.getStyle(),
				stateArrayPosition,
				fetchableIndex,
				declaringType,
				propertyAccess
		);
	}

	/**
	 * For Hibernate Reactive
	 */
	protected AbstractStateArrayContributorMapping(AbstractStateArrayContributorMapping original) {
		super( original );
		this.fetchTiming = original.fetchTiming;
		this.fetchStyle = original.fetchStyle;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFetchableName() {
		return getAttributeName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getMappedFetchOptions() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchStyle getStyle() {
		return fetchStyle;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return fetchTiming;
	}
}
