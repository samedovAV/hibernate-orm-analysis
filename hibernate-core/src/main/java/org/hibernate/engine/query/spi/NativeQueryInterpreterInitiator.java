/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.query.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.engine.query.internal.NativeQueryInterpreterStandardImpl;
import org.hibernate.service.spi.SessionFactoryServiceInitiator;
import org.hibernate.service.spi.SessionFactoryServiceInitiatorContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NativeQueryInterpreterInitiator implements SessionFactoryServiceInitiator<NativeQueryInterpreter> {
	/**
	 * Singleton access
	 */
	public static final NativeQueryInterpreterInitiator INSTANCE = new NativeQueryInterpreterInitiator();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryInterpreter initiateService(@Nonnull SessionFactoryServiceInitiatorContext context) {
		return new NativeQueryInterpreterStandardImpl( context.getSessionFactoryOptions().getNativeJdbcParametersIgnored() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<NativeQueryInterpreter> getServiceInitiated() {
		return NativeQueryInterpreter.class;
	}
}
