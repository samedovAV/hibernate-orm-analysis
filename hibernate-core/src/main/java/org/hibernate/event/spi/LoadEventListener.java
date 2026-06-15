/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of load events generated from a session.
 *
 * @author Steve Ebersole
 */
public interface LoadEventListener {

	/**
	 * Handle the given load event.
	 *
	 * @param event The load event to be handled.
	 *
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onLoad(LoadEvent event, LoadType loadType) throws HibernateException;

	/**
	 * Normal {@linkplain org.hibernate.Session#find} (and by extension
	 * {@linkplain org.hibernate.Session#get}) handling.
	 */
	LoadType FIND = new LoadType( "FIND" )
			.setAllowNulls( true )
			.setAllowProxyCreation( true )
			.setCheckDeleted( true )
			.setNakedEntityReturned( false );

	LoadType RELOAD = new LoadType( "RELOAD" )
			.setAllowNulls( false )
			.setAllowProxyCreation( false )
			.setCheckDeleted( true )
			.setNakedEntityReturned( false );

	LoadType GET = new LoadType( "GET" )
			.setAllowNulls( true )
			.setAllowProxyCreation( false )
			.setCheckDeleted( true )
			.setNakedEntityReturned( false );

	LoadType LOAD = new LoadType( "LOAD" )
			.setAllowNulls( false )
			.setAllowProxyCreation( true )
			.setCheckDeleted( true )
			.setNakedEntityReturned( false );

	LoadType IMMEDIATE_LOAD = new LoadType( "IMMEDIATE_LOAD" )
			.setAllowNulls( true )
			.setAllowProxyCreation( false )
			.setCheckDeleted( false )
			.setNakedEntityReturned( true );

	LoadType INTERNAL_LOAD_EAGER = new LoadType( "INTERNAL_LOAD_EAGER" )
			.setAllowNulls( false )
			.setAllowProxyCreation( false )
			.setCheckDeleted( false )
			.setNakedEntityReturned( false );

	LoadType INTERNAL_LOAD_LAZY = new LoadType( "INTERNAL_LOAD_LAZY" )
			.setAllowNulls( false )
			.setAllowProxyCreation( true )
			.setCheckDeleted( false )
			.setNakedEntityReturned( false );

	LoadType INTERNAL_LOAD_NULLABLE = new LoadType( "INTERNAL_LOAD_NULLABLE" )
			.setAllowNulls( true )
			.setAllowProxyCreation( false )
			.setCheckDeleted( false )
			.setNakedEntityReturned( false );

	final class LoadType {
		private final String name;

		private boolean nakedEntityReturned;
		private boolean allowNulls;
		private boolean checkDeleted;
		private boolean allowProxyCreation;

		private LoadType(String name) {
			this.name = name;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isAllowNulls() {
			return allowNulls;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private LoadType setAllowNulls(boolean allowNulls) {
			this.allowNulls = allowNulls;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isNakedEntityReturned() {
			return nakedEntityReturned;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private LoadType setNakedEntityReturned(boolean immediateLoad) {
			this.nakedEntityReturned = immediateLoad;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isCheckDeleted() {
			return checkDeleted;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private LoadType setCheckDeleted(boolean checkDeleted) {
			this.checkDeleted = checkDeleted;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isAllowProxyCreation() {
			return allowProxyCreation;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private LoadType setAllowProxyCreation(boolean allowProxyCreation) {
			this.allowProxyCreation = allowProxyCreation;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getName() {
			return name;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return name;
		}
	}
}
