/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.tracker;

import java.util.Arrays;

import org.hibernate.engine.spi.CompositeOwner;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * small low memory class to keep references to composite owners
 *
 * @author Ståle W. Pedersen
 */
public final class CompositeOwnerTracker {

	private String[] names;
	private CompositeOwner[] owners;

	public CompositeOwnerTracker() {
		names = new String[0];
		owners = new CompositeOwner[0];
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void add(String name, CompositeOwner owner) {
		for ( int i = 0; i < names.length; i++ ) {
			if ( names[i].equals( name ) ) {
				owners[i] = owner;
				return;
			}
		}
		names = Arrays.copyOf( names, names.length + 1 );
		names[names.length - 1] = name;
		owners = Arrays.copyOf( owners, owners.length + 1 );
		owners[owners.length - 1] = owner;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void callOwner(String fieldName) {
		for ( int i = 0; i < owners.length ; i++ ) {
			if ( owners[i] != null ) {
				owners[i].$$_hibernate_trackChange( names[i] + fieldName );
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void removeOwner(String name) {
		for ( int i = 0; i < names.length; i++ ) {
			if ( name.equals( names[i] ) ) {

				final String[] newNames = Arrays.copyOf( names, names.length - 1 );
				System.arraycopy( names, i + 1, newNames, i, newNames.length - i);
				names = newNames;

				final CompositeOwner[] newOwners = Arrays.copyOf( owners, owners.length - 1 );
				System.arraycopy( owners, i + 1, newOwners, i, newOwners.length - i);
				owners = newOwners;

				return;
			}
		}
	}

}
