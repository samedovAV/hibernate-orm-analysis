/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import org.hibernate.tool.schema.extract.spi.SequenceInformation;
import org.hibernate.tool.schema.extract.spi.SequenceInformationExtractor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SequenceInformationExtractorLegacyImpl implements SequenceInformationExtractor {
	/**
	 * Singleton access
	 */
	public static final SequenceInformationExtractorLegacyImpl INSTANCE = new SequenceInformationExtractorLegacyImpl();

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Iterable<SequenceInformation> extractMetadata(ExtractionContext extractionContext) throws SQLException {
		final String lookupSql = extractionContext.getJdbcEnvironment().getDialect().getQuerySequencesString();

		// *should* never happen, but to be safe in the interest of performance...
		if ( lookupSql == null ) {
			return SequenceInformationExtractorNoOpImpl.INSTANCE.extractMetadata( extractionContext );
		}

		return extractionContext.getQueryResults(
				lookupSql,
				null,
				resultSet -> {
					final IdentifierHelper identifierHelper = extractionContext.getJdbcEnvironment()
							.getIdentifierHelper();
					final List<SequenceInformation> sequenceInformationList = new ArrayList<>();
					while ( resultSet.next() ) {
						sequenceInformationList.add(
								new SequenceInformationImpl(
										new QualifiedSequenceName(
												identifierHelper.toIdentifier( resultSetCatalogName( resultSet ) ),
												identifierHelper.toIdentifier( resultSetSchemaName( resultSet ) ),
												identifierHelper.toIdentifier( resultSetSequenceName( resultSet ) )
										),
										resultSetStartValueSize( resultSet ),
										resultSetMinValue( resultSet ),
										resultSetMaxValue( resultSet ),
										resultSetIncrementValue( resultSet )
								)
						);
					}
					return sequenceInformationList;
				}
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceNameColumn() {
		return "sequence_name";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceCatalogColumn() {
		return "sequence_catalog";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceSchemaColumn() {
		return "sequence_schema";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceStartValueColumn() {
		return "start_value";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMinValueColumn() {
		return "minimum_value";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMaxValueColumn() {
		return "maximum_value";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceIncrementColumn() {
		return "increment";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String resultSetSequenceName(ResultSet resultSet) throws SQLException {
		return resultSet.getString( sequenceNameColumn() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String resultSetCatalogName(ResultSet resultSet) throws SQLException {
		String column = sequenceCatalogColumn();
		return column != null ? resultSet.getString( column ) : null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String resultSetSchemaName(ResultSet resultSet) throws SQLException {
		String column = sequenceSchemaColumn();
		return column != null ? resultSet.getString( column ) : null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetStartValueSize(ResultSet resultSet) throws SQLException {
		String column = sequenceStartValueColumn();
		return column != null ? resultSet.getLong( column ) : null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetMinValue(ResultSet resultSet) throws SQLException {
		String column = sequenceMinValueColumn();
		return column != null ? resultSet.getLong( column ) : null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetMaxValue(ResultSet resultSet) throws SQLException {
		String column = sequenceMaxValueColumn();
		return column != null ? resultSet.getLong( column ) : null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetIncrementValue(ResultSet resultSet) throws SQLException {
		String column = sequenceIncrementColumn();
		return column != null ? resultSet.getLong( column ) : null;
	}
}
