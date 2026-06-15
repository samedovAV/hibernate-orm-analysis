/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.internal;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.function.Supplier;

import org.hibernate.annotations.TenantId;
import org.hibernate.models.internal.MutableClassDetailsRegistry;
import org.hibernate.models.internal.jdk.JdkClassDetails;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.ClassDetailsRegistry;
import org.hibernate.models.spi.RegistryPrimer;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public class ModelsHelper {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static void preFillRegistries(RegistryPrimer.Contributions contributions, ModelsContext buildingContext) {
		OrmAnnotationHelper.forEachOrmAnnotation( contributions::registerAnnotation );

		registerPrimitive( boolean.class, buildingContext );
		registerPrimitive( byte.class, buildingContext );
		registerPrimitive( short.class, buildingContext );
		registerPrimitive( int.class, buildingContext );
		registerPrimitive( long.class, buildingContext );
		registerPrimitive( double.class, buildingContext );
		registerPrimitive( float.class, buildingContext );
		registerPrimitive( char.class, buildingContext );
		registerPrimitive( Blob.class, buildingContext );
		registerPrimitive( Clob.class, buildingContext );
		registerPrimitive( NClob.class, buildingContext );
		registerPrimitive( java.sql.Date.class, buildingContext );
		registerPrimitive( java.sql.Time.class, buildingContext );
		registerPrimitive( java.sql.Timestamp.class, buildingContext );

		buildingContext.getAnnotationDescriptorRegistry().getDescriptor( TenantId.class );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static void registerPrimitive(Class<?> theClass, ModelsContext buildingContext) {
		buildingContext.getClassDetailsRegistry()
				.as( MutableClassDetailsRegistry.class )
				.addClassDetails( new JdkClassDetails( theClass, buildingContext ) );

	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ClassDetails resolveClassDetails(
			String className,
			ClassDetailsRegistry classDetailsRegistry,
			Supplier<ClassDetails> classDetailsSupplier) {
		var classDetails = classDetailsRegistry.findClassDetails( className );
		if ( classDetails != null ) {
			return classDetails;
		}
		else {
			classDetails = classDetailsSupplier.get();
			classDetailsRegistry.as( MutableClassDetailsRegistry.class )
					.addClassDetails( className, classDetails );
			return classDetails;
		}
	}
}
