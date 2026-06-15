/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.QualifiedTypeName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class AbstractUserDefinedType implements UserDefinedType {

	private final String contributor;

	private Identifier catalog;
	private Identifier schema;
	private Identifier name;

	public AbstractUserDefinedType(
			String contributor,
			Namespace namespace,
			Identifier physicalTypeName) {
		this.contributor = contributor;
		this.catalog = namespace.getPhysicalName().catalog();
		this.schema = namespace.getPhysicalName().schema();
		this.name = physicalTypeName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContributor() {
		return contributor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQualifiedName(SqlStringGenerationContext context) {
		return context.format( new QualifiedTypeName( catalog, schema, name ) );
	}


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setName(String name) {
		this.name = Identifier.toIdentifier( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name == null ? null : name.getText();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getNameIdentifier() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedName() {
		return name == null ? null : name.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedName(Dialect dialect) {
		return name == null ? null : name.render( dialect );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedTableName getQualifiedTableName() {
		return name == null ? null : new QualifiedTableName( catalog, schema, name );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isQuoted() {
		return name.isQuoted();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setQuoted(boolean quoted) {
		if ( quoted != name.isQuoted() ) {
			name = new Identifier( name.getText(), quoted );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSchema(String schema) {
		this.schema = Identifier.toIdentifier( schema );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSchema() {
		return schema == null ? null : schema.getText();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedSchema() {
		return schema == null ? null : schema.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedSchema(Dialect dialect) {
		return schema == null ? null : schema.render( dialect );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSchemaQuoted() {
		return schema != null && schema.isQuoted();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCatalog(String catalog) {
		this.catalog = Identifier.toIdentifier( catalog );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCatalog() {
		return catalog == null ? null : catalog.getText();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedCatalog() {
		return catalog == null ? null : catalog.render();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedCatalog(Dialect dialect) {
		return catalog == null ? null : catalog.render( dialect );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCatalogQuoted() {
		return catalog != null && catalog.isQuoted();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (catalog == null ? 0 : catalog.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (schema == null ? 0 : schema.hashCode());
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean equals(Object object) {
		return object != null
			&& object.getClass() == getClass()
			&& equals( (AbstractUserDefinedType) object );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean equals(AbstractUserDefinedType table) {
		if ( null == table ) {
			return false;
		}
		else if ( this == table ) {
			return true;
		}
		else {
			return Identifier.areEqual( name, table.name )
				&& Identifier.areEqual( schema, table.schema )
				&& Identifier.areEqual( catalog, table.catalog );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		final var string = new StringBuilder()
				.append( getClass().getSimpleName() )
				.append( '(' );
		if ( getCatalog() != null ) {
			string.append( getCatalog() ).append( "." );
		}
		if ( getSchema() != null ) {
			string.append( getSchema() ).append( "." );
		}
		string.append( getName() ).append( ')' );
		return string.toString();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExportIdentifier() {
		final var qualifiedName = new StringBuilder();
		if ( catalog != null ) {
			qualifiedName.append( catalog.render() ).append( '.' );
		}
		if ( schema != null ) {
			qualifiedName.append( schema.render() ).append( '.' );
		}
		return qualifiedName.append( name.render() ).toString();
	}

}
