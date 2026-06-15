/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.env.internal;

import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.engine.jdbc.proxy.BlobProxy;
import org.hibernate.engine.jdbc.proxy.ClobProxy;
import org.hibernate.engine.jdbc.proxy.NClobProxy;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link LobCreator} implementation using non-contextual or local creation, meaning that we generate the LOB
 * references ourselves as opposed to delegating to the {@linkplain java.sql.Connection JDBC connection}.
 *
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class NonContextualLobCreator extends AbstractLobCreator implements LobCreator {
	/**
	 * Singleton access
	 */
	public static final NonContextualLobCreator INSTANCE = new NonContextualLobCreator();

	private NonContextualLobCreator() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Blob createBlob(byte[] bytes) {
		return BlobProxy.generateProxy( bytes );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Blob createBlob(InputStream stream, long length) {
		return BlobProxy.generateProxy( stream, length );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Clob createClob(String string) {
		return ClobProxy.generateProxy( string );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Clob createClob(Reader reader, long length) {
		return ClobProxy.generateProxy( reader, length );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NClob createNClob(String string) {
		return NClobProxy.generateProxy( string );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NClob createNClob(Reader reader, long length) {
		return NClobProxy.generateProxy( reader, length );
	}
}
