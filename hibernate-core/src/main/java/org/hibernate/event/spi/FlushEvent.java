/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Event class for stateful session flush.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.Session#flush
 */
public class FlushEvent extends AbstractSessionEvent {
	private int numberOfEntitiesProcessed;
	private int numberOfCollectionsProcessed;

	public FlushEvent(EventSource source) {
		super( source );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfEntitiesProcessed() {
		return numberOfEntitiesProcessed;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNumberOfEntitiesProcessed(int numberOfEntitiesProcessed) {
		this.numberOfEntitiesProcessed = numberOfEntitiesProcessed;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfCollectionsProcessed() {
		return numberOfCollectionsProcessed;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNumberOfCollectionsProcessed(int numberOfCollectionsProcessed) {
		this.numberOfCollectionsProcessed = numberOfCollectionsProcessed;
	}
}
