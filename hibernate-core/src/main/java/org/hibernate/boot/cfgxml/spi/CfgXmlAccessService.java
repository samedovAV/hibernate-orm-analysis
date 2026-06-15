/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.cfgxml.spi;

import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows access to any {@code cfg.xml} files specified for bootstrapping.
 *
 * @author Steve Ebersole
 */
public interface CfgXmlAccessService extends Service {
	String LOADED_CONFIG_KEY = "hibernate.boot.CfgXmlAccessService.key";

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadedConfig getAggregatedConfig();
}
