/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractAttributeKey {
	private static final String COLLECTION_ELEMENT = "{element}";

	private final AbstractAttributeKey parent;
	private final String property;
	private final String fullPath;
	private final int depth;

	/**
	 * Constructor for the base AttributePath
	 */
	protected AbstractAttributeKey() {
		this( null, "" );
	}

	/**
	 * Constructor for the base AttributeRole
	 */
	protected AbstractAttributeKey(String base) {
		this( null, base );
	}

	protected AbstractAttributeKey(AbstractAttributeKey parent, String property) {
		this.parent = parent;
		this.property = property;

		final String prefix;
		if ( parent != null ) {
			final String resolvedParent = parent.getFullPath();
			if ( StringHelper.isEmpty( resolvedParent ) ) {
				prefix = "";
			}
			else {
				prefix = resolvedParent + getDelimiter();
			}
			depth = parent.getDepth() + 1;
		}
		else {
			prefix = "";
			depth = 0;
		}

		this.fullPath = prefix + property;
	}

	/**
	 * How many "parts" are there to this path/role?
	 *
	 * @return The number of parts.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDepth() {
		return depth;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract char getDelimiter();

	/**
	 * Creates a new AbstractAttributeKey by appending the passed part.
	 *
	 * @param property The part to append
	 *
	 * @return The new AbstractAttributeKey
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract AbstractAttributeKey append(String property);

	/**
	 * Access to the parent part
	 *
	 * @return the parent part
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AbstractAttributeKey getParent() {
		return parent;
	}

	/**
	 * Access to the end path part.
	 *
	 * @return the end path part
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getProperty() {
		return property;
	}

	/**
	 * Access to the full path as a String
	 *
	 * @return The full path as a String
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * Does this part represent a root.
	 *
	 * @return {@code true} if this part is a root.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * Does this part represent a collection-element reference?
	 *
	 * @return {@code true} if the current property is a collection element
	 *         marker {@value #COLLECTION_ELEMENT}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCollectionElement() {
		return COLLECTION_ELEMENT.equals( property );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getFullPath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		final AbstractAttributeKey that = (AbstractAttributeKey) o;
		return this.fullPath.equals( that.fullPath );

	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int hashCode() {
		return fullPath.hashCode();
	}
}
