/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.MappingException;
import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.Alias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing some sort of auxiliary table, for
 * example, an {@linkplain jakarta.persistence.JoinTable association table},
 * a {@linkplain jakarta.persistence.SecondaryTable secondary table}, or a
 * table belonging to a {@linkplain jakarta.persistence.InheritanceType#JOINED
 * joined subclass}.
 *
 * @author Gavin King
 */
public class Join implements AttributeContainer, AuxiliaryTableHolder, Serializable {

	private static final Alias PK_ALIAS = new Alias(15, "PK");

	private final ArrayList<Property> properties = new ArrayList<>();
	private final ArrayList<Property> declaredProperties = new ArrayList<>();
	private Table table;
	private Table auxiliaryTable;
	private Map<String, Column> auxiliaryColumns;
	private KeyValue key;
	private PersistentClass persistentClass;
	private boolean inverse;
	private boolean optional;
	private boolean disableForeignKeyCreation;

	// Custom SQL
	private String customSQLInsert;
	private boolean customInsertCallable;
	private String customSQLUpdate;
	private boolean customUpdateCallable;
	private String customSQLDelete;
	private boolean customDeleteCallable;

	private Supplier<? extends Expectation> insertExpectation;
	private Supplier<? extends Expectation> updateExpectation;
	private Supplier<? extends Expectation> deleteExpectation;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addProperty(Property property) {
		properties.add( property );
		declaredProperties.add( property );
		property.setPersistentClass( persistentClass );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean contains(Property property) {
		return properties.contains( property );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Property getProperty(String propertyName) throws MappingException {
		throw new UnsupportedOperationException(); //TODO
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addMappedSuperclassProperty(Property property ) {
		properties.add( property );
		property.setPersistentClass( persistentClass );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Property> getDeclaredProperties() {
		return declaredProperties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Property> getProperties() {
		return properties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsProperty(Property property) {
		return properties.contains( property );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getAuxiliaryTable() {
		return auxiliaryTable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAuxiliaryTable(Table auxiliaryTable) {
		this.auxiliaryTable = auxiliaryTable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Column getAuxiliaryColumn(String name) {
		return auxiliaryColumns == null ? null : auxiliaryColumns.get( name );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addAuxiliaryColumn(String name, Column column) {
		if ( auxiliaryColumns == null ) {
			auxiliaryColumns = new HashMap<>();
		}
		auxiliaryColumns.put( name, column );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public KeyValue getKey() {
		return key;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setKey(KeyValue key) {
		this.key = key;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentClass getPersistentClass() {
		return persistentClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setPersistentClass(PersistentClass persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void disableForeignKeyCreation() {
		disableForeignKeyCreation = true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void createForeignKey() {
		final var foreignKey = getKey().createForeignKeyOfEntity( persistentClass.getEntityName() );
		if ( foreignKey != null && disableForeignKeyCreation ) {
			foreignKey.disableCreation();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void createPrimaryKey() {
		//Primary key constraint
		final var primaryKey = new PrimaryKey( table );
		primaryKey.setName( PK_ALIAS.toAliasString( table.getName() ) );
		table.setPrimaryKey(primaryKey);
		primaryKey.addColumns( getKey() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getPropertySpan() {
		return properties.size();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCustomSQLInsert(String customSQLInsert, boolean callable) {
		this.customSQLInsert = customSQLInsert;
		this.customInsertCallable = callable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCustomSQLInsert() {
		return customSQLInsert;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomInsertCallable() {
		return customInsertCallable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCustomSQLUpdate(String customSQLUpdate, boolean callable) {
		this.customSQLUpdate = customSQLUpdate;
		this.customUpdateCallable = callable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCustomSQLUpdate() {
		return customSQLUpdate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomUpdateCallable() {
		return customUpdateCallable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCustomSQLDelete(String customSQLDelete, boolean callable) {
		this.customSQLDelete = customSQLDelete;
		this.customDeleteCallable = callable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCustomSQLDelete() {
		return customSQLDelete;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomDeleteCallable() {
		return customDeleteCallable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInverse() {
		return inverse;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInverse(boolean leftJoin) {
		this.inverse = leftJoin;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getClass().getSimpleName() + '(' + table.getName() + ')';
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public boolean isLazy() {
		for ( Property property : properties ) {
			if ( !property.isLazy() ) {
				return false;
			}
		}
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOptional() {
		return optional;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOptional(boolean nullable) {
		this.optional = nullable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Supplier<? extends Expectation> getInsertExpectation() {
		return insertExpectation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInsertExpectation(Supplier<? extends Expectation> insertExpectation) {
		this.insertExpectation = insertExpectation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Supplier<? extends Expectation> getUpdateExpectation() {
		return updateExpectation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUpdateExpectation(Supplier<? extends Expectation> updateExpectation) {
		this.updateExpectation = updateExpectation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Supplier<? extends Expectation> getDeleteExpectation() {
		return deleteExpectation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDeleteExpectation(Supplier<? extends Expectation> deleteExpectation) {
		this.deleteExpectation = deleteExpectation;
	}
}
