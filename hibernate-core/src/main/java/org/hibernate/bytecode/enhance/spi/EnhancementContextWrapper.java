/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi;

import jakarta.persistence.metamodel.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class EnhancementContextWrapper implements EnhancementContext {

	private final ClassLoader loadingClassloader;
	private final EnhancementContext wrappedContext;

	public EnhancementContextWrapper(EnhancementContext wrappedContext, ClassLoader loadingClassloader) {
		this.wrappedContext = wrappedContext;
		this.loadingClassloader = loadingClassloader;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassLoader getLoadingClassLoader() {
		return loadingClassloader;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEntityClass(UnloadedClass classDescriptor) {
		return wrappedContext.isEntityClass( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCompositeClass(UnloadedClass classDescriptor) {
		return wrappedContext.isCompositeClass( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMappedSuperclassClass(UnloadedClass classDescriptor) {
		return wrappedContext.isMappedSuperclassClass( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean doBiDirectionalAssociationManagement(UnloadedField field) {
		return wrappedContext.doBiDirectionalAssociationManagement( field );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean doDirtyCheckingInline(UnloadedClass classDescriptor) {
		return wrappedContext.doDirtyCheckingInline( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean doExtendedEnhancement(UnloadedClass classDescriptor) {
		return wrappedContext.doExtendedEnhancement( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasLazyLoadableAttributes(UnloadedClass classDescriptor) {
		return wrappedContext.hasLazyLoadableAttributes( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPersistentField(UnloadedField ctField) {
		return wrappedContext.isPersistentField( ctField );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public UnloadedField[] order(UnloadedField[] persistentFields) {
		return wrappedContext.order( persistentFields );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isLazyLoadable(UnloadedField field) {
		return wrappedContext.isLazyLoadable( field );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMappedCollection(UnloadedField field) {
		return wrappedContext.isMappedCollection( field );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isDiscoveredType(UnloadedClass classDescriptor) {
		return wrappedContext.isDiscoveredType( classDescriptor );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerDiscoveredType(UnloadedClass classDescriptor, Type.PersistenceType type) {
		wrappedContext.registerDiscoveredType( classDescriptor, type );
	}
}
