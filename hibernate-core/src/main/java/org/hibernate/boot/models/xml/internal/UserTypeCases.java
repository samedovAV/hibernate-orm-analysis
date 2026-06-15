/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.internal;

import org.hibernate.boot.jaxb.mapping.spi.JaxbUserTypeImpl;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.models.spi.MutableMemberDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface UserTypeCases {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleNone(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleCharacter(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleString(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleByte(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleBoolean(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleShort(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleInteger(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleLong(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleDouble(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleFloat(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleBigInteger(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleBigDecimal(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleUuid(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleUrl(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleInetAddress(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleCurrency(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleLocale(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleClass(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleBlob(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleClob(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleNClob(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleInstant(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleDuration(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleYear(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleLocalDateTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleLocalDate(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleLocalTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleZonedDateTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleOffsetDateTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleOffsetTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleZoneId(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleZoneOffset(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleTimestamp(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleDate(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleTime(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleCalendar(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleTimeZone(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleGeneral(JaxbUserTypeImpl jaxbType, MutableMemberDetails memberDetails, XmlDocumentContext xmlDocumentContext);
}
