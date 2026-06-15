/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import java.util.function.Function;

import org.hibernate.Incubating;
import org.hibernate.engine.FetchTiming;
import org.hibernate.metamodel.mapping.AssociationKey;
import org.hibernate.metamodel.mapping.EmbeddableDiscriminatorMapping;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.mapping.EntityDiscriminatorMapping;
import org.hibernate.metamodel.mapping.EntityIdentifierMapping;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.ForeignKeyDescriptor;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.EntityIdentifierNavigablePath;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAliasBaseManager;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.embeddable.EmbeddableResultGraphNode;
import org.hibernate.sql.results.graph.entity.EntityResultGraphNode;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;

import static org.hibernate.query.results.internal.ResultsHelper.attributeName;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contains state related to building {@link DomainResult} and
 * {@link Fetch} graphs
 *
 * @author Steve Ebersole
 */
@Incubating
public interface DomainResultCreationState {
	/**
	 * Whether forcing the selection of the identifier is
	 * in effect for this creation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean forceIdentifierSelection(){
		return true;
	}

	/**
	 * The underlying state for SQL AST creation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstCreationState getSqlAstCreationState();

	/**
	 * Access to the SQL alias helper
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqlAliasBaseManager getSqlAliasBaseManager() {
		return (SqlAliasBaseManager) getSqlAstCreationState().getSqlAliasBaseGenerator();
	}

	/**
	 * Registers a circularity detection key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean registerVisitedAssociationKey(AssociationKey circularityKey) {
		return false;
	}

	/**
	 * Removes the registration of a circularity detection key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void removeVisitedAssociationKey(AssociationKey circularityKey){
	}

	/**
	 * Checks whether the given circularityKey is registered
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAssociationKeyVisited(AssociationKey circularityKey){
		return false;
	}

	/**
	 * Is this state accepting circularity detection keys?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isRegisteringVisitedAssociationKeys(){
		return false;
	}

	/**
	 * Resolve the ModelPart associated with a given NavigablePath.  More specific ModelParts should be preferred - e.g.
	 * the SingularAssociationAttributeMapping rather than just the EntityTypeMapping for the associated type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart resolveModelPart(NavigablePath navigablePath);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Fetch visitIdentifierFetch(EntityResultGraphNode fetchParent) {
		final EntityIdentifierMapping identifierMapping = fetchParent.getReferencedMappingContainer()
				.getIdentifierMapping();
		return fetchParent.generateFetchableFetch(
				identifierMapping,
				new EntityIdentifierNavigablePath( fetchParent.getNavigablePath(), attributeName( identifierMapping ) ),
				FetchTiming.IMMEDIATE,
				true,
				null,
				this
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default BasicFetch<?> visitDiscriminatorFetch(EntityResultGraphNode fetchParent) {
		final EntityMappingType entityDescriptor = fetchParent.getEntityValuedModelPart().getEntityMappingType();
		final EntityDiscriminatorMapping discriminatorMapping = entityDescriptor.getDiscriminatorMapping();
		// No need to fetch the discriminator if this type does not have subclasses
		if ( discriminatorMapping != null && entityDescriptor.hasSubclasses() ) {
			return discriminatorMapping.generateFetch(
					fetchParent,
					fetchParent.getNavigablePath().append( EntityDiscriminatorMapping.DISCRIMINATOR_ROLE_NAME ),
					FetchTiming.IMMEDIATE,
					true,
					null,
					this
			);
		}
		else {
			return null;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default BasicFetch<?> visitEmbeddableDiscriminatorFetch(EmbeddableResultGraphNode fetchParent, boolean nested) {
		final EmbeddableMappingType embeddableType = fetchParent.getReferencedMappingType();
		final EmbeddableDiscriminatorMapping discriminatorMapping = embeddableType.getDiscriminatorMapping();
		if ( discriminatorMapping != null ) {
			final Function<FetchParent, BasicFetch<?>> fetchSupplier = fp -> discriminatorMapping.generateFetch(
					fp,
					fp.getNavigablePath().append( EntityDiscriminatorMapping.DISCRIMINATOR_ROLE_NAME ),
					FetchTiming.IMMEDIATE,
					true,
					null,
					this
			);
			if ( nested ) {
				return withNestedFetchParent( fetchParent, fetchSupplier );
			}
			else {
				return fetchSupplier.apply( fetchParent );
			}
		}
		else {
			return null;
		}
	}

	/**
	 * Visit fetches for the given parent.
	 *
	 * We walk fetches via the SqlAstCreationContext because each "context"
	 * will define differently what should be fetched (HQL versus load)
	 */
	/*
	 * todo (6.0) : centralize the implementation of this
	 * 		most of the logic in the impls of this is identical.  variations include:
	 * 				1) given a Fetchable, determine the FetchTiming and `selected`[1].  Tricky as functional
	 * 					interface because of the "composite return".
	 * 				2) given a Fetchable, determine the LockMode - currently not handled very well here; should consult `#getLockOptions`
	 * 						 - perhaps a functional interface accepting the FetchParent and Fetchable and returning the LockMode
	 *
	 * 			so something like:
	 * 				List<Fetch> visitFetches(
	 * 	 					FetchParent fetchParent,
	 * 	 					BiFunction<FetchParent,Fetchable,(FetchTiming,`selected`)> fetchOptionsResolver,
	 * 	 					BiFunction<FetchParent,Fetchable,LockMode> lockModeResolver)
	 *
	 * [1] `selected` refers to the named parameter in
	 * {@link Fetchable#generateFetch(FetchParent, org.hibernate.query.NavigablePath, org.hibernate.engine.FetchTiming, boolean, LockMode, String, DomainResultCreationState)}.
	 * For {@link org.hibernate.engine.FetchTiming#IMMEDIATE}, this boolean value indicates
	 * whether the values for the generated assembler/initializers are or should be available in
	 * the {@link JdbcValues} being processed.  For {@link org.hibernate.engine.FetchTiming#DELAYED} this
	 * parameter has no effect
	 *
	 * todo (6.0) : wrt the "trickiness" of `selected[1]`, that may no longer be an issue given how TableGroups
	 * 		are built/accessed.  Comes down to how we'd know whether to join fetch or select fetch.  Simply pass
	 * 		along FetchStyle?
	*/
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ImmutableFetchList visitFetches(FetchParent fetchParent);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ImmutableFetchList visitNestedFetches(FetchParent fetchParent) {
		return withNestedFetchParent( fetchParent, this::visitFetches );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> R withNestedFetchParent(FetchParent fetchParent, Function<FetchParent, R> action);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isResolvingCircularFetch();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setResolvingCircularFetch(boolean resolvingCircularFetch);

	/**
	 * Returns the part of the foreign key that is currently being resolved,
	 * or <code>null</code> if no foreign key is currently being resolved.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDescriptor.Nature getCurrentlyResolvingForeignKeyPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCurrentlyResolvingForeignKeyPart(ForeignKeyDescriptor.Nature currentlyResolvingForeignKeySide);

	/**
	 * Register the fetch options applied by an entity graph to a fetch path.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void registerFetchOptions(
			NavigablePath fetchablePath,
			org.hibernate.engine.spi.FetchOptions fetchOptions) {
	}

	/**
	 * The fetch options applied by an entity graph to this fetch path.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default org.hibernate.engine.spi.FetchOptions getFetchOptions(NavigablePath fetchablePath) {
		return org.hibernate.engine.spi.FetchOptions.NONE;
	}
}
