/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.InstantiationException;
import org.hibernate.persister.state.spi.StateManagement;
import org.hibernate.persister.state.internal.StandardStateManagement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstracts over things which can have a {@linkplain StateManagement
 * customized state management strategy}, providing slots to plug in
 * extra columns related to custom state management.
 *
 * @author Gavin King
 *
 * @see org.hibernate.annotations.Temporal
 * @see org.hibernate.annotations.Audited
 * @see org.hibernate.annotations.SoftDelete
 *
 * @since 7.4
 */
public interface Stateful extends AuxiliaryTableHolder {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setStateManagementType(Class<? extends StateManagement> stateManagementType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends StateManagement> getStateManagementType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table getMainTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMainTablePartitioned();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMainTablePartitioned(boolean partitioned);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAuxiliaryColumnInPrimaryKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAuxiliaryColumnInPrimaryKey(String key);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPrimaryKeyDisabled();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPrimaryKeyDisabled(boolean disabled);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default StateManagement getStateManagement() {
		final var stateManagementType = getStateManagementType();
		if ( stateManagementType == null ) {
			return StandardStateManagement.INSTANCE;
		}
		else {
			try {
				return (StateManagement)
						stateManagementType
								.getDeclaredField( "INSTANCE" )
								.get( null );
			}
			catch (IllegalAccessException | NoSuchFieldException e) {
				throw new InstantiationException( "Could not create StateManagement",
						stateManagementType, e );
			}
		}
	}
}
