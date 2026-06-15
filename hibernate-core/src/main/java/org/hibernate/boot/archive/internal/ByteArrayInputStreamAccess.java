/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.archive.internal;

import org.hibernate.boot.archive.spi.InputStreamAccess;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// An InputStreamAccess implementation based on a byte array
///
/// @author Steve Ebersole
public class ByteArrayInputStreamAccess implements InputStreamAccess, Serializable {
	private final String name;
	private final byte[] bytes;

	public ByteArrayInputStreamAccess(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getStreamName() {
		return name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public InputStream accessInputStream() {
		return new ByteArrayInputStream( bytes );
	}
}
