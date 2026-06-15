/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;

import org.hibernate.engine.OptimisticLockStyle;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbEntity extends JaxbEntityOrMappedSuperclass {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setName(@Nullable String name);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbTableImpl getTable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTable(@Nullable JaxbTableImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableExpression();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTableExpression(@Nullable String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbSecondaryTableImpl> getSecondaryTables();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbSynchronizedTableImpl> getSynchronizeTables();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbPrimaryKeyJoinColumnImpl> getPrimaryKeyJoinColumns();

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbForeignKeyImpl getPrimaryKeyForeignKey();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPrimaryKeyForeignKey(@Nullable JaxbForeignKeyImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRowid();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setRowid(@Nullable String value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlRestriction();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlRestriction(@Nullable String value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbSqlSelectImpl getSqlSelect();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getHqlSelect();

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlInsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlInsert(JaxbCustomSqlImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlUpdate(@Nullable JaxbCustomSqlImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCustomSqlImpl getSqlDelete();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSqlDelete(@Nullable JaxbCustomSqlImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isDynamicInsert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDynamicInsert(@Nullable Boolean value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isDynamicUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDynamicUpdate(@Nullable Boolean value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isSelectBeforeUpdate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSelectBeforeUpdate(@Nullable Boolean value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCachingImpl getCaching();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCaching(@Nullable JaxbCachingImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getBatchSize();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setBatchSize(@Nullable Integer value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isLazy();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLazy(@Nullable Boolean value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isMutable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMutable(@Nullable Boolean value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	OptimisticLockStyle getOptimisticLocking();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOptimisticLocking(@Nullable OptimisticLockStyle value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbInheritanceImpl getInheritance();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setInheritance(@Nullable JaxbInheritanceImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDiscriminatorValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDiscriminatorValue(@Nullable String value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbDiscriminatorColumnImpl getDiscriminatorColumn();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDiscriminatorColumn(@Nullable JaxbDiscriminatorColumnImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbDiscriminatorFormulaImpl getDiscriminatorFormula();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDiscriminatorFormula(@Nullable JaxbDiscriminatorFormulaImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbSequenceGeneratorImpl getSequenceGenerator();

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbTableGeneratorImpl getTableGenerator();

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbGenericIdGeneratorImpl getGenericGenerator();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedHqlQueryImpl> getNamedQueries();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedNativeQueryImpl> getNamedNativeQueries();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedStatementImpl> getNamedStatements();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedNativeStatementImpl> getNamedNativeStatements();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedStoredProcedureQueryImpl> getNamedStoredProcedureQueries();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbSqlResultSetMappingImpl> getSqlResultSetMappings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbAttributeOverrideImpl> getAttributeOverrides();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbAssociationOverrideImpl> getAssociationOverrides();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbConvertImpl> getConverts();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbNamedEntityGraphImpl> getNamedEntityGraphs();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbFilterImpl> getFilters();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbFetchProfileImpl> getFetchProfiles();

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbTenantIdImpl getTenantId();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTenantId(@Nullable JaxbTenantIdImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbAttributesContainerImpl getAttributes();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAttributes(@Nullable JaxbAttributesContainerImpl value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isCacheable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCacheable(@Nullable Boolean value);

}
