/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;


import org.hibernate.sql.results.jdbc.spi.RowProcessingState;

import jakarta.annotation.Nullable;

import static java.util.Objects.requireNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public abstract class InitializerData {
	protected final RowProcessingState rowProcessingState;
	protected Initializer.State state = Initializer.State.UNINITIALIZED;
	protected @Nullable Object instance;

	public InitializerData(RowProcessingState rowProcessingState) {
		this.rowProcessingState = rowProcessingState;
	}

	/*
	 * Used by Hibernate Reactive
	 */
	public InitializerData(InitializerData original) {
		requireNonNull( original );
		this.rowProcessingState = original.rowProcessingState;
		this.state = original.state;
		this.instance = original.instance;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RowProcessingState getRowProcessingState() {
		return rowProcessingState;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Initializer.State getState() {
		return state;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setState(Initializer.State state) {
		this.state = state;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getInstance() {
		return instance;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInstance(@Nullable Object instance) {
		this.instance = instance;
	}
}
