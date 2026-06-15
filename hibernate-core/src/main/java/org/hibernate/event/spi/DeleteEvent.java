/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Event class for {@link org.hibernate.Session#remove}.
 *
 * @apiNote This class predates JPA, and today should
 *          really be named {@code RemoveEvent}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.Session#remove
 */
public class DeleteEvent extends AbstractSessionEvent {
	private final Object object;
	private String entityName;
	private boolean cascadeDeleteEnabled;
	// TODO: The removeOrphan concept is a temporary "hack" for HHH-6484.
	//       This should be removed once action/task ordering is improved.
	private boolean orphanRemovalBeforeUpdates;

	public DeleteEvent(Object object, EventSource source) {
		super(source);
		if (object == null) {
			throw new IllegalArgumentException( "Entity may not be null" );
		}
		this.object = object;
	}

	public DeleteEvent(String entityName, Object object, EventSource source) {
		this(object, source);
		this.entityName = entityName;
	}

	public DeleteEvent(String entityName, Object object, boolean cascadeDeleteEnabled, EventSource source) {
		this(object, source);
		this.entityName = entityName;
		this.cascadeDeleteEnabled = cascadeDeleteEnabled;
	}

	public DeleteEvent(String entityName, Object object, boolean cascadeDeleteEnabled,
			boolean orphanRemovalBeforeUpdates, EventSource source) {
		this(object, source);
		this.entityName = entityName;
		this.cascadeDeleteEnabled = cascadeDeleteEnabled;
		this.orphanRemovalBeforeUpdates = orphanRemovalBeforeUpdates;
	}

	/**
	 * Returns the encapsulated entity to be deleted.
	 *
	 * @return The entity to be deleted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getObject() {
		return object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCascadeDeleteEnabled() {
		return cascadeDeleteEnabled;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOrphanRemovalBeforeUpdates() {
		return orphanRemovalBeforeUpdates;
	}

}
