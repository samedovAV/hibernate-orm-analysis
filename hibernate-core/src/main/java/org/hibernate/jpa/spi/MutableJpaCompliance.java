/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface MutableJpaCompliance extends JpaCompliance {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setOrderByMappingCompliance(boolean orderByCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setProxyCompliance(boolean proxyCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setQueryCompliance(boolean queryCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTransactionCompliance(boolean transactionCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setClosedCompliance(boolean closedCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCachingCompliance(boolean cachingCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setGeneratorNameScopeCompliance(boolean generatorScopeCompliance);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLoadByIdCompliance(boolean enabled);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompliance immutableCopy();
}
