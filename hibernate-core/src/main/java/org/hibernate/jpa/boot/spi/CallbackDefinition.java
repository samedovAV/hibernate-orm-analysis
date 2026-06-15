/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.spi;

import java.io.Serializable;

import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Boot model definition of a Jakarta Persistence style callback.
///
/// @author Steve Ebersole
public interface CallbackDefinition extends Serializable {
	/// Create the corresponding runtime callback.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Callback<Object> createCallback(ManagedBeanRegistry beanRegistry);
}
