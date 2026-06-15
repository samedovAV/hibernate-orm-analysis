/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.hibernate.tool.schema.spi.SchemaManagementException;

import org.jboss.logging.Logger;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ScriptSourceInput implementation for File references.
 *
 * @author Steve Ebersole
 */
public class ScriptSourceInputFromFile extends AbstractScriptSourceInput {
	private static final Logger LOG = Logger.getLogger( ScriptSourceInputFromFile.class );

	private final File file;
	private final String charsetName;

	/**
	 * Constructs a ScriptSourceInputFromFile
	 *
	 * @param file The file to read from
	 * @param charsetName The charset name
	 */
	public ScriptSourceInputFromFile(File file, String charsetName) {
		this.file = file;
		this.charsetName = charsetName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getScriptDescription() {
		return file.getAbsolutePath();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Reader prepareReader() {
		return toReader( file, charsetName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static Reader toReader(File file, String charsetName) {
		if ( ! file.exists() ) {
			LOG.warnf( "Specified schema generation script file [%s] did not exist for reading", file );
			return new Reader() {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public int read(char[] cbuf, int off, int len) {
					return -1;
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public void close() {
				}
			};
		}

		try {
			final var fileInputStream = new FileInputStream( file );
			return charsetName != null
					? new InputStreamReader( fileInputStream, charsetName )
					: new InputStreamReader( fileInputStream );
		}
		catch (IOException e) {
			throw new SchemaManagementException(
					"Unable to open specified script target file [" + file + "] for reading",
					e
			);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void releaseReader(Reader reader) {
		try {
			reader.close();
		}
		catch (IOException e) {
			LOG.warn( "Unable to close file reader for generation script source" );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsScript(URL url) {
		return file.getAbsolutePath().equals( url.getPath() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "ScriptSourceInputFromFile(" + file.getAbsolutePath() + ")";
	}
}
