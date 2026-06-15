/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Common interface for all sub-entity mappings.
 *
 * @author Steve Ebersole
 */
public interface SubEntityInfo extends EntityInfo {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExtends();
}
