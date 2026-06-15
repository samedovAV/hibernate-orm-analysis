/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbQueryHintContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDescription();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JaxbQueryHint> getHints();
}
