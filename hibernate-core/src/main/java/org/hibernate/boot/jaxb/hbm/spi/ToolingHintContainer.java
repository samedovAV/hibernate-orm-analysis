/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for JAXB bindings which are containers of tooling hints.
 *
 * @author Steve Ebersole
 */
public interface ToolingHintContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmToolingHintType> getToolingHints();
}
