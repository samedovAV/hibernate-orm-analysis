/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import java.util.Objects;
import java.util.function.BiConsumer;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.type.descriptor.java.JavaType;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Base descriptor, within the mapping model, for any part of the
/// application's domain model: an attribute, an entity identifier,
/// collection elements, and so on.
///
/// @see DomainResultProducer
/// @see jakarta.persistence.metamodel.Bindable
///
/// @author Steve Ebersole
public interface ModelPart extends MappingModelExpressible {

	/// The path for this fetchable back to an entity in the domain model.  Acts as a unique
	/// identifier for individual parts.
	/// Some examples:
	/// * For an entity, the role name is simply the entity name.
	/// * For embeddable the role name is the path back to the root entity.  E.g. a Person's address
	/// would be a path `Person#address`.
	///
	/// For a collection the path would be the same as the "collection role".  E.g. an Order's lineItems
	/// would be `Order#lineItems`.  This is the same as the historical `CollectionPersister#getRoleName`.
	///
	/// For the (model)parts of a collection the role is either `{element}` or `{index}` depending.  E.g.
	/// `Order#lineItems.{element}`.  Attributes of the element or index type (embeddable or entity typed)
	/// would be based on this role.  E.g. `Order#lineItems.{element}.quantity`
	///
	/// For an attribute of an embedded, the role would be relative to its "container".  E.g. `Person#address.city` or
	/// `Person#addresses.{element}.city`
	///
	/// @apiNote Whereas [#getPartName()] is local to this part, NavigableRole can be a compound path
	///
	/// @see #getPartName()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigableRole getNavigableRole();

	/// The local part name, which is generally the unqualified role name
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getPartName();

	/// The type for this part.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingType getPartMappingType();

	/// The Java type for this part.  Generally equivalent to
	/// [MappingType#getMappedJavaType()] relative to
	/// [#getPartMappingType()]
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<?> getJavaType();

	/// Whether this model part describes something that physically
	/// exists in the domain model.
	///
	/// For example, an entity's [discriminator][EntityDiscriminatorMapping]
	/// is part of the model, but is not a physical part of the domain model - there
	/// is no "discriminator attribute".
	///
	/// Also indicates whether the part is castable to [VirtualModelPart]
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isVirtual() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEntityIdentifierMapping() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasPartitionedSelectionMapping();

	/// Create a [DomainResult] for a specific reference to this [ModelPart].
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> DomainResult<T> createDomainResult(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			String resultVariable,
			DomainResultCreationState creationState);

	/// Apply SQL selections for a specific reference to this [ModelPart]
	/// outside the domain query's root select clause.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState);

	/// Apply SQL selections for a specific reference to this [ModelPart]
	/// outside the domain query's root select clause.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState,
			BiConsumer<SqlSelection,JdbcMapping> selectionConsumer);

	/// Visits each physical (non-formula)  column.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int forEachColumn(SelectableConsumer consumer) {
		final int[] count = new int[] {0};
		forEachSelectable( 0, (index, selectableMapping) -> {
			if ( !selectableMapping.isFormula() ) {
				consumer.accept( count[0], selectableMapping );
				count[0] = count[0] + 1;
			}
		} );
		return count[0];
	}

	/// A shorthand form of [#forEachSelectable(int,SelectableConsumer)], that passes `0` as offset.
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachSelectable(SelectableConsumer consumer) {
		return forEachSelectable( 0, consumer );
	}

	/// Visits each selectable mapping with the selectable index offset by the given value.
	/// Returns the amount of jdbc types that have been visited.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int forEachSelectable(int offset, SelectableConsumer consumer) {
		return 0;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default AttributeMapping asAttributeMapping() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMappingType asEntityMappingType(){
		return null;
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	default BasicValuedModelPart asBasicValuedModelPart() {
		return null;
	}

	/// A shorthand form of [#breakDownJdbcValues(Object,int,Object,Object,JdbcValueBiConsumer,SharedSessionContractImplementor)],
	/// that passes `0` as offset and null for the two values `X` and `Y`.
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int breakDownJdbcValues(
			Object domainValue,
			JdbcValueConsumer valueConsumer,
			SharedSessionContractImplementor session) {
		return breakDownJdbcValues( domainValue, 0, null, null, valueConsumer, session );
	}

	/// Breaks down the domain value to its constituent JDBC values.
	///
	/// Think of it as breaking the multi-dimensional array into a visitable flat array.
	/// Additionally, it passes through the values `X` and `Y` to the consumer.
	/// Returns the amount of jdbc types that have been visited.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> int breakDownJdbcValues(
			Object domainValue,
			int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session);

	/// A shorthand form of [#decompose(Object,int,Object,Object,JdbcValueBiConsumer,SharedSessionContractImplementor)],
	/// that passes `0` as offset and null for the two values `X` and `Y`.
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int decompose(
			Object domainValue,
			JdbcValueConsumer valueConsumer,
			SharedSessionContractImplementor session) {
		return decompose( domainValue, 0, null, null, valueConsumer, session );
	}

	/// Similar to [#breakDownJdbcValues(Object,int,Object,Object,JdbcValueBiConsumer,SharedSessionContractImplementor)],
	/// but this method is supposed to be used for decomposing values for assignment expressions.
	/// Returns the amount of jdbc types that have been visited.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X, Y> int decompose(
			Object domainValue,
			int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session) {
		return breakDownJdbcValues( domainValue, offset, x, y, valueConsumer, session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType findContainingEntityMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean areEqual(@Nullable Object one, @Nullable Object other, SharedSessionContractImplementor session) {
		// NOTE : deepEquals to account for arrays (compound natural-id)
		return Objects.deepEquals( one, other );
	}

	/// Functional interface for consuming the JDBC values.
	@FunctionalInterface
	interface JdbcValueConsumer extends JdbcValueBiConsumer<Object, Object> {
		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		default void consume(int valueIndex, Object x, Object y, Object value, SelectableMapping jdbcValueMapping) {
			consume( valueIndex, value, jdbcValueMapping );
		}

		/// Consume a JDBC-level jdbcValue. The JDBC jdbcMapping descriptor is also passed in
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void consume(int valueIndex, Object value, SelectableMapping jdbcValueMapping);
	}

	/// Functional interface for consuming the JDBC values, along with two values of type `X` and `Y`.
	@FunctionalInterface
	interface JdbcValueBiConsumer<X, Y> {
		/// Consume a JDBC-level jdbcValue.  The JDBC jdbcMapping descriptor is also passed in
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void consume(int valueIndex, X x, Y y, Object value, SelectableMapping jdbcValueMapping);
	}
}
