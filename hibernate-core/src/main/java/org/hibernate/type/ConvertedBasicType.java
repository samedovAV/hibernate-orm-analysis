/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension for implementations of {@link BasicType} which have an implied
 * {@linkplain BasicValueConverter conversion}.
 */
public interface ConvertedBasicType<J> extends BasicType<J> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicValueConverter<J,?> getValueConverter();
}
