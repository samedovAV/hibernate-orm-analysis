/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.spi;

import java.util.List;

import org.hibernate.boot.model.TypeContributor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An object that provides a list of {@link TypeContributor}s to the JPA persistence provider.
 * <p>
 * An implementation may be registered with the JPA provider using the property
 * {@value org.hibernate.jpa.boot.spi.JpaSettings#TYPE_CONTRIBUTORS}.
 *
 * @deprecated Consider using {@linkplain java.util.ServiceLoader discovery} instead to
 * dynamically locate {@linkplain TypeContributor contributors}.
 *
 * @author Brett Meyer
 */
@Deprecated(forRemoval = true)
public interface TypeContributorList {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<TypeContributor> getTypeContributors();
}
