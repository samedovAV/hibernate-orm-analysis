/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.spi;

import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.models.spi.MemberDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for handling {@linkplain jakarta.persistence.Converter#autoApply auto-apply}
 * checks for JPA {@linkplain jakarta.persistence.AttributeConverter converters}.
 *
 * @author Steve Ebersole
 *
 * @see jakarta.persistence.Converter#autoApply
 */
public interface AutoApplicableConverterDescriptor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAutoApplicable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> getAutoAppliedConverterDescriptorForAttribute(MemberDetails memberDetails, MetadataBuildingContext context);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> getAutoAppliedConverterDescriptorForCollectionElement(MemberDetails memberDetails, MetadataBuildingContext context);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> getAutoAppliedConverterDescriptorForMapKey(MemberDetails memberDetails, MetadataBuildingContext context);
}
