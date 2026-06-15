/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.format;


import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * JSON document producer.
 * Implementation of this inteface will used to build a JSON document.
 * Implementation example is {@link StringJsonDocumentWriter }
 * @author Emmanuel Jannetti
 */

public interface JsonDocumentWriter {
	/**
	 * Starts a new JSON Objects.
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter startObject();

	/**
	 * Ends a new JSON Objects
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter endObject();

	/**
	 * Starts a new JSON array.
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter startArray();

	/**
	 * Ends a new JSON array.
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter endArray();

	/**
	 * Adds a new JSON element name.
	 * @param key the element name.
	 * @return this instance
	 * @throws IllegalArgumentException key name does not follow JSON specification.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter objectKey(String key);

	/**
	 * Adds a new JSON element null value.
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter nullValue();

	/**
	 * Adds a new JSON element numeric value.
	 * @return this instance
	 * @param value the element numeric name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter numericValue(Number value);

	/**
	 * Adds a new JSON element boolean value.
	 * @return this instance
	 * @param value the element boolean name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter booleanValue(boolean value);

	/**
	 * Adds a new JSON element string value.
	 * @return this instance
	 * @param value the element string name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JsonDocumentWriter stringValue(String value);

	/**
	 * Adds a JSON value to the document
	 * @param value the value to be serialized
	 * @param javaType the Java type of the value
	 * @param jdbcType the JDBC type for the value to be serialized
	 * @param options the wrapping options
	 * @return this instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JsonDocumentWriter serializeJsonValue(Object value,
							JavaType<T> javaType,
							JdbcType jdbcType,
							WrapperOptions options);
}
