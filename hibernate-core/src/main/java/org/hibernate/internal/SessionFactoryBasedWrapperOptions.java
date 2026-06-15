/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;


import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.format.FormatMapper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An incomplete implementation of {@link WrapperOptions} which is not backed by a session.
 *
 * @see SessionFactoryImplementor#getWrapperOptions()
 *
 * @author Christian Beikov
 */
class SessionFactoryBasedWrapperOptions implements WrapperOptions {

	private final SessionFactoryImplementor factory;

	SessionFactoryBasedWrapperOptions(SessionFactoryImplementor factory) {
		this.factory = factory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedSessionContractImplementor getSession() {
		throw new UnsupportedOperationException( "No session" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryImplementor getSessionFactory() {
		return factory;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public LobCreator getLobCreator() {
		return factory.getJdbcServices().getLobCreator( getSession() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FormatMapper getXmlFormatMapper() {
		return factory.getSessionFactoryOptions().getXmlFormatMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FormatMapper getJsonFormatMapper() {
		return factory.getSessionFactoryOptions().getJsonFormatMapper();
	}
}
