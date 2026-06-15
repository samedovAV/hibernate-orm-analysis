/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.profile.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Commonality between entities and collections as something that can be affected by fetch profiles.
 *
 * @author Steve Ebersole
 */
public interface FetchProfileAffectee {
	/**
	 * Register the profile name with the entity/collection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerAffectingFetchProfile(String fetchProfileName);
}
