/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.usertype;

import org.hibernate.Incubating;
import org.hibernate.annotations.Type;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.models.spi.MemberDetails;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information useful during {@linkplain UserType} creation and initialization.
 *
 * @author Yanming Zhou
 * @see AnnotationBasedUserType
 *
 * @since 7.3
 */
@Incubating
public interface UserTypeCreationContext {
	/**
	 * Access to the {@link MetadataBuildingContext}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataBuildingContext getBuildingContext();

	/**
	 * Access to available services.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();

	/**
	 * Access to the {@link MemberDetails}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MemberDetails getMemberDetails();

	/**
	 * Access to the parameters.
	 *
	 * @see Type#parameters()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Properties getParameters();

}
