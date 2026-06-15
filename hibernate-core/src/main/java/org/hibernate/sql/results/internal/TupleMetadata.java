/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import jakarta.persistence.TupleElement;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Metadata about the tuple structure.
 *
 * @author Christian Beikov
 * @author Gavin King
 */
public final class TupleMetadata {
	private final TupleElement<?>[] elements;
	private final String[] aliases;
	private List<String> aliasList;
	private Map<String, Integer> nameIndex;
	private Map<TupleElement<?>, Integer> elementIndex;
	private List<TupleElement<?>> list;

	public TupleMetadata(TupleElement<?>[] elements, String[] aliases) {
		this.elements = elements;
		this.aliases = aliases;
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Integer get(TupleElement<?> element) {
		if ( elementIndex == null ) {
			final Map<TupleElement<?>, Integer> map = new IdentityHashMap<>( elements.length );
			for (int i = 0; i < elements.length; i++ ) {
				map.put( elements[i], i );
			}
			elementIndex = unmodifiableMap( map );
		}
		return elementIndex.get( element );
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Integer get(String name) {
		if ( nameIndex == null ) {
			final Map<String, Integer> map = new HashMap<>( aliases.length );
			for ( int i = 0; i < aliases.length; i++ ) {
				map.put( aliases[i], i );
			}
			nameIndex = unmodifiableMap( map );
		}
		return nameIndex.get( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TupleElement<?>> getList() {
		if ( list == null ) {
			list = List.of( elements );
		}
		return list;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getAliases() {
		if ( aliasList != null ) {
			aliasList = List.of( aliases );
		}
		return aliasList;
	}
}
