/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.PluralAttribute;
import org.hibernate.Internal;
import org.hibernate.graph.AttributeNode;
import org.hibernate.graph.Graph;
import org.hibernate.graph.SubGraph;
import org.hibernate.metamodel.model.domain.PersistentAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Integration version of the {@link Graph} contract.
 *
 * @author Strong Liu
 * @author Steve Ebersole
 * @author Andrea Boriero
 * @author Gavin King
 */
public interface GraphImplementor<J> extends Graph<J>, GraphNodeImplementor<J> {

	/**
	 * Returns the named node associated with the graph, if one, including
	 * checks into treated subgraphs.
	 * Returns the node regardless of {@linkplain AttributeNode#isRemoved()} which
	 * is important for actually implementing proper spec compliance with regard to
	 * impact of removed nodes.
	 *
	 * @return The named node, or {@code null}.
	 *
	 * @see jakarta.persistence.Graph#removeAttributeNode
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<?,?,?> findNode(@Nonnull String name);

	/**
	 * Retrieve an already existing node, if one, from the graph excluding all other checks.
	 * Used in implementing {@linkplain #findNode(String)}
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<?,?,?> getExistingNode(@Nonnull PersistentAttribute<?, ?> attribute);

	/**
	 * Retrieve an already existing node, if one, from the graph excluding all other checks.
	 * Used in implementing {@linkplain #findNode(String)}
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<?,?,?> getExistingNode(@Nonnull String attributeName);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	GraphImplementor<J> makeCopy(boolean mutable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void merge(@Nullable GraphImplementor<J> other);

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void mergeInternal(@Nonnull GraphImplementor<J> graph);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends AttributeNodeImplementor<?,?,?>> getAttributeNodeList();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<PersistentAttribute<? super J, ?>, AttributeNodeImplementor<?,?,?>> getNodes();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<Class<? extends J>, SubGraphImplementor<? extends J>> getTreatedSubgraphs();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> AttributeNodeImplementor<Y,?,?> getAttributeNode(@Nonnull String attributeName);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> AttributeNodeImplementor<Y,?,?> getAttributeNode(@Nonnull Attribute<? super J, Y> attribute);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<?,?,?> findOrCreateAttributeNode(@Nonnull String name);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> AttributeNodeImplementor<Y,?,?> addAttributeNode(@Nonnull Attribute<? super J, Y> attribute);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends J> SubGraphImplementor<Y> addTreatedSubgraph(@Nonnull Class<Y> type);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends J> SubGraphImplementor<Y> addTreatedSubgraph(@Nonnull ManagedType<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SubGraphImplementor<X> addSubgraph(@Nonnull String attributeName);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SubGraphImplementor<X> addSubgraph(@Nonnull String attributeName, @Nonnull Class<X> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SubGraphImplementor<X> addSubgraph(@Nonnull Attribute<? super J, X> attribute);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SubGraphImplementor<Y> addTreatedSubgraph(@Nonnull Attribute<? super J, ? super Y> attribute, @Nonnull Class<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<AJ> SubGraphImplementor<AJ> addTreatedSubgraph(@Nonnull Attribute<? super J, ? super AJ> attribute, @Nonnull ManagedType<AJ> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SubGraphImplementor<E> addTreatedElementSubgraph(@Nonnull PluralAttribute<? super J, ?, ? super E> attribute, @Nonnull Class<E> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<AJ> SubGraph<AJ> addTreatedElementSubgraph(@Nonnull PluralAttribute<? super J, ?, ? super AJ> attribute, @Nonnull ManagedType<AJ> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SubGraphImplementor<X> addKeySubgraph(@Nonnull String attributeName);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SubGraphImplementor<X> addKeySubgraph(@Nonnull String attributeName, @Nonnull Class<X> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K> SubGraphImplementor<K> addTreatedMapKeySubgraph(@Nonnull MapAttribute<? super J, ? super K, ?> attribute, @Nonnull Class<K> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<AJ> SubGraphImplementor<AJ> addTreatedMapKeySubgraph(@Nonnull MapAttribute<? super J, ? super AJ, ?> attribute, @Nonnull ManagedType<AJ> type);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAttributeNode(@Nonnull String attributeName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAttributeNode(@Nonnull Attribute<? super J, ?> attribute);
}
