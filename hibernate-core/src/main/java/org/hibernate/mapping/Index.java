/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Exportable;
import org.hibernate.dialect.Dialect;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static org.hibernate.internal.util.StringHelper.qualify;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing an {@linkplain jakarta.persistence.Index index} on a relational database table.
 * <p>
 * We regularize the semantics of unique constraints on nullable columns: two null values are not considered to be
 * "equal" for the purpose of determining uniqueness, just as specified by ANSI SQL and common sense.
 *
 * @author Gavin King
 */
public class Index implements Exportable, Serializable {
	private Identifier name;
	private Table table;
	private boolean unique;
	private String type = "";
	private String using = "";
	private String options = "";
	private final java.util.List<Selectable> selectables = new ArrayList<>();
	private final java.util.Map<Selectable, String> selectableOrderMap = new HashMap<>();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTable(Table table) {
		this.table = table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnique() {
		return unique;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getType() {
		return type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setType(String type) {
		this.type = type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUsing() {
		return using;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUsing(String using) {
		this.using = using;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOptions() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOptions(String options) {
		this.options = options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getColumnSpan() {
		return selectables.size();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Selectable> getSelectables() {
		return unmodifiableList( selectables );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<Selectable, String> getSelectableOrderMap() {
		return unmodifiableMap( selectableOrderMap );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addColumn(Selectable selectable) {
		if ( !selectables.contains( selectable ) ) {
			selectables.add( selectable );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addColumn(Selectable selectable, String order) {
		addColumn( selectable );
		if ( isNotEmpty( order ) ) {
			selectableOrderMap.put( selectable, order );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name == null ? null : name.getText();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setName(String name) {
		this.name = Identifier.toIdentifier( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQuotedName(Dialect dialect) {
		return name == null ? null : name.render( dialect );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getClass().getSimpleName() + "(" + getName() + ")";
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getExportIdentifier() {
		return qualify( getTable().getExportIdentifier(), "IDX-" + getName() );
	}
}
