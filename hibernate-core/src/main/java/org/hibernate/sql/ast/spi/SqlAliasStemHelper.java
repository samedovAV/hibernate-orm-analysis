/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.AssertionFailure;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Gavin King
 */
public class SqlAliasStemHelper {
	/**
	 * Singleton access
	 */
	public static final SqlAliasStemHelper INSTANCE = new SqlAliasStemHelper();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String generateStemFromEntityName(String entityName) {
		return acronym( toSimpleEntityName( entityName ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String toSimpleEntityName(String entityName) {
		String simpleName = StringHelper.unqualify( entityName );
		if ( simpleName.contains( "$" ) ) {
			// inner class
			simpleName = simpleName.substring( simpleName.lastIndexOf( '$' ) + 1 );
		}
		if ( StringHelper.isEmpty( simpleName ) ) {
			throw new AssertionFailure( "Could not determine simple name as base for alias [" + entityName + "]" );
		}
		return simpleName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String generateStemFromAttributeName(String attributeName) {
		return acronym(attributeName);
	}


	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private String acronym(String name) {
		StringBuilder string = new StringBuilder();
		char last = '\0';
		for (int i = 0; i<name.length(); i++ ) {
			char ch = name.charAt(i);
			if ( Character.isLetter(ch) ) {
				if ( string.length() == 0
						|| Character.isUpperCase(ch) && !Character.isUpperCase(last) ) {
					string.append( Character.toLowerCase(ch) );
				}
			}
			last = ch;
		}
		return string.length() == 0 ? "z" : string.toString();
	}
}
