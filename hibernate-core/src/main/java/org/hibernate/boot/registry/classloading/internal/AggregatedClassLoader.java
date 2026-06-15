/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.classloading.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public class AggregatedClassLoader extends ClassLoader {
	private final ClassLoader[] individualClassLoaders;
	private final TcclLookupPrecedence tcclLookupPrecedence;

	public AggregatedClassLoader(final LinkedHashSet<ClassLoader> orderedClassLoaderSet, TcclLookupPrecedence precedence) {
		super( null );
		individualClassLoaders = orderedClassLoaderSet.toArray( new ClassLoader[orderedClassLoaderSet.size()] );
		tcclLookupPrecedence = precedence;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Iterator<ClassLoader> newClassLoaderIterator() {
		final ClassLoader threadClassLoader = locateTCCL();
		if ( tcclLookupPrecedence == TcclLookupPrecedence.NEVER || threadClassLoader == null ) {
			return newTcclNeverIterator();
		}
		else if ( tcclLookupPrecedence == TcclLookupPrecedence.AFTER ) {
			return newTcclAfterIterator(threadClassLoader);
		}
		else if ( tcclLookupPrecedence == TcclLookupPrecedence.BEFORE ) {
			return newTcclBeforeIterator(threadClassLoader);
		}
		else {
			throw new RuntimeException( "Unknown precedence: "+tcclLookupPrecedence );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Iterator<ClassLoader> newTcclBeforeIterator(final ClassLoader threadContextClassLoader) {
		final ClassLoader systemClassLoader = locateSystemClassLoader();
		return new Iterator<ClassLoader>() {
			private int currentIndex = 0;
			private boolean tcCLReturned = false;
			private boolean sysCLReturned = false;

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean hasNext() {
				if ( !tcCLReturned ) {
					return true;
				}
				else if ( currentIndex < individualClassLoaders.length ) {
					return true;
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					return true;
				}

				return false;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public ClassLoader next() {
				if ( !tcCLReturned ) {
					tcCLReturned = true;
					return threadContextClassLoader;
				}
				else if ( currentIndex < individualClassLoaders.length ) {
					currentIndex += 1;
					return individualClassLoaders[ currentIndex - 1 ];
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					sysCLReturned = true;
					return systemClassLoader;
				}
				throw new IllegalStateException( "No more item" );
			}
		};
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Iterator<ClassLoader> newTcclAfterIterator(final ClassLoader threadContextClassLoader) {
		final ClassLoader systemClassLoader = locateSystemClassLoader();
		return new Iterator<ClassLoader>() {
			private int currentIndex = 0;
			private boolean tcCLReturned = false;
			private boolean sysCLReturned = false;

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean hasNext() {
				if ( currentIndex < individualClassLoaders.length ) {
					return true;
				}
				else if ( !tcCLReturned ) {
					return true;
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					return true;
				}

				return false;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public ClassLoader next() {
				if ( currentIndex < individualClassLoaders.length ) {
					currentIndex += 1;
					return individualClassLoaders[ currentIndex - 1 ];
				}
				else if ( !tcCLReturned ) {
					tcCLReturned = true;
					return threadContextClassLoader;
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					sysCLReturned = true;
					return systemClassLoader;
				}
				throw new IllegalStateException( "No more item" );
			}
		};
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Iterator<ClassLoader> newTcclNeverIterator() {
		final ClassLoader systemClassLoader = locateSystemClassLoader();
		return new Iterator<>() {
			private int currentIndex = 0;
			private boolean sysCLReturned = false;

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean hasNext() {
				if ( currentIndex < individualClassLoaders.length ) {
					return true;
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					return true;
				}

				return false;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public ClassLoader next() {
				if ( currentIndex < individualClassLoaders.length ) {
					currentIndex += 1;
					return individualClassLoaders[ currentIndex - 1 ];
				}
				else if ( !sysCLReturned && systemClassLoader != null ) {
					sysCLReturned = true;
					return systemClassLoader;
				}
				throw new IllegalStateException( "No more item" );
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Enumeration<URL> getResources(String name) throws IOException {
		final LinkedHashSet<URL> resourceUrls = new LinkedHashSet<>();
		final Iterator<ClassLoader> clIterator = newClassLoaderIterator();
		while ( clIterator.hasNext() ) {
			final ClassLoader classLoader = clIterator.next();
			final Enumeration<URL> urls = classLoader.getResources( name );
			while ( urls.hasMoreElements() ) {
				resourceUrls.add( urls.nextElement() );
			}
		}

		return new Enumeration<URL>() {
			final Iterator<URL> resourceUrlIterator = resourceUrls.iterator();

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean hasMoreElements() {
				return resourceUrlIterator.hasNext();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public URL nextElement() {
				return resourceUrlIterator.next();
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	protected URL findResource(String name) {
		final var clIterator = newClassLoaderIterator();
		while ( clIterator.hasNext() ) {
			final ClassLoader classLoader = clIterator.next();
			final URL resource = classLoader.getResource( name );
			if ( resource != null ) {
				return resource;
			}
		}
		return super.findResource( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		final var clIterator = newClassLoaderIterator();
		final var exception = new ClassNotFoundException( "Could not load requested class: " + name );
		while ( clIterator.hasNext() ) {
			final ClassLoader classLoader = clIterator.next();
			try {
				return classLoader.loadClass( name );
			}
			catch (Exception|LinkageError ex) {
				exception.addSuppressed( ex );
			}
		}
		throw exception;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static ClassLoader locateSystemClassLoader() {
		try {
			return ClassLoader.getSystemClassLoader();
		}
		catch (Exception e) {
			return null;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static ClassLoader locateTCCL() {
		try {
			return Thread.currentThread().getContextClassLoader();
		}
		catch (Exception e) {
			return null;
		}
	}

}
