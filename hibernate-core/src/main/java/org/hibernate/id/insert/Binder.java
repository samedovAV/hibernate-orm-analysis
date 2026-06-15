/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.insert;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface Binder {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void bindValues(PreparedStatement ps) throws SQLException;
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEntity();
}
