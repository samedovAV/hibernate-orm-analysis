/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Event class for {@link org.hibernate.Session#isDirty}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.Session#isDirty
 */
public class DirtyCheckEvent extends AbstractSessionEvent {
	private boolean dirty;

	public DirtyCheckEvent(EventSource source) {
		super(source);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDirty() {
		return dirty;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
