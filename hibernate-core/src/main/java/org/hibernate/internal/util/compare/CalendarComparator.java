/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.compare;

import java.util.Calendar;
import java.util.Comparator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class CalendarComparator implements Comparator<Calendar> {
	public static final CalendarComparator INSTANCE = new CalendarComparator();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int compare(Calendar x, Calendar y) {
		if ( x.before( y ) ) {
			return -1;
		}
		if ( x.after( y ) ) {
			return 1;
		}
		return 0;
	}
}
