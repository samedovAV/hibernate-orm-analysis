/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.io.Serializable;

import org.hibernate.AssertionFailure;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.spi.TypeConfiguration;

import static org.hibernate.engine.internal.ForeignKeys.getEntityIdentifierIfNotUnsaved;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A one-to-one association that maps to specific formula(s)
 * instead of the primary key column of the owning entity.
 *
 * @author Gavin King
 */
public class SpecialOneToOneType extends OneToOneType {

	public SpecialOneToOneType(
			TypeConfiguration typeConfiguration,
			String referencedEntityName,
			ForeignKeyDirection foreignKeyType,
			boolean referenceToPrimaryKey,
			String uniqueKeyPropertyName,
			boolean lazy,
			boolean unwrapProxy,
			String entityName,
			String propertyName,
			boolean constrained) {
		super(
				typeConfiguration,
				referencedEntityName,
				foreignKeyType,
				referenceToPrimaryKey,
				uniqueKeyPropertyName,
				lazy,
				unwrapProxy,
				entityName,
				propertyName,
				constrained
			);
	}

	public SpecialOneToOneType(SpecialOneToOneType original, String superTypeEntityName) {
		super( original, superTypeEntityName );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getColumnSpan(MappingContext mapping) throws MappingException {
		return super.getIdentifierOrUniqueKeyType( mapping ).getColumnSpan( mapping );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int[] getSqlTypeCodes(MappingContext mappingContext) throws MappingException {
		return super.getIdentifierOrUniqueKeyType( mappingContext ).getSqlTypeCodes( mappingContext );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useLHSPrimaryKey() {
		return false;
	}

	// TODO: copy/paste from ManyToOneType
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Serializable disassemble(Object value, SharedSessionContractImplementor session, Object owner)
	throws HibernateException {

		if (value==null) {
			return null;
		}
		else {
			// cache the actual id of the object, not the value of the
			// property-ref, which might not be initialized
			final Object id = getEntityIdentifierIfNotUnsaved( getAssociatedEntityName(), value, session );
			if ( id==null ) {
				throw new AssertionFailure(
						"cannot cache a reference to an object with a null id: " +
						getAssociatedEntityName()
				);
			}
			return getIdentifierType( session ).disassemble( id, session, owner );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Serializable disassemble(Object value, SessionFactoryImplementor sessionFactory) throws HibernateException {
		if ( value == null ) {
			return null;
		}
		else {
			// cache the actual id of the object, not the value of the
			// property-ref, which might not be initialized
			final Object id = getIdentifier( value, sessionFactory );
			if ( id == null ) {
				throw new AssertionFailure(
						"cannot cache a reference to an object with a null id: " +
								getAssociatedEntityName()
				);
			}
			return getIdentifierType( sessionFactory.getRuntimeMetamodels() ).disassemble( id, sessionFactory );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object assemble(Serializable oid, SharedSessionContractImplementor session, Object owner)
	throws HibernateException {
		//TODO: currently broken for unique-key references (does not detect
		//      change to unique key property of the associated object)
		final Object id = getIdentifierType(session).assemble(oid, session, null); //the owner of the association is not the owner of the id
		return id == null ? null : resolveIdentifier( id, session );
	}



}
