/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.boot.archive.spi.InputStreamAccess;
import org.hibernate.boot.jaxb.internal.FileXmlSource;
import org.hibernate.boot.jaxb.internal.InputStreamAccessXmlSource;
import org.hibernate.boot.jaxb.internal.InputStreamXmlSource;
import org.hibernate.boot.jaxb.internal.MappingBinder;
import org.hibernate.boot.jaxb.internal.UrlXmlSource;
import org.hibernate.boot.jaxb.spi.Binding;
import org.hibernate.boot.jaxb.spi.JaxbBindableMappingDescriptor;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Holds the XML binder and a classloader used for binding mappings, as well
 * as access to methods to perform binding of sources of mapping XML.
 *
 * @apiNote This class is very poorly named.
 *
 * @author Steve Ebersole
 */
public class XmlMappingBinderAccess {
	private final ClassLoaderService classLoaderService;
	private final MappingBinder mappingBinder;

	public XmlMappingBinderAccess(ServiceRegistry serviceRegistry) {
		this.classLoaderService = serviceRegistry.getService( ClassLoaderService.class );
		this.mappingBinder = new MappingBinder( serviceRegistry );
	}

	public XmlMappingBinderAccess(ServiceRegistry serviceRegistry, Function<String, Object> configAccess) {
		this.classLoaderService = serviceRegistry.getService( ClassLoaderService.class );
		this.mappingBinder = new MappingBinder( classLoaderService, configAccess );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingBinder getMappingBinder() {
		return mappingBinder;
	}

	/**
	 * Create a {@linkplain Binding binding} from a named URL resource
	 *
	 * @see UrlXmlSource#fromUrl
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Binding<JaxbBindableMappingDescriptor> bind(String resource) {
		//noinspection unchecked,rawtypes
		return (Binding) UrlXmlSource.fromResource( resource, classLoaderService, getMappingBinder() );
	}

	/**
	 * Create a {@linkplain Binding binding} from a File reference
	 *
	 * @see FileXmlSource#fromFile
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Binding<JaxbBindableMappingDescriptor> bind(File file) {
		//noinspection unchecked,rawtypes
		return (Binding) FileXmlSource.fromFile( file, getMappingBinder() );
	}

	/**
	 * Create a {@linkplain Binding binding} from an input stream
	 *
	 * @see InputStreamAccessXmlSource#fromStreamAccess
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Binding<JaxbBindableMappingDescriptor> bind(InputStreamAccess xmlInputStreamAccess) {
		//noinspection unchecked,rawtypes
		return (Binding) InputStreamAccessXmlSource.fromStreamAccess( xmlInputStreamAccess, getMappingBinder() );
	}

	/**
	 * Create a {@linkplain Binding binding} from an input stream
	 *
	 * @see InputStreamXmlSource#fromStream
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Binding<JaxbBindableMappingDescriptor> bind(InputStream xmlInputStream) {
		//noinspection unchecked,rawtypes
		return (Binding) InputStreamXmlSource.fromStream( xmlInputStream, getMappingBinder() );
	}

	/**
	 * Create a {@linkplain Binding binding} from a URL
	 *
	 * @see UrlXmlSource#fromUrl
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Binding<JaxbBindableMappingDescriptor> bind(URL url) {
		//noinspection unchecked,rawtypes
		return (Binding) UrlXmlSource.fromUrl( url, getMappingBinder() );
	}
}
