/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common interface for mappings that may contain secondary table (join) mappings.
 *
 * @author Steve Ebersole
 */
public interface SecondaryTableContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmSecondaryTableType> getJoin();
}
