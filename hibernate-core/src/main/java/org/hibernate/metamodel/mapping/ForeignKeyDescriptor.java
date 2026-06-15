/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import java.util.function.IntFunction;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.internal.MappingModelCreationProcess;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupProducer;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchParent;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for foreign-keys
 */
public interface ForeignKeyDescriptor extends VirtualModelPart, ValuedModelPart {

	String PART_NAME = "{fk}";
	String TARGET_PART_NAME = "{fk-target}";

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getPartName() {
		return PART_NAME;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getKeyTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTargetTable();


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValuedModelPart getKeyPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValuedModelPart getTargetPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isKeyPart(ValuedModelPart modelPart);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ValuedModelPart getPart(Nature nature) {
		if ( nature == Nature.KEY ) {
			return getKeyPart();
		}
		else {
			return getTargetPart();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Side getKeySide();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Side getTargetSide();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Side getSide(Nature nature) {
		if ( nature == Nature.KEY ) {
			return getKeySide();
		}
		else {
			return getTargetSide();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getContainingTableExpression() {
		return getKeyTable();
	}

	/**
	 * Compare the 2 values
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int compare(Object key1, Object key2);

	/**
	 * Create a DomainResult for the referring-side of the fk
	 * The table group must be the one containing the target.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResult<?> createKeyDomainResult(
			NavigablePath navigablePath,
			TableGroup targetTableGroup,
			FetchParent fetchParent,
			DomainResultCreationState creationState);

	/**
	 * Create a DomainResult for the referring-side of the fk
	 * The table group must be the one containing the target.
	 * The {@link Nature} is the association side of the foreign key i.e. {@link Association#getSideNature()}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResult<?> createKeyDomainResult(
			NavigablePath navigablePath,
			TableGroup targetTableGroup,
			Nature fromSide,
			FetchParent fetchParent,
			DomainResultCreationState creationState);

	/**
	 * Create a DomainResult for the target-side of the fk
	 * The table group must be the one containing the target
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResult<?> createTargetDomainResult(
			NavigablePath navigablePath,
			TableGroup targetTableGroup,
			FetchParent fetchParent,
			DomainResultCreationState creationState);

	/**
	 * Create a DomainResult for the referring-side of the fk
	 * The table group must be the one containing the target.
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> DomainResult<T> createDomainResult(
			NavigablePath navigablePath,
			TableGroup targetTableGroup,
			String resultVariable,
			DomainResultCreationState creationState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate generateJoinPredicate(
			TableGroup targetSideTableGroup,
			TableGroup keySideTableGroup,
			SqlAstCreationState creationState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate generateJoinPredicate(
			TableReference targetSideReference,
			TableReference keySideReference,
			SqlAstCreationState creationState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSimpleJoinPredicate(Predicate predicate);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectableMapping getSelectable(int columnIndex);

	/**
	 * Visits the FK "referring" columns
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int forEachSelectable(int offset, SelectableConsumer consumer) {
		return visitKeySelectables( offset, consumer );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Object getAssociationKeyFromSide(
			Object targetObject,
			Nature nature,
			SharedSessionContractImplementor session) {
		return getAssociationKeyFromSide( targetObject, getSide( nature ), session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getAssociationKeyFromSide(
			Object targetObject,
			ForeignKeyDescriptor.Side side,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int visitKeySelectables(int offset, SelectableConsumer consumer);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int visitKeySelectables(SelectableConsumer consumer)  {
		return visitKeySelectables( 0, consumer );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int visitTargetSelectables(int offset, SelectableConsumer consumer);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int visitTargetSelectables(SelectableConsumer consumer) {
		return visitTargetSelectables( 0, consumer );
	}

	/**
	 * Return a copy of this foreign key descriptor with the selectable mappings as provided by the given accessor.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDescriptor withKeySelectionMapping(
			ManagedMappingType declaringType,
			TableGroupProducer declaringTableGroupProducer,
			IntFunction<SelectableMapping> selectableMappingAccess,
			MappingModelCreationProcess creationProcess);

	/**
	 * Return a copy of this foreign key descriptor with the target part as given by the argument.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDescriptor withTargetPart(ValuedModelPart targetPart);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AssociationKey getAssociationKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasConstraint();

	enum Nature {
		KEY,
		TARGET;

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Nature inverse() {
			return this == KEY ? TARGET : KEY;
		}
	}

	interface Side {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Nature getNature();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ValuedModelPart getModelPart();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEmbedded();

}
