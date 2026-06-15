/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.cte;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The consumer part of a CTE statement - the select or insert or delete or update that uses
 * the CTE
 *
 * @author Steve Ebersole
 * @author Christian Beikov
 */
public interface CteContainer {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, CteStatement> getCteStatements();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CteStatement getCteStatement(String cteLabel);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addCteStatement(CteStatement cteStatement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, CteObject> getCteObjects();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CteObject getCteObject(String cteObjectName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addCteObject(CteObject cteObject);

}
