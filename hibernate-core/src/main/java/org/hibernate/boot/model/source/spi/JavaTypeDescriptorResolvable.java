/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.model.JavaTypeDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JavaTypeDescriptorResolvable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolveJavaTypeDescriptor(JavaTypeDescriptor descriptor);
}
