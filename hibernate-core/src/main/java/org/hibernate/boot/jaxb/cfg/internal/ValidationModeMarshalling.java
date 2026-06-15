/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.cfg.internal;

import org.hibernate.internal.util.StringHelper;

import jakarta.persistence.ValidationMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ValidationModeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ValidationMode fromXml(String name) {
		if ( StringHelper.isEmpty( name ) ) {
			return ValidationMode.AUTO;
		}
		return ValidationMode.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(ValidationMode validationMode) {
		if ( validationMode == null ) {
			return null;
		}
		return validationMode.name();
	}
}
