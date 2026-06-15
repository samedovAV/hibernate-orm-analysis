/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;

import org.hibernate.boot.internal.LimitedCollectionClassification;

import jakarta.persistence.EnumType;
import jakarta.persistence.TemporalType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB binding interface for plural attributes
 *
 * @author Brett Meyer
 */
public interface JaxbPluralAttribute extends JaxbPersistentAttribute, JaxbLockableAttribute, JaxbStandardAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbPluralFetchModeImpl getFetchMode();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetchMode(JaxbPluralFetchModeImpl mode);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCollectionUserTypeImpl getCollectionType();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCollectionType(JaxbCollectionUserTypeImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCollectionIdImpl getCollectionId();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCollectionId(JaxbCollectionIdImpl id);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getBatchSize();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setBatchSize(Integer size);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LimitedCollectionClassification getClassification();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setClassification(LimitedCollectionClassification value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getOrderBy();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOrderBy(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbOrderColumnImpl getOrderColumn();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOrderColumn(JaxbOrderColumnImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getListIndexBase();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setListIndexBase(Integer value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSort();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSort(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbPluralAnyMappingImpl.JaxbSortNaturalImpl getSortNatural();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSortNatural(JaxbPluralAnyMappingImpl.JaxbSortNaturalImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbMapKeyImpl getMapKey();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKey(JaxbMapKeyImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbMapKeyClassImpl getMapKeyClass();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyClass(JaxbMapKeyClassImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TemporalType getMapKeyTemporal();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyTemporal(TemporalType value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EnumType getMapKeyEnumerated();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyEnumerated(EnumType value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbAttributeOverrideImpl> getMapKeyAttributeOverrides();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbConvertImpl> getMapKeyConverts();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbMapKeyColumnImpl getMapKeyColumn();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyColumn(JaxbMapKeyColumnImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbUserTypeImpl getMapKeyType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyType(JaxbUserTypeImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbMapKeyJoinColumnImpl> getMapKeyJoinColumns();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbForeignKeyImpl getMapKeyForeignKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMapKeyForeignKey(JaxbForeignKeyImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isMutable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMutable(Boolean mutable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlRestriction();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlRestriction(String sqlRestriction);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlInsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlInsert(JaxbCustomSqlImpl sqlInsert);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlUpdate(JaxbCustomSqlImpl sqlUpdate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlDelete();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlDelete(JaxbCustomSqlImpl sqlDelete);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlDeleteAll();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlDeleteAll(JaxbCustomSqlImpl sqlDeleteAll);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbFilterImpl> getFilters();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isOptional() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void setOptional(Boolean optional) {

	}
}
