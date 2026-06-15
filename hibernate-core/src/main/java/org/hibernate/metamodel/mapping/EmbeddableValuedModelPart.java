/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import java.util.function.Consumer;

import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.query.sqm.sql.SqmToSqlAstConverter;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoinProducer;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.FetchableContainer;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the mapping of an embeddable (composite).
 *
 * @see jakarta.persistence.Embedded
 * @see jakarta.persistence.EmbeddedId
 * @see jakarta.persistence.Embeddable
 */
public interface EmbeddableValuedModelPart extends ValuedModelPart, Fetchable, FetchableContainer, TableGroupJoinProducer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableMappingType getEmbeddableTypeDescriptor();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EmbeddableMappingType getMappedType() {
		return getEmbeddableTypeDescriptor();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JavaType<?> getJavaType() {
		return getEmbeddableTypeDescriptor().getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default ModelPart findSubPart(String name, EntityMappingType treatTargetType) {
		return getEmbeddableTypeDescriptor().findSubPart( name, treatTargetType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void forEachSubPart(IndexedConsumer<ModelPart> consumer, EntityMappingType treatTarget) {
		getEmbeddableTypeDescriptor().forEachSubPart( consumer, treatTarget );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void visitSubParts(Consumer<ModelPart> consumer, EntityMappingType treatTargetType) {
		getEmbeddableTypeDescriptor().visitSubParts( consumer, treatTargetType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getJdbcTypeCount() {
		return getEmbeddableTypeDescriptor().getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getJdbcMapping(int index) {
		return getEmbeddableTypeDescriptor().getJdbcMapping( index );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		return getEmbeddableTypeDescriptor().forEachJdbcType( offset, action );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <X, Y> int forEachJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		return getEmbeddableTypeDescriptor().forEachJdbcValue( value, offset, x, y, valuesConsumer, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <X, Y> int breakDownJdbcValues(
			Object domainValue,
			int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session) {
		return getEmbeddableTypeDescriptor().breakDownJdbcValues( domainValue, offset, x, y, valueConsumer, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default  <X, Y> int decompose(
			Object domainValue,
			int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session) {
		return getEmbeddableTypeDescriptor().decompose( domainValue, offset, x, y, valueConsumer, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getNumberOfFetchables() {
		return getEmbeddableTypeDescriptor().getNumberOfFetchables();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Fetchable getFetchable(int position) {
		return getEmbeddableTypeDescriptor().getFetchable( position );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getSelectableIndex(String selectableName) {
		return getEmbeddableTypeDescriptor().getSelectableIndex( selectableName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SelectableMapping getSelectable(int columnIndex) {
		return getEmbeddableTypeDescriptor().getSelectable( columnIndex );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachSelectable(int offset, SelectableConsumer consumer) {
		return getEmbeddableTypeDescriptor().forEachSelectable( offset, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void forEachInsertable(SelectableConsumer consumer) {
		getEmbeddableTypeDescriptor().forEachInsertable( 0, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void forEachUpdatable(SelectableConsumer consumer) {
		getEmbeddableTypeDescriptor().forEachUpdatable( 0, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean hasPartitionedSelectionMapping() {
		return getEmbeddableTypeDescriptor().hasPartitionedSelectionMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <X, Y> int forEachDisassembledJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		return getEmbeddableTypeDescriptor().forEachDisassembledJdbcValue(
				value,
				offset,
				x,
				y,
				valuesConsumer,
				session
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Object disassemble(Object value, SharedSessionContractImplementor session) {
		return getEmbeddableTypeDescriptor().disassemble( value, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		getEmbeddableTypeDescriptor().addToCacheKey( cacheKey, value, session );
	}

	/**
	 * @see org.hibernate.annotations.Parent
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PropertyAccess getParentInjectionAttributePropertyAccess() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlTuple toSqlExpression(
			TableGroup tableGroup,
			Clause clause,
			SqmToSqlAstConverter walker,
			SqlAstCreationState sqlAstCreationState);
}
