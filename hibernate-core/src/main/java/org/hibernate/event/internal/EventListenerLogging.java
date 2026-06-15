/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.internal;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.TRACE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Subsystem logging related to event listeners
 */
@SubSystemLogging(
		name = EventListenerLogging.NAME,
		description = "Logging related to event listeners and event listener services"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90060001, max = 90070000)
@Internal
public interface EventListenerLogging extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".event";

	EventListenerLogging EVENT_LISTENER_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), EventListenerLogging.class, NAME, Locale.ROOT );

	// Load

	@LogMessage(level = TRACE)
	@Message(id = 90060008, value = "Loading entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void loadingEntity(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060005, value = "Entity proxy found in session cache")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityProxyFoundInSessionCache(); // dupe of SessionLogging

	@LogMessage(level = DEBUG)
	@Message(id = 90060007, value = "Ignoring NO_PROXY to honor laziness")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringNoProxyToHonorLaziness(); // dupe of SessionLogging

	@LogMessage(level = TRACE)
	@Message(id = 90060050, value = "Creating new proxy for entity")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingNewProxy();

	@LogMessage(level = TRACE)
	@Message(id = 90060051, value = "Searching caches for entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolving(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060006, value = "Entity found in persistence context")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityResolvedInPersistenceContext();

	@LogMessage(level = TRACE)
	@Message(id = 90060052, value = "Entity found in second-level cache %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityResolvedInCache(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060053, value = "Entity not found in any cache, loading from datastore %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityNotResolvedInCache(String infoString);

	// Auto-flush

	@LogMessage(level = TRACE)
	@Message(id = 90060038, value = "Need to execute flush")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void needToExecuteFlush();

	@LogMessage(level = TRACE)
	@Message(id = 90060039, value = "No need to execute flush")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noNeedToExecuteFlush();

	// Flush

	@LogMessage(level = DEBUG)
	@Message(id = 90060071, value = "Flushed: %s insertions, %s updates, %s deletions to %s objects")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushedEntitiesSummary(int insertions, int updates, int deletions, int objects);

	@LogMessage(level = DEBUG)
	@Message(id = 90060072, value = "Flushed: %s (re)creations, %s updates, %s removals to %s collections")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushedCollectionsSummary(int recreations, int updates, int removals, int collections);

	@LogMessage(level = TRACE)
	@Message(id = 90060011, value = "Flushing session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushingSession();

	@LogMessage(level = TRACE)
	@Message(id = 90060012, value = "Processing flush-time cascades")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processingFlushTimeCascades();

	@LogMessage(level = TRACE)
	@Message(id = 90060013, value = "Dirty checking collections")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void dirtyCheckingCollections();

	@LogMessage(level = TRACE)
	@Message(id = 90060014, value = "Flushing entities and processing referenced collections")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushingEntitiesAndProcessingReferencedCollections();

	@LogMessage(level = TRACE)
	@Message(id = 90060015, value = "Processing unreferenced collections")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processingUnreferencedCollections();

	@LogMessage(level = TRACE)
	@Message(id = 90060016, value = "Scheduling collection removes, (re)creates, and updates")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void schedulingCollectionOperations();

	@LogMessage(level = TRACE)
	@Message(id = 90060017, value = "Executing flush")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void executingFlush();

	@LogMessage(level = TRACE)
	@Message(id = 90060018, value = "Post flush")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postFlush();

	// Flush entity

	@LogMessage(level = TRACE)
	@Message(id = 90060059, value = "Updating immutable, deleted entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatingImmutableDeletedEntity(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060060, value = "Updating non-modifiable, deleted entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatingNonModifiableDeletedEntity(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060061, value = "Updating deleted entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatingDeletedEntity(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060062, value = "Updating entity %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatingEntity(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060063, value = "Found dirty properties [%s] (%s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void foundDirtyProperties(String entityInfo, String dirtyPropertyNames);

	// Merge

	@LogMessage(level = TRACE)
	@Message(id = 90060019, value = "Ignoring uninitialized proxy in merge")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringUninitializedProxy();

	@LogMessage(level = TRACE)
	@Message(id = 90060020, value = "Ignoring uninitialized enhanced proxy in merge")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringUninitializedEnhancedProxy();

	@LogMessage(level = TRACE)
	@Message(id = 90060021, value = "Already in merge process")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyInMergeProcess();

	@LogMessage(level = TRACE)
	@Message(id = 90060022, value = "Already in merge context; adding to merge process")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyInMergeContext();

	@LogMessage(level = TRACE)
	@Message(id = 90060023, value = "Ignoring persistent instance %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringPersistentInstance(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060024, value = "Merging transient instance %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void mergingTransientInstance(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060025, value = "Merging detached instance %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void mergingDetachedInstance(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060026, value = "Detached instance not found in database")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void detachedInstanceNotFoundInDatabase();

	// Initialize collection

	@LogMessage(level = TRACE)
	@Message(id = 90060033, value = "Initializing collection %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initializingCollection(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060034, value = "Collection initialized from cache")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionInitializedFromCache();

	@LogMessage(level = TRACE)
	@Message(id = 90060035, value = "Collection not cached")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionNotCached();

	@LogMessage(level = TRACE)
	@Message(id = 90060036, value = "Collection initialized")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionInitialized();

	@LogMessage(level = TRACE)
	@Message(id = 90060037, value = "Disregarding cached version (if any) of collection due to enabled filters")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void disregardingCachedVersionDueToEnabledFilters();

	// Persist

	@LogMessage(level = TRACE)
	@Message(id = 90060058, value = "Persisting %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void persisting(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060040, value = "Persisting transient instance")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void persistingTransientInstance();

	@LogMessage(level = TRACE)
	@Message(id = 90060041, value = "Unscheduling entity deletion %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unschedulingEntityDeletion(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060076, value ="Generated identifier [%s] using generator '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void generatedId(String loggableString, String name);

	// Refresh

	@LogMessage(level = TRACE)
	@Message(id = 90060044, value = "Refreshing %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void refreshing(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060043, value = "Refreshing transient %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void refreshingTransient(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060042, value = "Already refreshed")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyRefreshed();

	// Delete

	@LogMessage(level = TRACE)
	@Message(id = 90060047, value = "Deleting %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deleting(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060045, value = "Deleted entity was not associated with current session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deletedEntityNotAssociatedWithSession();

	@LogMessage(level = TRACE)
	@Message(id = 90060046, value = "Already handled transient entity; skipping")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyHandledTransient();

	@LogMessage(level = DEBUG)
	@Message(id = 90060001, value = "Flushing and evicting managed instance of type [%s] before removing detached instance with same id")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushAndEvictOnRemove(String entityName);

	@LogMessage(level = DEBUG)
	@Message(id = 90060002, value = "Handling transient entity in delete processing")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handlingTransientEntity();

	@LogMessage(level = TRACE)
	@Message(id = 90060009, value = "Deleting a persistent instance")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deletingPersistentInstance();

	@LogMessage(level = TRACE)
	@Message(id = 90060010, value = "Persistent instance was already deleted or scheduled for deletion")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyDeleted();

	// Evict

	@LogMessage(level = TRACE)
	@Message(id = 90060048, value = "Evicting %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evicting(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060049, value = "Evicting collection %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingCollection(String collectionInfo);

	// Lock

	@LogMessage(level = TRACE)
	@Message(id = 90060064, value = "Reassociating transient instance: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void reassociatingTransientInstance(String infoString);

	// EntityState

//	@LogMessage(level = TRACE)
//	@Message(id = 90060054, value = "Persistent instance of: %s")
//	void persistentInstance(String loggableName);
//
//	@LogMessage(level = TRACE)
//	@Message(id = 90060055, value = "Deleted instance of: %s")
//	void deletedInstance(String loggableName);
//
//	@LogMessage(level = TRACE)
//	@Message(id = 90060056, value = "Transient instance of: %s")
//	void transientInstance(String loggableName);
//
//	@LogMessage(level = TRACE)
//	@Message(id = 90060057, value = "Detached instance of: %s")
//	void detachedInstance(String loggableName);

	// Reattach / wrap

	@LogMessage(level = TRACE)
	@Message(id = 90060066, value = "Collection dereferenced while transient %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionDereferencedWhileTransient(String infoString);

	@LogMessage(level = TRACE)
	@Message(id = 90060067, value = "Wrapped collection in role: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void wrappedCollectionInRole(String role);
}
