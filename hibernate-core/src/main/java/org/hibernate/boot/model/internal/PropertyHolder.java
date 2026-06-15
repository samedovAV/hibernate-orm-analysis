/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import jakarta.annotation.Nullable;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.boot.model.convert.spi.ConverterDescriptor;
import org.hibernate.mapping.Join;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.MemberDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Property holder abstract property containers from their direct implementation
 *
 * @author Emmanuel Bernard
 */
public interface PropertyHolder {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getClassName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityOwnerClassName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table getTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addProperty(Property prop, MemberDetails memberDetails, ClassDetails declaringClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addProperty(Property prop, MemberDetails memberDetails, @Nullable AnnotatedColumns columns, ClassDetails declaringClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void movePropertyToJoin(Property prop, Join join, MemberDetails memberDetails, ClassDetails declaringClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	KeyValue getIdentifier();

	/**
	 * Return true if this component is or is embedded in a @EmbeddedId
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOrWithinEmbeddedId();

	/**
	 * Return true if this component is within an @ElementCollection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isWithinElementCollection();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentClass getPersistentClass();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isComponent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEntity();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setParentProperty(String parentProperty);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getPath();

	/**
	 * return null if the column is not overridden,
	 * or an array of columns if it is
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Column[] getOverriddenColumn(String propertyName);

	/**
	 * return null if the column is not overridden,
	 * or an array of columns if it is
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JoinColumn[] getOverriddenJoinColumn(String propertyName);

	/**
	 * return null if hte foreign key is not overridden, or the foreign key if true
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ForeignKey getOverriddenForeignKey(String propertyName) {
		// todo: does this necessarily need to be a default method?
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnTransformer getOverriddenColumnTransformer(String logicalColumnName);

	/**
	 * return
	 * - null if no join table is present,
	 * - the join table if not overridden,
	 * - the overridden join table otherwise
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JoinTable getJoinTable(MemberDetails attributeMember);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Join addJoin(JoinTable joinTableAnn, boolean noDelayInPkColumnCreation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Join addJoin(JoinTable joinTable, Table table, boolean noDelayInPkColumnCreation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInIdClass();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setInIdClass(Boolean isInIdClass);

	/**
	 * Called during binding to allow the PropertyHolder to inspect its discovered properties.  Mainly
	 * this is used in collecting attribute conversion declarations (via @Convert/@Converts).
	 *
	 * @param property The property
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void startingProperty(MemberDetails property);

	/**
	 * Determine the AttributeConverter to use for the given property.
	 *
	 * @return The ConverterDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> resolveAttributeConverterDescriptor(MemberDetails property, boolean autoApply);

	/**
	 * Is this container modifiable within the entity it belongs to?
	 * For example, an embeddable class might be immutable (all final
	 * fields), but the properties that belong to it are still
	 * modifiable if the embedded field referring to the embeddable
	 * object is non-final.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isModifiable();
}
