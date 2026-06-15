/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.Map;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
public class XmlAttributes implements SqlAstNode {

	private final Map<String, Expression> attributes;

	public XmlAttributes(Map<String, Expression> attributes) {
		this.attributes = attributes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, Expression> getAttributes() {
		return attributes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException("XmlAttributes doesn't support walking");
	}

}
