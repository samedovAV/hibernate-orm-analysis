/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.AnnotationException;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.FractionalSeconds;
import org.hibernate.annotations.GeneratedColumn;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ObjectNameNormalizer;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.spi.PropertyData;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.AggregateColumn;
import org.hibernate.mapping.CheckConstraint;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.Formula;
import org.hibernate.mapping.Join;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.models.spi.ModelsContext;

import static org.hibernate.boot.model.internal.BinderHelper.getPath;
import static org.hibernate.boot.model.internal.BinderHelper.getRelativePath;
import static org.hibernate.boot.model.internal.DialectOverridesAnnotationHelper.getOverridableAnnotation;
import static org.hibernate.boot.BootLogging.BOOT_LOGGER;
import static org.hibernate.internal.util.StringHelper.isBlank;
import static org.hibernate.internal.util.StringHelper.isEmpty;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static org.hibernate.internal.util.StringHelper.nullIfBlank;
import static org.hibernate.internal.util.collections.ArrayHelper.isEmpty;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping to a column, logically representing a
 * {@link jakarta.persistence.Column} annotation, but not
 * every instance corresponds to an explicit annotation in
 * the Java code.
 * <p>
 * This class holds a representation that is intermediate
 * between the annotation of the Java source code, and the
 * mapping model object {@link Column}. It's used only by
 * the {@link AnnotationBinder} while parsing annotations,
 * and does not survive into later stages of the startup
 * process.
 *
 * @author Emmanuel Bernard
 */
public class AnnotatedColumn {

	private Column mappingColumn;
	private boolean insertable = true;
	private boolean updatable = true;
	private String explicitTableName; // the JPA @Column annotation lets you specify a table name
	private boolean isImplicit;
	public String sqlType;
	private Long length;
	private Integer precision;
	private Integer scale;
	private Integer temporalPrecision; // technically scale, but most dbs call it precision so...
	private Integer arrayLength;
	private String logicalColumnName;
	private boolean unique;
	private boolean nullable = true;
	private String formulaString;
	private Formula formula;
	private String readExpression;
	private String writeExpression;

	private String defaultValue;
	private String generatedAs;

	private final List<CheckConstraint> checkConstraints = new ArrayList<>();

	private AnnotatedColumns parent;

	String options;
	String comment;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AnnotatedColumns getParent() {
		return parent;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setParent(AnnotatedColumns parent) {
		parent.addColumn( this );
		this.parent = parent;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getLogicalColumnName() {
		return logicalColumnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlType() {
		return sqlType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Long getLength() {
		return length;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getPrecision() {
		return precision;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getScale() {
		return scale;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getArrayLength() {
		return arrayLength;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setArrayLength(Integer arrayLength) {
		this.arrayLength = arrayLength;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnique() {
		return unique;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFormula() {
		return isNotEmpty( formulaString );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitTableName() {
		return explicitTableName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setExplicitTableName(String explicitTableName) {
		this.explicitTableName = "``".equals( explicitTableName ) ? "" : explicitTableName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFormula(String formula) {
		this.formulaString = formula;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isImplicit() {
		return isImplicit;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setImplicit(boolean implicit) {
		isImplicit = implicit;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLength(Long length) {
		this.length = length;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTemporalPrecision(Integer temporalPrecision) {
		this.temporalPrecision = temporalPrecision;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLogicalColumnName(String logicalColumnName) {
		this.logicalColumnName = logicalColumnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isNullable() {
		return isFormula() || mappingColumn.isNullable();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultValue() {
		return defaultValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addCheckConstraint(String name, String constraint) {
		checkConstraints.add( new CheckConstraint( name, constraint ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addCheckConstraint(String name, String constraint, String options) {
		checkConstraints.add( new CheckConstraint( name, constraint, options ) );
	}

//	public String getComment() {
//		return comment;
//	}

//	public void setComment(String comment) {
//		this.comment = comment;
//	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getGeneratedAs() {
		return generatedAs;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void setGeneratedAs(String as) {
		this.generatedAs = as;
	}

	public AnnotatedColumn() {
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void bind() {
		if ( isNotEmpty( formulaString ) ) {
			BOOT_LOGGER.bindingFormula( formulaString );
			initMappingFormula();
		}
		else {
			initMappingColumn(
					logicalColumnName,
					getParent().getPropertyName(),
					length,
					precision,
					scale,
					temporalPrecision,
					arrayLength,
					nullable,
					sqlType,
					unique,
					true
			);
			if ( defaultValue != null ) {
				mappingColumn.setDefaultValue( defaultValue );
			}
			for ( var constraint : checkConstraints ) {
				mappingColumn.addCheckConstraint( constraint );
			}
			mappingColumn.setOptions( options );

			if ( isNotEmpty( comment ) ) {
				mappingColumn.setComment( comment );
			}
			if ( generatedAs != null ) {
				mappingColumn.setGeneratedAs( generatedAs );
			}
			if ( logicalColumnName != null ) {
				BOOT_LOGGER.bindingColumn( logicalColumnName );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initMappingFormula() {
		formula = new Formula();
		formula.setFormula( formulaString );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void initMappingColumn(
			String columnName,
			String propertyName,
			Long length,
			Integer precision,
			Integer scale,
			Integer temporalPrecision,
			Integer arrayLength,
			boolean nullable,
			String sqlType,
			boolean unique,
			boolean applyNamingStrategy) {
		mappingColumn = new Column();
		mappingColumn.setExplicit( !isImplicit );
		final boolean nameDetermined =
				inferColumnNameIfPossible( columnName, propertyName, applyNamingStrategy );
		mappingColumn.setLength( length );
		if ( precision != null && precision > 0 ) {  //relevant precision
			mappingColumn.setPrecision( precision );
			mappingColumn.setScale( scale );
		}
		if ( temporalPrecision != null ) {
			mappingColumn.setTemporalPrecision( temporalPrecision );
		}
		mappingColumn.setArrayLength( arrayLength );
		mappingColumn.setNullable( nullable );
		mappingColumn.setSqlType( sqlType );
		mappingColumn.setUnique( unique );
		// if the column name is not determined, we will assign the
		// name to the unique key later this method gets called again
		// from linkValueUsingDefaultColumnNaming() in second pass
		if ( unique && nameDetermined ) {
			// assign a unique key name to the column
			getParent().getTable().createUniqueKey( mappingColumn, getBuildingContext() );
		}
		for ( var constraint : checkConstraints ) {
			mappingColumn.addCheckConstraint( constraint );
		}
		mappingColumn.setDefaultValue( defaultValue );
		mappingColumn.setOptions( options );
		mappingColumn.setComment( comment );

		if ( writeExpression != null ) {
			final int numberOfJdbcParams = StringHelper.count( writeExpression, '?' );
			if ( numberOfJdbcParams != 1 ) {
				throw new AnnotationException(
						"Write expression in '@ColumnTransformer' for property '" + propertyName
						+ "' and column '" + logicalColumnName + "'"
						+ " must contain exactly one placeholder character ('?')"
				);
			}
		}

		mappingColumn.setResolvedCustomRead( readExpression );
		mappingColumn.setCustomWrite( writeExpression );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNameDeferred() {
		return mappingColumn == null || isEmpty( mappingColumn.getName() );
	}

	/**
	 * Attempt to infer the column name from the explicit {@code name} given by the annotation and the property or field
	 * name. In the case of a {@link jakarta.persistence.JoinColumn}, this is impossible, due to the rules implemented in
	 * {@link org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl#determineJoinColumnName}. In cases
	 * where the column name cannot be inferred, the {@link Column} is not assigned a name, and this method returns
	 * {@code false}. The "dummy" {@code Column} will later be replaced with a {@code Column} with a name determined by
	 * the {@link ImplicitNamingStrategy} when {@link AnnotatedJoinColumn#linkValueUsingDefaultColumnNaming} is called
	 * during a {@link org.hibernate.boot.spi.SecondPass}.
	 * @return {@code true} if a name could be inferred
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean inferColumnNameIfPossible(String columnName, String propertyName, boolean applyNamingStrategy) {
		if ( isNotEmpty( columnName ) || isNotEmpty( propertyName ) ) {
			mappingColumn.setName(
					processColumnName( resolveLogicalColumnName( columnName, propertyName ),
							applyNamingStrategy, isNotEmpty( columnName ) ) );
			return true;
		}
		else {
			return false;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String resolveLogicalColumnName(String columnName, String propertyName) {
		final String baseColumnName = isNotEmpty( columnName ) ? columnName : inferColumnName( propertyName );
		final var propertyHolder = parent.getPropertyHolder();
		return propertyHolder != null && propertyHolder.isComponent()
				// see if we need to apply one-or-more @EmbeddedColumnNaming patterns
				? applyEmbeddedColumnNaming( baseColumnName, (ComponentPropertyHolder) propertyHolder )
				: baseColumnName;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private String applyEmbeddedColumnNaming(String inferredColumnName, ComponentPropertyHolder propertyHolder) {
		// code
		String result = inferredColumnName;
		boolean appliedAnyPatterns = false;

		final String columnNamingPattern = propertyHolder.getComponent().getColumnNamingPattern();
		if ( isNotEmpty( columnNamingPattern ) ) {
			// zip_code
			result = String.format( columnNamingPattern, result );
			appliedAnyPatterns = true;
		}

		ComponentPropertyHolder tester = propertyHolder;
		while ( tester.parent.isComponent() ) {
			final var parentHolder = (ComponentPropertyHolder) tester.parent;
			final String parentColumnNamingPattern = parentHolder.getComponent().getColumnNamingPattern();
			if ( isNotEmpty( parentColumnNamingPattern ) ) {
				// 	home_zip_code
				result = String.format( parentColumnNamingPattern, result );
				appliedAnyPatterns = true;
			}
			tester = parentHolder;
		}

		if ( appliedAnyPatterns ) {
			// we need to adjust the logical name to be picked up in `#addColumnBinding`
			this.logicalColumnName = result;
		}

		return result;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String processColumnName(String columnName, boolean applyNamingStrategy, boolean isExplicit) {
		if ( applyNamingStrategy ) {
			final var database = getDatabase();
			return getPhysicalNamingStrategy()
					.toPhysicalColumnName( database.toIdentifier( columnName, isExplicit ),
							database.getJdbcEnvironment() )
					.render( database.getDialect() );
		}
		else {
			return getObjectNameNormalizer().toDatabaseIdentifierText( columnName );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String inferColumnName(String propertyName) {
		Identifier implicitName = getObjectNameNormalizer().normalizeIdentifierQuoting(
				getImplicitNamingStrategy().determineBasicColumnName(
						new ImplicitBasicColumnNameSource() {
							final AttributePath attributePath = AttributePath.parse( propertyName );

							@Override
							@Prove(complexity = Complexity.O_1, n = "", count = {})
							public AttributePath getAttributePath() {
								return attributePath;
							}

							@Override
							@Prove(complexity = Complexity.O_1, n = "", count = {})
							public boolean isCollectionElement() {
								// if the propertyHolder is a collection, assume the
								// @Column refers to the element column
								final var propertyHolder = getParent().getPropertyHolder();
								return !propertyHolder.isComponent() && !propertyHolder.isEntity();
							}

							@Override
							@Prove(complexity = Complexity.O_N, n = "", count = {})
							public MetadataBuildingContext getBuildingContext() {
								return AnnotatedColumn.this.getBuildingContext();
							}
						}
				)
		);

		// HHH-6005 magic
		if ( implicitName.getText().contains( "_{element}_" ) ) {
			// Re-derive the identifier (and its quoting) from the replaced text:
			// the "{" and "}" characters in "{element}" auto-quote the original
			// identifier, but after replacement the text contains no special
			// characters so it should be unquoted again — otherwise it bypasses
			// the PhysicalNamingStrategy, which leaves quoted identifiers alone.
			final String replaced = implicitName.getText().replace( "_{element}_", "_" );
			implicitName = getObjectNameNormalizer().normalizeIdentifierQuoting( Identifier.toIdentifier( replaced ) );
		}

		return implicitName.render( getDatabase().getDialect() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private ObjectNameNormalizer getObjectNameNormalizer() {
		return getBuildingContext().getObjectNameNormalizer();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private Database getDatabase() {
		return getBuildingContext().getMetadataCollector().getDatabase();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private PhysicalNamingStrategy getPhysicalNamingStrategy() {
		return getBuildingContext().getBuildingOptions().getPhysicalNamingStrategy();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private ImplicitNamingStrategy getImplicitNamingStrategy() {
		return getBuildingContext().getBuildingOptions().getImplicitNamingStrategy();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return mappingColumn.getName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Column getMappingColumn() {
		return mappingColumn;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInsertable() {
		return insertable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpdatable() {
		return updatable;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
		if ( mappingColumn != null ) {
			mappingColumn.setNullable( nullable );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void setMappingColumn(Column mappingColumn) {
		this.mappingColumn = mappingColumn;
	}

	//TODO: move this operation to AnnotatedColumns!!

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void linkWithAggregateValue(SimpleValue value, Component component) {
		mappingColumn = new AggregateColumn( mappingColumn, component );
		linkWithValue( value );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void linkWithValue(SimpleValue value) {
		if ( formula != null ) {
			value.addFormula( formula );
		}
		else {
			final var table = value.getTable();
			parent.setTable( table );
			mappingColumn.setValue( value );
			value.addColumn( mappingColumn, insertable, updatable );
			table.addColumn( mappingColumn );
			addColumnBinding( value );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void addColumnBinding(SimpleValue value) {
		final String logicalColumnName;
		if ( isNotEmpty( this.logicalColumnName ) ) {
			logicalColumnName = this.logicalColumnName;
		}
		else {
			final Identifier implicitName = getObjectNameNormalizer().normalizeIdentifierQuoting(
					getImplicitNamingStrategy().determineBasicColumnName(
							new ImplicitBasicColumnNameSource() {
								@Override
								@Prove(complexity = Complexity.O_1, n = "", count = {})
								public AttributePath getAttributePath() {
									return AttributePath.parse( getParent().getPropertyName() );
								}

								@Override
								@Prove(complexity = Complexity.O_1, n = "", count = {})
								public boolean isCollectionElement() {
									return false;
								}

								@Override
								@Prove(complexity = Complexity.O_N, n = "", count = {})
								public MetadataBuildingContext getBuildingContext() {
									return AnnotatedColumn.this.getBuildingContext();
								}
							}
					)
			);
			logicalColumnName = implicitName.render( getDatabase().getDialect() );
		}
		getBuildingContext().getMetadataCollector()
				.addColumnNameBinding( value.getTable(), logicalColumnName, getMappingColumn() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forceNotNull() {
		if ( mappingColumn == null ) {
			throw new CannotForceNonNullableException(
					"Cannot perform #forceNotNull because internal org.hibernate.mapping.Column reference is null: " +
							"likely a formula"
			);
		}
		nullable = false;
		mappingColumn.setNullable( false );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildFormulaFromAnnotation(
			org.hibernate.annotations.Formula formulaAnn,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnOrFormulaFromAnnotation(
				null,
				formulaAnn,
				null,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnFromNoAnnotation(
			FractionalSeconds fractionalSeconds,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnsFromAnnotations(
				null,
				fractionalSeconds,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnFromAnnotation(
			jakarta.persistence.Column column,
			FractionalSeconds fractionalSeconds,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnOrFormulaFromAnnotation(
				column,
				null,
				fractionalSeconds,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnsFromAnnotations(
			jakarta.persistence.Column[] columns,
			FractionalSeconds fractionalSeconds,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnsOrFormulaFromAnnotation(
				columns,
				null,
				fractionalSeconds,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				null,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnFromAnnotations(
			jakarta.persistence.Column column,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnsOrFormulaFromAnnotation(
				column == null
						? null
						: new jakarta.persistence.Column[] {column},
				null,
				null,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				suffixForDefaultColumnName,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnOrFormulaFromAnnotation(
			jakarta.persistence.Column column,
			org.hibernate.annotations.Formula formulaAnn,
			FractionalSeconds fractionalSeconds,
//			Comment commentAnn,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {
		return buildColumnsOrFormulaFromAnnotation(
				column==null
						? null
						: new jakarta.persistence.Column[] {column},
				formulaAnn,
				fractionalSeconds,
//				commentAnn,
				nullability,
				propertyHolder,
				inferredData,
				null,
				secondaryTables,
				context
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AnnotatedColumns buildColumnsOrFormulaFromAnnotation(
			jakarta.persistence.Column[] columns,
			org.hibernate.annotations.Formula formulaAnn,
			FractionalSeconds fractionalSeconds,
//			Comment comment,
			Nullability nullability,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context) {

		if ( formulaAnn != null ) {
			final var parent = new AnnotatedColumns();
			parent.setPropertyHolder( propertyHolder );
			parent.setPropertyName( getRelativePath( propertyHolder, inferredData.getPropertyName() ) );
			parent.setBuildingContext( context );
			parent.setJoins( secondaryTables ); //unnecessary
			final var formulaColumn = new AnnotatedColumn();
			formulaColumn.setFormula( formulaAnn.value() );
			formulaColumn.setImplicit( false );
//			formulaColumn.setBuildingContext( context );
//			formulaColumn.setPropertyHolder( propertyHolder );
			formulaColumn.setParent( parent );
			formulaColumn.bind();
			return parent;
		}
		else {
			final var actualColumns = overrideColumns( columns, propertyHolder, inferredData );
			if ( isEmpty( actualColumns ) ) {
				return buildImplicitColumn(
						fractionalSeconds,
						inferredData,
						suffixForDefaultColumnName,
						secondaryTables,
						propertyHolder,
//						comment,
						nullability,
						context
				);
			}
			else {
				return buildExplicitColumns(
//						comment,
						propertyHolder,
						inferredData,
						suffixForDefaultColumnName,
						secondaryTables,
						context,
						actualColumns,
						fractionalSeconds
				);
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static jakarta.persistence.Column[] overrideColumns(
			jakarta.persistence.Column[] columns,
			PropertyHolder propertyHolder,
			PropertyData inferredData ) {
		final String path = getPath( propertyHolder, inferredData );
		final var overriddenCols = propertyHolder.getOverriddenColumn( path );
		if ( overriddenCols != null ) {
			//check for overridden first
			if ( columns != null && overriddenCols.length != columns.length ) {
				//TODO: unfortunately, we never actually see this nice error message, since
				//      PersistentClass.validate() gets called first and produces a worse message
				throw new AnnotationException( "Property '" + path
						+ "' specifies " + columns.length
						+ " '@AttributeOverride's but the overridden property has " + overriddenCols.length
						+ " columns (every column must have exactly one '@AttributeOverride')" );
			}
			if ( BOOT_LOGGER.isTraceEnabled() ) {
				BOOT_LOGGER.columnMappingOverridden( inferredData.getPropertyName() );
			}
			return isEmpty( overriddenCols ) ? null : overriddenCols;
		}
		else {
			return columns;
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static AnnotatedColumns buildExplicitColumns(
//			Comment comment,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			Map<String, Join> secondaryTables,
			MetadataBuildingContext context,
			jakarta.persistence.Column[] actualCols,
			FractionalSeconds fractionalSeconds) {
		final var parent = new AnnotatedColumns();
		parent.setPropertyHolder( propertyHolder );
		parent.setPropertyName( getRelativePath( propertyHolder, inferredData.getPropertyName() ) );
		parent.setJoins( secondaryTables );
		parent.setBuildingContext( context );
		final var database = context.getMetadataCollector().getDatabase();
		for ( var column : actualCols ) {
			buildColumn(
//					comment,
					propertyHolder,
					inferredData,
					suffixForDefaultColumnName,
					parent,
					actualCols.length,
					database,
					column,
					fractionalSeconds,
					getSqlType( context, column ),
					getTableName( column, database ),
					context.getBootstrapContext().getModelsContext()
			);
		}
		return parent;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String getTableName(
			jakarta.persistence.Column column,
			Database database) {
		final String table = column.table();
		return table.isBlank()
				? ""
				: database.getJdbcEnvironment().getIdentifierHelper().toIdentifier( table ).render();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String getSqlType(
			MetadataBuildingContext context,
			jakarta.persistence.Column column) {
		final String columnDefinition = column.columnDefinition();
		return columnDefinition.isBlank()
				? null
				: context.getObjectNameNormalizer().applyGlobalQuoting( columnDefinition );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static AnnotatedColumn buildColumn(
//			Comment comment,
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			AnnotatedColumns parent,
			int numberOfColumns,
			Database database,
			jakarta.persistence.Column column,
			FractionalSeconds fractionalSeconds,
			String sqlType,
			String tableName,
			ModelsContext sourceModelContext) {
		final String columnName = logicalColumnName( inferredData, suffixForDefaultColumnName, database, column );
		final var annotatedColumn = new AnnotatedColumn();
		annotatedColumn.setLogicalColumnName( columnName );
		annotatedColumn.setImplicit( false );
		annotatedColumn.setSqlType( sqlType );
		annotatedColumn.setLength( (long) column.length() );
		if ( fractionalSeconds != null ) {
			annotatedColumn.setTemporalPrecision( fractionalSeconds.value() );
		}
		else {
			annotatedColumn.setPrecision( column.precision() );
			// The passed annotation could also be a MapKeyColumn
			annotatedColumn.setTemporalPrecision( temporalPrecision( column ) );
		}
		annotatedColumn.setScale( column.scale() );
		annotatedColumn.handleArrayLength( inferredData );
		annotatedColumn.setNullable( column.nullable() );
		annotatedColumn.setUnique( column.unique() );
		annotatedColumn.setInsertable( column.insertable() );
		annotatedColumn.setUpdatable( column.updatable() );
		annotatedColumn.setExplicitTableName( tableName );
		annotatedColumn.setParent( parent );
		annotatedColumn.applyColumnDefault( inferredData, numberOfColumns );
		annotatedColumn.applyGeneratedAs( inferredData, numberOfColumns );
		annotatedColumn.applyColumnCheckConstraint( column );
		annotatedColumn.applyColumnOptions( column );
		annotatedColumn.applyColumnComment(column);
		annotatedColumn.applyCheckConstraint( inferredData, numberOfColumns );
		annotatedColumn.extractDataFromPropertyData( propertyHolder, inferredData, sourceModelContext );
		annotatedColumn.bind();
		return annotatedColumn;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static Integer temporalPrecision(jakarta.persistence.Column column) {
		final Integer secondPrecision =
				column.annotationType() == jakarta.persistence.Column.class
						? column.secondPrecision()
						: null;
		return secondPrecision == null || secondPrecision == -1
				? null
				: secondPrecision;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void handleArrayLength(PropertyData inferredData) {
		final var arrayAnn = inferredData.getAttributeMember().getDirectAnnotationUsage( Array.class );
		if ( arrayAnn != null ) {
			setArrayLength( arrayAnn.length() );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String logicalColumnName(
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			Database database,
			jakarta.persistence.Column column) {
		final String columnName = getColumnName( database, column );
		// NOTE: this is the logical column name, not the physical!
		return isEmpty( columnName ) && isNotEmpty( suffixForDefaultColumnName )
				? inferredData.getPropertyName() + suffixForDefaultColumnName
				: columnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String getColumnName(Database database, jakarta.persistence.Column column) {
		final String name = column.name();
		return name.isBlank()
				? null
				: database.getJdbcEnvironment().getIdentifierHelper().toIdentifier( name ).render();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyColumnDefault(PropertyData inferredData, int length) {
		final var memberDetails = inferredData.getAttributeMember();
		if ( memberDetails != null ) {
			final var columnDefault = getOverridableAnnotation(
					memberDetails,
					ColumnDefault.class,
					getBuildingContext()
			);
			if ( columnDefault != null ) {
				if ( length != 1 ) {
					throw new AnnotationException( "'@ColumnDefault' may only be applied to single-column mappings but '"
							+ memberDetails.getName() + "' maps to " + length + " columns" );
				}
				setDefaultValue( columnDefault.value() );
			}
		}
		else {
			BOOT_LOGGER.couldNotPerformColumnDefaultLookup();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyGeneratedAs(PropertyData inferredData, int length) {
		final var memberDetails = inferredData.getAttributeMember();
		if ( memberDetails != null ) {
			final var generatedColumn = getOverridableAnnotation(
					memberDetails,
					GeneratedColumn.class,
					getBuildingContext()
			);
			if ( generatedColumn != null ) {
				if (length!=1) {
					throw new AnnotationException("'@GeneratedColumn' may only be applied to single-column mappings but '"
							+ memberDetails.getName() + "' maps to " + length + " columns" );
				}
				setGeneratedAs( generatedColumn.value() );
			}
		}
		else {
			BOOT_LOGGER.couldNotPerformGeneratedColumnLookup();
		}
}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void applyColumnCheckConstraint(jakarta.persistence.Column column) {
		applyCheckConstraints( column.check() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	void applyCheckConstraints(jakarta.persistence.CheckConstraint[] checkConstraintAnnotationUsages) {
		if ( isNotEmpty( checkConstraintAnnotationUsages ) ) {
			for ( var checkConstraintAnnotationUsage : checkConstraintAnnotationUsages ) {
				addCheckConstraint(
						nullIfBlank( checkConstraintAnnotationUsage.name() ),
						checkConstraintAnnotationUsage.constraint(),
						checkConstraintAnnotationUsage.options()
				);
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	void applyCheckConstraint(PropertyData inferredData, int length) {
		final var memberDetails = inferredData.getAttributeMember();
		if ( memberDetails != null ) {
			// if there are multiple annotations, they're not overrideable
			final var checksAnn = memberDetails.getDirectAnnotationUsage( Checks.class );
			if ( checksAnn != null ) {
				final var checkAnns = checksAnn.value();
				for ( var checkAnn : checkAnns ) {
					addCheckConstraint( nullIfBlank( checkAnn.name() ), checkAnn.constraints() );
				}
			}
			else {
				final var checkAnn = getOverridableAnnotation( memberDetails, Check.class, getBuildingContext() );
				if ( checkAnn != null ) {
					if ( length != 1 ) {
						throw new AnnotationException("'@Check' may only be applied to single-column mappings but '"
								+ memberDetails.getName() + "' maps to " + length + " columns (use a table-level '@Check')" );
					}
					addCheckConstraint( nullIfBlank( checkAnn.name() ), checkAnn.constraints() );
				}
			}
		}
		else {
			BOOT_LOGGER.couldNotPerformCheckLookup();
		}
	}

	//must only be called after all setters are defined and before binding
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void extractDataFromPropertyData(
			PropertyHolder propertyHolder,
			PropertyData inferredData,
			ModelsContext context) {
		if ( inferredData != null ) {
			final var memberDetails = inferredData.getAttributeMember();
			if ( memberDetails != null ) {
				if ( propertyHolder.isComponent() ) {
					processColumnTransformerExpressions( propertyHolder.getOverriddenColumnTransformer( logicalColumnName ) );
				}
				memberDetails.forEachAnnotationUsage( ColumnTransformer.class, context, this::processColumnTransformerExpressions );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void processColumnTransformerExpressions(ColumnTransformer annotation) {
		if ( annotation != null ) {
			final String targetColumnName = annotation.forColumn();
			if ( isBlank( targetColumnName )
					|| targetColumnName.equals( logicalColumnName != null ? logicalColumnName : "" ) ) {
				readExpression = nullIfBlank( annotation.read() );
				writeExpression = nullIfBlank( annotation.write() );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static AnnotatedColumns buildImplicitColumn(
			FractionalSeconds fractionalSeconds,
			PropertyData inferredData,
			String suffixForDefaultColumnName,
			Map<String, Join> secondaryTables,
			PropertyHolder propertyHolder,
//			Comment comment,
			Nullability nullability,
			MetadataBuildingContext context) {
		final var columns = new AnnotatedColumns();
		columns.setPropertyHolder( propertyHolder );
		columns.setPropertyName( getRelativePath( propertyHolder, inferredData.getPropertyName() ) );
		columns.setBuildingContext( context );
		columns.setJoins( secondaryTables );
		columns.setPropertyHolder( propertyHolder );
		final AnnotatedColumn column = new AnnotatedColumn();
//		if ( comment != null ) {
//			column.setComment( comment.value() );
//		}
		//not following the spec but more clean
		if ( nullability != Nullability.FORCED_NULL
				&& !PropertyBinder.isOptional( inferredData.getAttributeMember(), propertyHolder ) ) {
			column.setNullable( false );
		}
		final String propertyName = inferredData.getPropertyName();
//		column.setPropertyHolder( propertyHolder );
//		column.setPropertyName( getRelativePath( propertyHolder, propertyName ) );
//		column.setJoins( secondaryTables );
//		column.setBuildingContext( context );
		// property name + suffix is an "explicit" column name
		final boolean implicit = isEmpty( suffixForDefaultColumnName );
		if ( !implicit ) {
			column.setLogicalColumnName( propertyName + suffixForDefaultColumnName );
		}
		column.setImplicit( implicit );
		column.setParent( columns );
		column.applyColumnDefault( inferredData, 1 );
		column.applyGeneratedAs( inferredData, 1 );
		column.applyCheckConstraint( inferredData, 1 );
		column.extractDataFromPropertyData( propertyHolder, inferredData, context.getBootstrapContext().getModelsContext() );
		column.handleArrayLength( inferredData );
		if ( fractionalSeconds != null ) {
			column.setTemporalPrecision( fractionalSeconds.value() );
		}
		column.bind();
		return columns;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		final var string = new StringBuilder();
		string.append( getClass().getSimpleName() ).append( "(" );
		if ( isNotEmpty( formulaString ) ) {
			string.append( "formula='" ).append( formulaString );
		}
		else if ( isNotEmpty( logicalColumnName ) ) {
			string.append( "column='" ).append( logicalColumnName );
		}
		string.append( ")" );
		return string.toString();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	MetadataBuildingContext getBuildingContext() {
		return getParent().getBuildingContext();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void applyColumnOptions(jakarta.persistence.Column column) {
		options = column.options();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void applyColumnComment(jakarta.persistence.Column column) {
		if ( !column.comment().isBlank() ) {
			comment = column.comment();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOptions(String options){
		this.options = options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setComment(String comment){
		this.comment = comment;
	}
}
