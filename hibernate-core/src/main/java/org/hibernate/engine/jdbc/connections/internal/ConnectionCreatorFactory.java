/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.internal;

import java.sql.Driver;
import java.util.Map;
import java.util.Properties;

import org.hibernate.service.ServiceRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A factory for {@link ConnectionCreator}.
 *
 * @author Christian Beikov
 */
public interface ConnectionCreatorFactory {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConnectionCreator create(
			Driver driver,
			ServiceRegistry serviceRegistry,
			String url,
			Properties connectionProps,
			Boolean autocommit,
			Integer isolation,
			String initSql,
			Map<String, Object> configurationValues);

}
