/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.FetchType;
import org.hibernate.graph.AttributeNode;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Integration version of the {@link AttributeNode} contract.
 *
 * @param <J> The type of the attribute
 * @param <E> The element type, if this node represents a
 *        {@linkplain jakarta.persistence.metamodel.PluralAttribute plural attribute},
 *        or the type of the singular attribute, if it doesn't
 * @param <K> The map key type, if this node represents a
 *        {@linkplain jakarta.persistence.metamodel.MapAttribute map attribute}
 *
 * @author Strong Liu
 * @author Steve Ebersole
 * @author Gavin King
 */
public interface AttributeNodeImplementor<J, E, K> extends AttributeNode<J>, GraphNodeImplementor<J> {

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<J, E, K> makeCopy(boolean mutable);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<J> addSubgraph();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends J> SubGraphImplementor<S> addTreatedSubgraph(@Nonnull Class<S> type);

	/**
	 * Mark the attribute as removed or not.
	 * We allow passing the boolean to allow for later unmarking it for removal, e.g.
	 * should {@linkplain org.hibernate.graph.Graph#addAttributeNode} be called later.
	 *
	 * @return {@code this} for chaining.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeNodeImplementor<J, E, K> markRemoved(boolean removed);

	/**
	 * The {@link FetchType} option applied to this node, or {@code null}
	 * if this node neither adds nor removes the attribute.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchType getFetchType();

	/**
	 * Create a value subgraph, without knowing whether it represents a singular value or
	 * plural element, rooted at this attribute node.
	 *
	 * @apiNote This version is more lenient and is therefore disfavored. Prefer the use
	 *          of {@link #addSingularSubgraph()} and {@link #addElementSubgraph()}.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<E> addValueSubgraph();

	/**
	 * Create a value subgraph representing a singular value rooted at this attribute node.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<J> addSingularSubgraph();

	/**
	 * Create a value subgraph representing a plural element rooted at this attribute node.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<E> addElementSubgraph();

	/**
	 * Create a key subgraph rooted at this attribute node.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<K> addKeySubgraph();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void merge(@Nonnull AttributeNodeImplementor<J,E,K> other);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<Class<?>, SubGraphImplementor<?>> getSubGraphs();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<Class<?>, SubGraphImplementor<?>> getKeySubGraphs();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<E> getValueSubgraph();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<K> getKeySubgraph();
}
