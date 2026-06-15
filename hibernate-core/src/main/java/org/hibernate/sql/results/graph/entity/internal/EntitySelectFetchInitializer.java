/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity.internal;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.hibernate.EntityFilterException;
import org.hibernate.FetchNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.entity.EntityInitializer;
import org.hibernate.sql.results.graph.internal.AbstractInitializer;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;

import jakarta.annotation.Nullable;

import static org.hibernate.internal.log.LoggingHelper.toLoggableString;
import static org.hibernate.proxy.HibernateProxy.extractLazyInitializer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class EntitySelectFetchInitializer<Data extends EntitySelectFetchInitializer.EntitySelectFetchInitializerData>
		extends AbstractInitializer<Data> implements EntityInitializer<Data> {
	protected final InitializerParent<?> parent;
	private final NavigablePath navigablePath;
	private final boolean isPartOfKey;
	private final boolean isEnhancedForLazyLoading;

	protected final EntityPersister concreteDescriptor;
	protected final DomainResultAssembler<?> keyAssembler;
	protected final ToOneAttributeMapping toOneMapping;
	protected final boolean affectedByFilter;
	protected final boolean keyIsEager;
	protected final boolean hasLazySubInitializer;
	protected final FetchOptions fetchOptions;

	public static class EntitySelectFetchInitializerData extends InitializerData {
		// per-row state
		protected @Nullable Object entityIdentifier;

		public EntitySelectFetchInitializerData(
				EntitySelectFetchInitializer<?> initializer,
				RowProcessingState rowProcessingState) {
			super( rowProcessingState );
		}

		/*
		 * Used by Hibernate Reactive
		 */
		public EntitySelectFetchInitializerData(EntitySelectFetchInitializerData original) {
			super( original );
			this.entityIdentifier = original.entityIdentifier;
		}
	}

	public EntitySelectFetchInitializer(
			InitializerParent<?> parent,
			ToOneAttributeMapping toOneMapping,
			NavigablePath fetchedNavigable,
			EntityPersister concreteDescriptor,
			DomainResult<?> keyResult,
			boolean affectedByFilter,
			FetchOptions fetchOptions,
			AssemblerCreationState creationState) {
		super( creationState );
		this.parent = parent;
		this.toOneMapping = toOneMapping;
		this.navigablePath = fetchedNavigable;
		this.concreteDescriptor = concreteDescriptor;
		this.affectedByFilter = affectedByFilter;
		this.fetchOptions = fetchOptions;

		isPartOfKey = Initializer.isPartOfKey( fetchedNavigable, parent );
		keyAssembler = keyResult.createResultAssembler( this, creationState );
		isEnhancedForLazyLoading =
				concreteDescriptor.getBytecodeEnhancementMetadata()
						.isEnhancedForLazyLoading();

		keyIsEager = keyAssembler.isEager();
		hasLazySubInitializer = keyAssembler.hasLazySubInitializers();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected InitializerData createInitializerData(RowProcessingState rowProcessingState) {
		return new EntitySelectFetchInitializerData( this, rowProcessingState );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPart getInitializedPart(){
		return toOneMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable InitializerParent<?> getParent() {
		return parent;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void resolveFromPreviousRow(Data data) {
		if ( data.getState() == State.UNINITIALIZED ) {
			if ( data.getInstance() == null ) {
				data.setState( State.MISSING );
			}
			else {
				final var initializer = keyAssembler.getInitializer();
				if ( initializer != null ) {
					initializer.resolveFromPreviousRow( data.getRowProcessingState() );
				}
				data.setState( State.INITIALIZED );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void resolveInstance(Data data) {
		if ( data.getState() == State.KEY_RESOLVED ) {
			final var rowProcessingState = data.getRowProcessingState();
			final Object identifier = keyAssembler.assemble( rowProcessingState );
			data.entityIdentifier = identifier;
			if ( identifier == null ) {
				data.setState( State.MISSING );
				data.setInstance( null );
			}
			else {
				data.setState( State.INITIALIZED );
				initialize( data );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void resolveInstance(Object instance, Data data) {
		if ( instance == null ) {
			data.setState(  State.MISSING );
			data.entityIdentifier = null;
			data.setInstance( null );
		}
		else {
			final var rowProcessingState = data.getRowProcessingState();
			final var session = rowProcessingState.getSession();
			final var persistenceContext = session.getPersistenceContextInternal();
			final var lazyInitializer = extractLazyInitializer( instance );
			if ( lazyInitializer == null ) {
				data.setState( State.INITIALIZED );
				data.entityIdentifier = concreteDescriptor.getIdentifier( instance, session );
			}
			else if ( lazyInitializer.isUninitialized() ) {
				data.setState( State.RESOLVED );
				data.entityIdentifier = lazyInitializer.getInternalIdentifier();
			}
			else {
				data.setState( State.INITIALIZED );
				data.entityIdentifier = lazyInitializer.getInternalIdentifier();
			}

			final var entityKey =
					rowProcessingState.getSession()
							.generateEntityKey( data.entityIdentifier, concreteDescriptor );
			final var entityHolder = persistenceContext.getEntityHolder(
					entityKey
			);

			if ( entityHolder == null || entityHolder.getEntity() != instance && entityHolder.getProxy() != instance ) {
				// the existing entity instance is detached or transient
				if ( entityHolder != null ) {
					final var managed = entityHolder.getManagedObject();
					data.setInstance( managed );
					data.entityIdentifier = entityHolder.getEntityKey().getIdentifier();
					data.setState( entityHolder.isInitialized() ? State.INITIALIZED : State.RESOLVED );
				}
				else {
					initialize( data, null, session, persistenceContext );
				}
			}
			else {
				data.setInstance( instance );
			}

			if ( keyIsEager ) {
				final var initializer = keyAssembler.getInitializer();
				assert initializer != null;
				initializer.resolveInstance( data.entityIdentifier, rowProcessingState );
			}
			else if ( rowProcessingState.needsResolveState() ) {
				// Resolve the state of the identifier if result caching is enabled and this is not a query cache hit
				keyAssembler.resolveState( rowProcessingState );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initializeInstance(Data data) {
		if ( data.getState() == State.RESOLVED ) {
			data.setState( State.INITIALIZED );
			Hibernate.initialize( data.getInstance() );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void initialize(EntitySelectFetchInitializerData data) {
		final var rowProcessingState = data.getRowProcessingState();
		final var session = rowProcessingState.getSession();
		final var persistenceContext = session.getPersistenceContextInternal();
		final EntityKey entityKey = data.getRowProcessingState().getSession().generateEntityKey( data.entityIdentifier, concreteDescriptor );
		initialize( data, persistenceContext.getEntityHolder( entityKey ), session, persistenceContext );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void initialize(
			EntitySelectFetchInitializerData data,
			@Nullable EntityHolder holder,
			SharedSessionContractImplementor session,
			PersistenceContext persistenceContext) {
		if ( holder != null ) {
			data.setInstance( persistenceContext.proxyFor( holder, concreteDescriptor ) );
			if ( holder.getEntityInitializer() == null ) {
				if ( data.getInstance() != null && Hibernate.isInitialized( data.getInstance() ) ) {
					data.setState( State.INITIALIZED );
					return;
				}
			}
			else if ( holder.getEntityInitializer() != this ) {
				// the entity is already being loaded elsewhere in this processing level
				if ( holder.getJdbcValuesProcessingState() == data.getRowProcessingState().getJdbcValuesSourceProcessingState() ) {
					data.setState( State.INITIALIZED );
				}
				return;
			}
			else if ( data.getInstance() == null ) {
				// todo: maybe mark this as resolved instead?
				assert holder.getProxy() == null : "How to handle this case?";
				data.setState( State.INITIALIZED );
				return;
			}
		}
		data.setState( State.INITIALIZED );
		final String entityName = concreteDescriptor.getEntityName();

		final Object instance =
				withFetchOptions(
						session,
						() -> session.internalLoad(
								entityName,
								data.entityIdentifier,
								true,
								toOneMapping.isInternalLoadNullable()
						)
				);
		data.setInstance( instance );

		if ( instance == null ) {
			checkNotFound( data );
			persistenceContext.claimEntityHolderIfPossible(
					data.getRowProcessingState().getSession().generateEntityKey( data.entityIdentifier, concreteDescriptor ),
					null,
					data.getRowProcessingState().getJdbcValuesSourceProcessingState(),
					this
			);
		}

		final boolean unwrapProxy = toOneMapping.isUnwrapProxy() && isEnhancedForLazyLoading;
		final var lazyInitializer = extractLazyInitializer( data.getInstance() );
		if ( lazyInitializer != null ) {
			lazyInitializer.setUnwrap( unwrapProxy );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	void checkNotFound(EntitySelectFetchInitializerData data) {
		checkNotFound( toOneMapping, affectedByFilter,
				concreteDescriptor.getEntityName(),
				data.entityIdentifier );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static void checkNotFound(
			ToOneAttributeMapping toOneMapping,
			boolean affectedByFilter,
			String entityName, Object identifier) {
		final var notFoundAction = toOneMapping.getNotFoundAction();
		if ( notFoundAction != NotFoundAction.IGNORE ) {
			if ( affectedByFilter ) {
				throw new EntityFilterException( entityName, identifier,
						toOneMapping.getNavigableRole().getFullPath() );
			}
			if ( notFoundAction == NotFoundAction.EXCEPTION ) {
				throw new FetchNotFoundException( entityName, identifier );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initializeInstanceFromParent(Object parentInstance, Data data) {
		final var attributeMapping = getInitializedPart().asAttributeMapping();
		final Object instance =
				attributeMapping != null
						? attributeMapping.getValue( parentInstance )
						: parentInstance;
		if ( instance == null ) {
			data.setState( State.MISSING );
			data.entityIdentifier = null;
			data.setInstance( null );
		}
		else {
			data.setState( State.INITIALIZED );
			// No need to initialize this
			data.entityIdentifier = null;
			data.setInstance( instance );
			Hibernate.initialize( instance );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void forEachSubInitializer(BiConsumer<Initializer<?>, RowProcessingState> consumer, InitializerData data) {
		final var initializer = keyAssembler.getInitializer();
		if ( initializer != null ) {
			consumer.accept( initializer, data.getRowProcessingState() );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getEntityDescriptor() {
		return concreteDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getConcreteDescriptor(Data data) {
		return concreteDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getEntityIdentifier(Data data) {
		return data.entityIdentifier;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPartOfKey() {
		return isPartOfKey;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEager() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void resolveState(EntitySelectFetchInitializerData data) {
		keyAssembler.resolveState( data.getRowProcessingState() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasLazySubInitializers() {
		return hasLazySubInitializer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isResultInitializer() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "EntitySelectFetchInitializer("
				+ toLoggableString( getNavigablePath() ) + ")";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?> getKeyAssembler() {
		return keyAssembler;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected <T> T withFetchOptions(SharedSessionContractImplementor session, Supplier<T> action) {
		return session.getLoadQueryInfluencers()
				.withFetchOptions( session, fetchOptions, action);
	}

}
