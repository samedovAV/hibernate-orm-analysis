/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines additional information for a EmbeddableSource in relation to
 * the thing that contains it.
 *
 * @author Steve Ebersole
 */
public interface EmbeddableSourceContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeRole getAttributeRoleBase();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributePath getAttributePathBase();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ToolingHintContext getToolingHintContextBaselineForEmbeddable();
}
