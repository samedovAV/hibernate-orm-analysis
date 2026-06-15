/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import static org.jboss.logging.Logger.Level.TRACE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Logging related to entity and collection mutations stemming from persistence-context events
 *
 * @author Steve Ebersole
 */
@SubSystemLogging(
		name = ModelMutationLogging.NAME,
		description = "Logging related to entity and collection mutations stemming from persistence context events"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90005101, max = 90005300)
@Internal
public interface ModelMutationLogging extends BasicLogger {

	String NAME = SubSystemLogging.BASE + ".jdbc.mutation";

	ModelMutationLogging MODEL_MUTATION_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), ModelMutationLogging.class, NAME, Locale.ROOT );

	@LogMessage(level = TRACE)
	@Message(id = 90005101, value = "Static SQL for entity: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void staticSqlForEntity(String entityName);

	@LogMessage(level = TRACE)
	@Message(id = 90005102, value = " Lazy select (%s) : %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void lazySelect(String fetchGroup, String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005103, value = " Version select: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void versionSelect(String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005104, value = " Insert (%s): %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void insertOperationSql(int index, String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005105, value = " Update (%s): %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateOperationSql(int index, String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005106, value = " Delete (%s): %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deleteOperationSql(int index, String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005107, value = "Static SQL for collection: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void staticSqlForCollection(String role);

	@LogMessage(level = TRACE)
	@Message(id = 90005108, value = " Row insert: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionRowInsert(String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005109, value = " Row update: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionRowUpdate(String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005110, value = " Row delete: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionRowDelete(String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005111, value = " One-shot delete: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionOneShotDelete(String sql);

	@LogMessage(level = TRACE)
	@Message(id = 90005112, value = "Performing delete (%s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performingDelete(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005113, value = "%s rows upsert-deleted from '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void upsertDeletedRowCount(int rowCount, String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005114, value = "Performing upsert of '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performingUpsert(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005115, value = "%s rows upserted in '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void upsertedRowCount(int rowCount, String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005116, value = "Updating collection rows: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatingCollectionRows(String rolePath, Object key);

	@LogMessage(level = TRACE)
	@Message(id = 90005117, value = "Updated %s collection rows: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updatedCollectionRows(int count, String rolePath, Object key);

	@LogMessage(level = TRACE)
	@Message(id = 90005118, value = "Performing update of '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performingUpdate(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005119, value = "Deleting removed collection rows: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deletingRemovedCollectionRows(String rolePath, Object key);

	@LogMessage(level = TRACE)
	@Message(id = 90005120, value = "No rows to delete")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noRowsToDelete();

	@LogMessage(level = TRACE)
	@Message(id = 90005121, value = "Done deleting %s collection rows: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void doneDeletingCollectionRows(int rowCount, String rolePath);

	@LogMessage(level = TRACE)
	@Message(id = 90005122, value = "Inserting collection rows: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void insertingNewCollectionRows(String rolePath, Object key);

	@LogMessage(level = TRACE)
	@Message(id = 90005124, value = "Done inserting %s collection rows: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void doneInsertingCollectionRows(int rowCount, String rolePath);

	@LogMessage(level = TRACE)
	@Message(id = 90005125, value = "Deleting collection: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removingCollection(String rolePath, Object key);

	@LogMessage(level = TRACE)
	@Message(id = 90005127, value = "Batch add for table '%s' (batch position %s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addBatchForTable(String tableName, int batchPosition);

	@LogMessage(level = TRACE)
	@Message(id = 90005128, value = "Skipping batch add for table '%s' (batch position %s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingAddBatchForTable(String tableName, int batchPosition);

	@LogMessage(level = TRACE)
	@Message(id = 90005130, value = "Upsert update altered no rows; performing insert into '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void upsertUpdateNoRowsPerformingInsert(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005131, value = "Skipping execution of secondary insert into '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingSecondaryInsert(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005132, value = "Skipping update of '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingUpdate(String tableName);

	@LogMessage(level = TRACE)
	@Message(id = 90005133, value = "No collection rows to insert: %s#%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noCollectionRowsToInsert(String rolePath, Object id);
}
