/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.spi;

import java.io.InputStream;
import javax.xml.transform.Source;

import org.hibernate.boot.jaxb.Origin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for performing JAXB binding.
 *
 * @author Steve Ebersole
 */
public interface Binder<T> {
	/**
	 * Bind from an XML source.
	 *
	 * @param source The XML source.
	 * @param origin The descriptor of the source origin
	 * @return The bound JAXB model
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends T> Binding<X> bind(Source source, Origin origin);

	/**
	 * Bind from an InputStream
	 *
	 * @param stream The InputStream containing XML
	 * @param origin The descriptor of the stream origin
	 * @return The bound JAXB model
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends T> Binding<X> bind(InputStream stream, Origin origin);
}
