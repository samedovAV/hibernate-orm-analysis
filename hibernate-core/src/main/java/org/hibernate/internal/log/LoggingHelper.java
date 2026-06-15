/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.log;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Helper for logging collection, entity and embeddable information.  Uses path collapsing
 * for readability
 *
 * @author Steve Ebersole
 */
public class LoggingHelper {
	public static final String NULL = "<null>";
	public static final String UNREFERENCED = "<unreferenced>";

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toLoggableString(NavigableRole role) {
		if ( role == null ) {
			return UNREFERENCED;
		}

		return role.getFullPath();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toLoggableString(NavigablePath path) {
		assert path != null;

		return path.getFullPath();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static String toLoggableString(NavigableRole role, Object key) {
		if ( role == null ) {
			return UNREFERENCED;
		}

		return toLoggableString( toLoggableString( role ), key );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static String toLoggableString(NavigablePath path, Object key) {
		assert path != null;
		return toLoggableString( toLoggableString( path ), key );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static String toLoggableString(CollectionKey collectionKey) {
		return toLoggableString( collectionKey.getRole(), collectionKey.getKey() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static String toLoggableString(EntityKey entityKey) {
		return toLoggableString( StringHelper.collapse( entityKey.getEntityName() ), entityKey.getIdentifierValue() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String toLoggableString(String roleOrPath, Object key) {
		assert roleOrPath != null;

		StringBuilder buffer = new StringBuilder();

		buffer.append( roleOrPath );
		buffer.append( '#' );

		if ( key == null ) {
			buffer.append( NULL );
		}
		else {
			buffer.append( key );
		}

		return buffer.toString();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static String toLoggableString(PersistentCollection<?> collectionInstance) {
		if ( collectionInstance == null ) {
			return NULL;
		}

		return toLoggableString(
				collectionInstance.getRole(),
				collectionInstance.getKey()
		);
	}
}
