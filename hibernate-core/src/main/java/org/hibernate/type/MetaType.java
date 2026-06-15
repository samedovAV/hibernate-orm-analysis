/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.Internal;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.metamodel.mapping.DiscriminatorValue;
import org.hibernate.metamodel.spi.ImplicitDiscriminatorStrategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
@Internal
public class MetaType extends AbstractType {
	public static final String[] REGISTRATION_KEYS = ArrayHelper.EMPTY_STRING_ARRAY;

	private final Type valueType;
	private final ImplicitDiscriminatorStrategy implicitValueStrategy;
	private final Map<DiscriminatorValue,String> discriminatorValuesToEntityNameMap;
	private final Map<String,Object> entityNameToDiscriminatorValueMap;

	public MetaType(
			Type valueType,
			ImplicitDiscriminatorStrategy implicitValueStrategy,
			Map<DiscriminatorValue,String> explicitValueMappings) {
		this.valueType = valueType;
		this.implicitValueStrategy = implicitValueStrategy;

		if ( explicitValueMappings == null || explicitValueMappings.isEmpty() ) {
			discriminatorValuesToEntityNameMap = new HashMap<>();
			entityNameToDiscriminatorValueMap = new HashMap<>();
		}
		else {
			discriminatorValuesToEntityNameMap = explicitValueMappings;
			entityNameToDiscriminatorValueMap = new HashMap<>();
			for ( var entry : discriminatorValuesToEntityNameMap.entrySet() ) {
				entityNameToDiscriminatorValueMap.put( entry.getValue(), entry.getKey() );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getBaseType() {
		return valueType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ImplicitDiscriminatorStrategy getImplicitValueStrategy() {
		return implicitValueStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getRegistrationKeys() {
		return REGISTRATION_KEYS;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<DiscriminatorValue, String> getDiscriminatorValuesToEntityNameMap() {
		return discriminatorValuesToEntityNameMap;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String,Object> getEntityNameToDiscriminatorValueMap(){
		return entityNameToDiscriminatorValueMap;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int[] getSqlTypeCodes(MappingContext mappingContext) throws MappingException {
		return valueType.getSqlTypeCodes( mappingContext );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getColumnSpan(MappingContext mapping) throws MappingException {
		return valueType.getColumnSpan(mapping);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return String.class;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int compare(Object x, Object y, SessionFactoryImplementor sessionFactory) {
		return compare( x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void nullSafeSet(
			PreparedStatement st,
			Object value,
			int index,
			SharedSessionContractImplementor session) throws HibernateException, SQLException {
		throw new UnsupportedOperationException();
//		baseType.nullSafeSet(st, value==null ? null : entityNameToDiscriminatorValueMap.get(value), index, session);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void nullSafeSet(
			PreparedStatement st,
			Object value,
			int index,
			boolean[] settable,
			SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if ( settable[0] ) {
			nullSafeSet(st, value, index, session);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toLoggableString(Object value, SessionFactoryImplementor factory) throws HibernateException {
		return toXMLString(value, factory);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toXMLString(Object value, SessionFactoryImplementor factory) throws HibernateException {
		return (String) value; //value is the entity name
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object fromXMLString(String xml, MappingContext mappingContext) throws HibernateException {
		return xml; //xml is the entity name
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return valueType.getName(); //TODO!
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object deepCopy(Object value, SessionFactoryImplementor factory) throws HibernateException {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object replace(
			Object original,
			Object target,
			SharedSessionContractImplementor session,
			Object owner,
			Map<Object, Object> copyCache) {
		return original;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean[] toColumnNullness(Object value, MappingContext mapping) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isDirty(Object old, Object current, boolean[] checkable, SharedSessionContractImplementor session) throws HibernateException {
		return checkable[0] && isDirty(old, current, session);
	}
}
