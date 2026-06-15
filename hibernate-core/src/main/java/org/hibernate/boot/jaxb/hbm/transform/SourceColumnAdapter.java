/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface SourceColumnAdapter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isNotNull();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isUnique();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getLength();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPrecision();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getScale();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCheck();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefault();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIndex();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUniqueKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRead();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWrite();
}
