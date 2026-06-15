/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import static org.hibernate.internal.util.StringHelper.split;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An attribute path is, generally speaking, the path of attribute names back
 * to a "root" (which is either an entity or a persistent collection).  The
 * name of this root typically is <strong>not</strong> included in the path.
 *
 * @author Steve Ebersole
 */
public class AttributePath extends AbstractAttributeKey {
	public static final char DELIMITER = '.';

	public AttributePath() {
		super();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected char getDelimiter() {
		return DELIMITER;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributePath append(String property) {
		return new AttributePath( this, property );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AttributePath getParent() {
		return (AttributePath) super.getParent();
	}

	public AttributePath(AttributePath parent, String property) {
		super( parent, property );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static AttributePath parse(String path) {
		if ( path != null ) {
			AttributePath attributePath = new AttributePath();
			for ( String part : split( ".", path ) ) {
				attributePath = attributePath.append( part );
			}
			return attributePath;
		}
		else {
			return null;
		}
	}
}
