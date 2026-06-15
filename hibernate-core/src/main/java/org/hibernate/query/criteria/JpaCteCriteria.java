/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.sql.ast.tree.cte.CteMaterialization;
import org.hibernate.sql.ast.tree.cte.CteSearchClauseKind;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A CTE (common table expression) criteria.
 */
@Incubating
public interface JpaCteCriteria<T> extends JpaCriteriaNode {

	/**
	 * The name under which this CTE is registered.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * The type of the CTE.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCteCriteriaType<T> getType();

	/**
	 * The definition of the CTE.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<?> getCteDefinition();

	/**
	 * The container within this CTE is registered.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCteContainer getCteContainer();

	/**
	 * The materialization hint for the CTE.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CteMaterialization getMaterialization();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMaterialization(CteMaterialization materialization);

	/**
	 * The kind of search (breadth-first or depth-first) that should be done for a recursive query.
	 * May be null if unspecified or if this is not a recursive query.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CteSearchClauseKind getSearchClauseKind();

	/**
	 * The order by which should be searched.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JpaSearchOrder> getSearchBySpecifications();

	/**
	 * The attribute name by which one can order the final CTE result, to achieve the search order.
	 * Note that an implicit {@link JpaCteCriteriaAttribute} will be made available for this.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSearchAttributeName();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void search(CteSearchClauseKind kind, String searchAttributeName, JpaSearchOrder... searchOrders) {
		search( kind, searchAttributeName, Arrays.asList( searchOrders ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void search(CteSearchClauseKind kind, String searchAttributeName, List<JpaSearchOrder> searchOrders);

	/**
	 * The attributes to use for cycle detection.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JpaCteCriteriaAttribute> getCycleAttributes();

	/**
	 * The attribute name which is used to mark when a cycle has been detected.
	 * Note that an implicit {@link JpaCteCriteriaAttribute} will be made available for this.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCycleMarkAttributeName();

	/**
	 * The attribute name that represents the computation path, which is used for cycle detection.
	 * Note that an implicit {@link JpaCteCriteriaAttribute} will be made available for this.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCyclePathAttributeName();

	/**
	 * The value which is set for the cycle mark attribute when a cycle is detected.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getCycleValue();

	/**
	 * The default value for the cycle mark attribute when no cycle is detected.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getNoCycleValue();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cycle(String cycleMarkAttributeName, JpaCteCriteriaAttribute... cycleColumns) {
		cycleUsing( cycleMarkAttributeName, null, Arrays.asList( cycleColumns ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cycle(String cycleMarkAttributeName, List<JpaCteCriteriaAttribute> cycleColumns) {
		cycleUsing( cycleMarkAttributeName, null, true, false, cycleColumns );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void cycleUsing(String cycleMarkAttributeName, String cyclePathAttributeName, JpaCteCriteriaAttribute... cycleColumns) {
		cycleUsing( cycleMarkAttributeName, cyclePathAttributeName, Arrays.asList( cycleColumns ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void cycleUsing(String cycleMarkAttributeName, String cyclePathAttributeName, List<JpaCteCriteriaAttribute> cycleColumns) {
		cycleUsing( cycleMarkAttributeName, cyclePathAttributeName, true, false, cycleColumns );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> void cycle(String cycleMarkAttributeName, X cycleValue, X noCycleValue, JpaCteCriteriaAttribute... cycleColumns) {
		cycleUsing( cycleMarkAttributeName, null, cycleValue, noCycleValue, Arrays.asList( cycleColumns ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> void cycle(String cycleMarkAttributeName, X cycleValue, X noCycleValue, List<JpaCteCriteriaAttribute> cycleColumns) {
		cycleUsing( cycleMarkAttributeName, null, cycleValue, noCycleValue, cycleColumns );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <X> void cycleUsing(String cycleMarkAttributeName, String cyclePathAttributeName, X cycleValue, X noCycleValue, JpaCteCriteriaAttribute... cycleColumns) {
		cycleUsing( cycleMarkAttributeName, cyclePathAttributeName, cycleValue, noCycleValue, Arrays.asList( cycleColumns ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> void cycleUsing(String cycleMarkAttributeName, String cyclePathAttributeName, X cycleValue, X noCycleValue, List<JpaCteCriteriaAttribute> cycleColumns);
}
