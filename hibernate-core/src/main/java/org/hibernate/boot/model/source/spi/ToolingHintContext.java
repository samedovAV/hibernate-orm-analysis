/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.mapping.MetaAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a collection of "tooling hints" ({@code <meta/>} mapping info) keyed by a name.
 *
 * @apiNote Historically, these were called "meta attributes", but as these are values used solely
 *          by external tooling it was decided to begin calling them tooling hints. For temporary
 *         backwards compatibility (temporary until we move away from o.h.mapping model) you will
 *         see mixed usage.
 *
 * @author Steve Ebersole
 */
public class ToolingHintContext {
	private final ConcurrentMap<String, ToolingHint> toolingHintMap = new ConcurrentHashMap<>();

	public ToolingHintContext(ToolingHintContext baseline) {
		if ( baseline == null ) {
			return;
		}

		for ( ToolingHint toolingHint : baseline.toolingHintMap.values() ) {
			if ( toolingHint.isInheritable() ) {
				toolingHintMap.put( toolingHint.getName(), toolingHint );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Collection<ToolingHint> getToolingHints() {
		return toolingHintMap.values();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<String> getKeys() {
		return toolingHintMap.keySet();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHint getToolingHint(String key) {
		return toolingHintMap.get( key );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void add(ToolingHint toolingHint) {
		toolingHintMap.put( toolingHint.getName(), toolingHint );
	}

	/**
	 * The {@link org.hibernate.mapping} package accepts these as a Map, so for now
	 * expose the underlying Map.  But we unfortunately need to collect a Map...
	 *
	 * @return The underlying Map
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String,MetaAttribute> getMetaAttributeMap() {
		final Map<String,MetaAttribute> collectedAttributeMap = new ConcurrentHashMap<>();
		for ( ToolingHint toolingHint : toolingHintMap.values() ) {
			collectedAttributeMap.put( toolingHint.getName(), toolingHint.asMetaAttribute() );
		}
		return collectedAttributeMap;
	}
}
