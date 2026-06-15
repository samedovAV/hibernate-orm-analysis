/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.collection;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.MappingException;
import org.hibernate.action.internal.CollectionRecreateAction;
import org.hibernate.action.internal.CollectionRemoveAction;
import org.hibernate.action.internal.CollectionUpdateAction;
import org.hibernate.action.internal.QueuedOperationCollectionAction;
import org.hibernate.action.queue.spi.decompose.DecompositionContext;
import org.hibernate.action.queue.spi.plan.FlushOperation;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.cache.spi.entry.CacheEntryStructure;
import org.hibernate.collection.spi.CollectionSemantics;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.Restrictable;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.persister.collection.mutation.RowMutationOperations;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A strategy for persisting a mapped collection role. A
 * {@code CollectionPersister} orchestrates rendering of SQL statements
 * corresponding to basic lifecycle events, including {@code insert},
 * {@code update}, and {@code delete} statements, and their execution via
 * JDBC.
 * <p>
 * Concrete implementations of this interface handle
 * {@linkplain OneToManyPersister one-to-many} and
 * {@linkplain BasicCollectionPersister many-to-many} association
 * cardinalities, and to a certain extent abstract the details of those
 * mappings from collaborators.
 * <p>
 * Note that an instance of {@link PersistentCollection} may change roles.
 * This object implements persistence of a collection instance while the
 * instance is referenced in a particular role.
 * <p>
 * This interface defines a contract between the persistence strategy and
 * the actual {@linkplain PersistentCollection persistent collection framework}
 * and {@link org.hibernate.engine.spi.SessionImplementor session}. It does
 * not define operations that are required for querying collections, nor
 * for loading by outer join. Implementations are highly coupled to the
 * {@link PersistentCollection} hierarchy, since double dispatch is used to
 * load and update collection elements.
 * <p>
 * Unless a custom {@link org.hibernate.persister.spi.PersisterFactory} is
 * used, it is expected that implementations of {@link CollectionPersister}
 * define a constructor accepting the following arguments:
 * <ol>
 *     <li>
 *         {@link org.hibernate.mapping.Collection} - The metadata about
 *         the collection to be handled by the persister,
 *     </li>
 *     <li>
 *         {@link CollectionDataAccess} - the second level caching strategy
 *         for this collection, and
 *     </li>
 *     <li>
 *         {@link RuntimeModelCreationContext} - access to additional
 *         information useful while constructing the persister.
 *     </li>
 * </ol>
 *
 * @see PersistentCollection
 * @see PluralAttributeMapping
 *
 * @author Gavin King
 */
public interface CollectionPersister extends Restrictable {
	/**
	 * The NavigableRole for this collection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigableRole getNavigableRole();

	/**
	 * Get the name of this collection role (the fully qualified class name,
	 * extended by a "property path")
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getRole() {
		return getNavigableRole().getFullPath();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PluralAttributeMapping getAttributeMapping() {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	/**
	 * Decomposes a collection recreate action into planned operations.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void decompose(
			CollectionRecreateAction action,
			int ordinalBase,
			SharedSessionContractImplementor session,
			DecompositionContext decompositionContext,
			Consumer<FlushOperation> operationConsumer);

	/**
	 * Removes the collection:<ul>
	 *     <li>
	 *         For collections with a collection-table, this will execute a DELETE based
	 *         on the {@linkplain org.hibernate.engine.spi.CollectionKey collection-key}
	 *     </li>
	 *     <li>
	 *         For one-to-many collections, this executes an UPDATE to unset the collection-key
	 *         on the association table
	 *     </li>
	 * </ul>
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void decompose(
			CollectionRemoveAction action,
			int ordinalBase,
			SharedSessionContractImplementor session,
			DecompositionContext decompositionContext,
			Consumer<FlushOperation> operationConsumer);

	/**
	 * Decomposes a collection update action into planned operations
	*/
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void decompose(
			CollectionUpdateAction action,
			int ordinalBase,
			SharedSessionContractImplementor session,
			DecompositionContext decompositionContext,
			Consumer<FlushOperation> operationConsumer);

	/**
	 * Decomposes a queued collection operation action into planned operations.
	 * <p>
	 * Queued collection operations often have no SQL work of their own, but still need
	 * flush-time lifecycle cleanup after the plan reaches the action.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void decompose(
			QueuedOperationCollectionAction action,
			int ordinalBase,
			SharedSessionContractImplementor session,
			Consumer<FlushOperation> operationConsumer);

	/**
	 * Get the persister of the entity that "owns" this collection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getOwnerEntityPersister();

	/**
	 * Initialize the given collection with the given key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initialize(Object key, SharedSessionContractImplementor session) throws HibernateException;

	/**
	 * Is this collection role cacheable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasCache();

	/**
	 * Whether {@link #remove(Object, SharedSessionContractImplementor)} might actually do something,
	 * or if it is definitely a no-op.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean needsRemove() {
		return true;
	}

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RowMutationOperations getRowMutationOperations();

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isRowInsertEnabled();

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isRowDeleteEnabled();

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean[] getIndexColumnIsSettable();

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean[] getElementColumnIsSettable();

	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	UnaryOperator<Object> getIndexIncrementer();

	/**
	 * Access to the collection's cache region
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionDataAccess getCacheAccessStrategy();

	/**
	 * Get the structure used to store data into the collection's {@linkplain #getCacheAccessStrategy() cache region}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheEntryStructure getCacheEntryStructure();

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean useShallowQueryCacheLayout();

	/**
	 * Return the element class of an array, or null otherwise
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getElementClass();

	/**
	 * Is this an array of primitive values?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPrimitiveArray();
	/**
	 * Is this an array?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isArray();
	/**
	 * Is this a one-to-many association?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOneToMany();
	/**
	 * Is this a many-to-many association?  Note that this is mainly
	 * a convenience feature as the single persister does not
	 * contain all the information needed to handle a many-to-many
	 * itself, as internally it is looked at as two many-to-ones.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isManyToMany();

	/**
	 * Is this an "indexed" collection? (list or map)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasIndex();
	/**
	 * Is this collection lazily initialized?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isLazy();
	/**
	 * Is this collection "inverse", so state changes are not
	 * propagated to the database.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInverse();
	/**
	 * Completely remove the persistent state of the collection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void remove(Object id, SharedSessionContractImplementor session);

	/**
	 * (Re)create the collection's persistent state
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void recreate(
			PersistentCollection<?> collection,
			Object key,
			SharedSessionContractImplementor session);

	/**
	 * Delete the persistent state of any elements that were removed from
	 * the collection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deleteRows(
			PersistentCollection<?> collection,
			Object key,
			SharedSessionContractImplementor session);

	/**
	 * Update the persistent state of any elements that were modified
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateRows(
			PersistentCollection<?> collection,
			Object key,
			SharedSessionContractImplementor session);

	/**
	 * Insert the persistent state of any new collection elements
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void insertRows(
			PersistentCollection<?> collection,
			Object key,
			SharedSessionContractImplementor session);

	/**
	 * Process queued operations within the PersistentCollection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processQueuedOps(
			PersistentCollection<?> collection,
			Object key,
			SharedSessionContractImplementor session);

	/**
	 * Get the surrogate key generation strategy (optional operation)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BeforeExecutionGenerator getGenerator();

	/**
	 * Does this collection implement "orphan delete"?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasOrphanDelete();

	/**
	 * Is this an ordered collection? (An ordered collection is
	 * ordered by the initialization operation, not by sorting
	 * that happens in memory, as in the case of a sorted collection.)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasOrdering();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasManyToManyOrdering();

	/**
	 * Get the "space" that holds the persistent state
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getCollectionSpaces();

	/**
	 * Is cascade delete handled by the database-level
	 * foreign key constraint definition?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCascadeDeleteEnabled();

	/**
	 * Does this collection cause version increment of the
	 * owning entity?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isVersioned();

	/**
	 * Can the elements of this collection change?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	//public boolean isSubselectLoadable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postInstantiate() throws MappingException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getFactory();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAffectedByEnabledFilters(SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByEnabledFilters(LoadQueryInfluencers influencers) {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByEnabledFilters(LoadQueryInfluencers influencers, boolean onlyApplyForLoadByKeyFilters) {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByEnabledFilters(
			Set<ManagedMappingType> visitedTypes,
			LoadQueryInfluencers influencers,
			boolean onlyApplyForLoadByKey) {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByEntityGraph(LoadQueryInfluencers influencers) {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByEnabledFetchProfiles(LoadQueryInfluencers influencers) {
		throw new UnsupportedOperationException( "CollectionPersister used for [" + getRole() + "] does not support SQL AST" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isExtraLazy() {
		return false;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getSize(Object key, SharedSessionContractImplementor session);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean indexExists(Object key, Object index, SharedSessionContractImplementor session);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean elementExists(Object key, Object element, SharedSessionContractImplementor session);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getElementByIndex(Object key, Object index, SharedSessionContractImplementor session, Object owner);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getBatchSize() {
		return -1;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isBatchLoadable() {
		return getBatchSize() > 1;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSubselectLoadable() {
		return false;
	}

	/**
	 * @return the name of the property this collection is mapped by
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getMappedByProperty();

	/**
	 * For sorted collections, the comparator to use.  Non-parameterized
	 * because for SORTED_SET the elements are compared but for SORTED_MAP the
	 * keys are compared
	 *
	 * @see CollectionClassification#SORTED_MAP
	 * @see CollectionClassification#SORTED_SET
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Comparator<?> getSortingComparator();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// mapping model

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionSemantics<?,?> getCollectionSemantics();


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyBaseManyToManyRestrictions(
			Consumer<Predicate> predicateConsumer,
			TableGroup tableGroup,
			boolean useQualifier,
			Map<String, Filter> enabledFilters,
			Set<String> treatAsDeclarations,
			SqlAstCreationState creationState);



	/**
	 * Generates the collection's key column aliases, based on the given
	 * suffix.
	 *
	 * @param suffix The suffix to use in the key column alias generation.
	 * @return The key column aliases.
	 *
	 * @deprecated Read-by-position makes this irrelevant.  Currently still used
	 * by {@link org.hibernate.query.sql.internal.SQLQueryParser}
	 */
	@Deprecated( since = "6", forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getKeyColumnAliases(String suffix);

	/**
	 * Generates the collection's index column aliases, based on the given
	 * suffix.
	 *
	 * @param suffix The suffix to use in the index column alias generation.
	 * @return The key column aliases, or null if not indexed.
	 *
	 * @deprecated Read-by-position makes this irrelevant.  Currently still used
	 * by {@link org.hibernate.query.sql.internal.SQLQueryParser}
	 */
	@Deprecated( since = "6", forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getIndexColumnAliases(String suffix);

	/**
	 * Generates the collection's element column aliases, based on the given
	 * suffix.
	 *
	 * @param suffix The suffix to use in the element column alias generation.
	 * @return The key column aliases.
	 *
	 * @deprecated Read-by-position makes this irrelevant.  Currently still used
	 * by {@link org.hibernate.query.sql.internal.SQLQueryParser}
	 */
	@Deprecated( since = "6", forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getElementColumnAliases(String suffix);

	/**
	 * Generates the collection's identifier column aliases, based on the given
	 * suffix.
	 *
	 * @param suffix The suffix to use in the key column alias generation.
	 * @return The key column aliases.
	 *
	 * @deprecated Read-by-position makes this irrelevant.  Currently still used
	 * by {@link org.hibernate.query.sql.internal.SQLQueryParser}
	 */
	@Deprecated( since = "6", forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIdentifierColumnAlias(String suffix);

	/**
	 * Get the associated {@code Type}
	 *
	 * @deprecated Hibernate is moving away from {@link Type}.  Corresponding
	 * {@linkplain org.hibernate.metamodel.mapping mapping metamodel} calls should
	 * be used instead - here (generally), {@link PluralAttributeMapping}
	 */
	@Deprecated( forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionType getCollectionType();

	/**
	 * Get the "key" type (the type of the foreign key)
	 *
	 * @deprecated Hibernate is moving away from {@link Type}.  Corresponding
	 * {@linkplain org.hibernate.metamodel.mapping mapping metamodel} calls should
	 * be used instead - here, {@link PluralAttributeMapping#getKeyDescriptor()}
	 */
	@Deprecated( forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getKeyType();

	/**
	 * Get the "index" type for a list or map (optional operation)
	 *
	 * @deprecated Hibernate is moving away from {@link Type}.  Corresponding
	 * {@linkplain org.hibernate.metamodel.mapping mapping metamodel} calls should
	 * be used instead - here, {@link PluralAttributeMapping#getIndexDescriptor()}
	 */
	@Deprecated( forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getIndexType();

	/**
	 * Get the "element" type
	 *
	 * @deprecated Hibernate is moving away from {@link Type}.  Corresponding
	 * {@linkplain org.hibernate.metamodel.mapping mapping metamodel} calls should
	 * be used instead - here, {@link PluralAttributeMapping#getElementDescriptor()}
	 */
	@Deprecated( forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getElementType();

	/**
	 * Get the type of the surrogate key
	 *
	 * @deprecated Hibernate is moving away from {@link Type}.  Corresponding
	 * {@linkplain org.hibernate.metamodel.mapping mapping metamodel} calls should
	 * be used instead - here, {@link PluralAttributeMapping#getIdentifierDescriptor()}
	 */
	@Deprecated( forRemoval = true )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getIdentifierType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIdentifierColumnName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableName();

	/**
	 * Generate a list of collection index and element columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String selectFragment(String alias, String columnSuffix);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getCollectionPropertyColumnAliases(String propertyName, String string);

	/**
	 * Get the persister of the element class, if this is a
	 * collection of entities (optional operation).  Note that
	 * for a one-to-many association, the returned persister
	 * must be {@code OuterJoinLoadable}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getElementPersister();
}
