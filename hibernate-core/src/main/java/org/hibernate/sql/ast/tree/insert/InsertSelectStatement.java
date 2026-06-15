/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.insert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.AbstractMutationStatement;
import org.hibernate.sql.ast.tree.cte.CteContainer;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.model.MutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * todo (6.2) - Would much prefer to split insert-values and
 * 		insert-select into individual contracts - something like
 * 		`InsertStatement` and `InsertSelectStatement` e.g.
 * 		Would help alleviate much of the duplication in handling
 * 		between inserts from SQM and those from model mutation
 *
 * @author Steve Ebersole
 */
public class InsertSelectStatement extends AbstractMutationStatement implements InsertStatement {

	public static final String DEFAULT_ALIAS = "to_insert_";
	private List<ColumnReference> targetColumnReferences;
	private QueryPart sourceSelectStatement;
	private List<Values> valuesList = new ArrayList<>();
	private ConflictClause conflictClause;

	public InsertSelectStatement(NamedTableReference targetTable, MutationTarget<?,?> mutationTarget) {
		this( null, targetTable, mutationTarget, Collections.emptyList() );
	}

	public InsertSelectStatement(NamedTableReference targetTable, List<ColumnReference> returningColumns) {
		this( null, targetTable, null, returningColumns );
	}

	public InsertSelectStatement(
			CteContainer cteContainer,
			NamedTableReference targetTable,
			MutationTarget<?,?> mutationTarget,
			List<ColumnReference> returningColumns) {
		super( cteContainer, targetTable, mutationTarget, returningColumns );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnReference> getTargetColumns() {
		return targetColumnReferences == null ? Collections.emptyList() : targetColumnReferences;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEachTargetColumn(BiConsumer<Integer, ColumnReference> consumer) {
		if ( targetColumnReferences == null ) {
			return;
		}

		for ( int i = 0; i < targetColumnReferences.size(); i++ ) {
			consumer.accept( i, targetColumnReferences.get( i ) );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addTargetColumnReference(ColumnReference reference) {
		if ( targetColumnReferences == null ) {
			targetColumnReferences = new ArrayList<>();
		}
		targetColumnReferences.add( reference );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addTargetColumnReferences(ColumnReference... references) {
		if ( targetColumnReferences == null ) {
			targetColumnReferences = new ArrayList<>();
		}

		Collections.addAll( this.targetColumnReferences, references );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addTargetColumnReferences(List<ColumnReference> references) {
		if ( targetColumnReferences == null ) {
			targetColumnReferences = new ArrayList<>();
		}

		this.targetColumnReferences.addAll( references );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryPart getSourceSelectStatement() {
		return sourceSelectStatement;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSourceSelectStatement(QueryPart sourceSelectStatement) {
		this.sourceSelectStatement = sourceSelectStatement;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Values> getValuesList() {
		return valuesList;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setValuesList(List<Values> valuesList) {
		this.valuesList = valuesList;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ConflictClause getConflictClause() {
		return conflictClause;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setConflictClause(ConflictClause conflictClause) {
		this.conflictClause = conflictClause;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitInsertStatement( this );
	}
}
