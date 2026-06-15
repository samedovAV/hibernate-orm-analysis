/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.sql.results.graph.Fetchable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes a ModelPart which is a basic value, either<ul>
 *     <li>a {@link jakarta.persistence.Basic} attribute</li>
 *     <li>a basic-valued collection part</li>
 * </ul>
 *
 * @author Steve Ebersole
 */
public interface BasicValuedModelPart extends BasicValuedMapping, ValuedModelPart, Fetchable, SelectableMapping {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MappingType getPartMappingType() {
		return this::getJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getJdbcTypeCount() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getJdbcMapping(int index) {
		return BasicValuedMapping.super.getJdbcMapping( index );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getSingleJdbcMapping() {
		return BasicValuedMapping.super.getSingleJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SelectableMapping getSelectable(int columnIndex) {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int forEachSelectable(int offset, SelectableConsumer consumer) {
		consumer.accept( offset, this );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int forEachSelectable(SelectableConsumer consumer) {
		consumer.accept( 0, this );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasPartitionedSelectionMapping() {
		return isPartitioned();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default BasicValuedModelPart asBasicValuedModelPart() {
		return this;
	}
}
