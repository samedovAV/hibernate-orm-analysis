/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;

import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Jan Schatteman
 */
public interface JaxbNamedQuery extends JaxbQueryHintContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuery();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getTimeout();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isCacheable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCacheRegion();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFetchSize();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isReadOnly();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbQueryParamTypeImpl> getQueryParam();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheMode getCacheMode();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FlushMode getFlushMode();
}
