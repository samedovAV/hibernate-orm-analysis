/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.support;

import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// @author Steve Ebersole
public class Helper {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean needsIdentityPrePhase(EntityPersister persister, Object identifier) {
		// IDENTITY generation needs pre-phase execution when ID is not yet assigned
		// (i.e., identifier == null means database will generate it)
		return persister.getGenerator().generatedOnExecution() && identifier == null;
	}
}
