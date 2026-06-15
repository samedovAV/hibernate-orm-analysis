/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal.options;

import jakarta.persistence.EntityAgent;
import org.hibernate.FlushMode;
import org.hibernate.SessionEventListener;
import org.hibernate.engine.creation.internal.SessionCreationOptions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.OptionsHelper;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Mutable collector for options which apply to
/// [stateless sessions][org.hibernate.StatelessSession].
///
/// Stateless sessions share the common creation options but do not
/// support the stateful-only knobs represented by [StatefulOptions].
/// The inherited [SessionCreationOptions] getter methods for those
/// unsupported settings return the fixed values expected by stateless
/// session construction.
///
/// @since 8.0
/// @author Steve Ebersole
public class StatelessOptions extends CommonOptions implements SessionCreationOptions {
	public StatelessOptions(SessionFactoryImplementor sessionFactory) {
		super( sessionFactory );
	}

	/**
	 * Apply a Jakarta Persistence creation option to this collector.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StatelessOptions apply(EntityAgent.CreationOption option) {
		OptionsHelper.applyOption( this, option );
		return this;
	}

	/**
	 * Apply Jakarta Persistence creation options to this collector.
	 */
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public StatelessOptions apply(EntityAgent.CreationOption... options) {
		if ( options != null ) {
			for ( var option : options ) {
				apply( option );
			}
		}
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean shouldAutoJoinTransactions() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FlushMode getInitialSessionFlushMode() {
		return FlushMode.ALWAYS;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean shouldAutoClose() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean shouldAutoClear() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIdentifierRollbackEnabled() {
		// identifier rollback is not yet implemented for StatelessSessions
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SessionEventListener> getCustomSessionEventListeners() {
		return null;
	}
}
