/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.produce.function;

import org.hibernate.type.BindingContext;
import org.hibernate.query.sqm.tree.SqmTypedNode;

import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public final class StandardArgumentsValidators {
	/**
	 * Disallow instantiation
	 */
	private StandardArgumentsValidators() {
	}

	/**
	 * Static validator for performing no validation
	 */
	public static final ArgumentsValidator NONE = new ArgumentsValidator() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void validate(
				List<? extends SqmTypedNode<?>> arguments,
				String functionName,
				BindingContext bindingContext) {
			// nothing to do
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getSignature() {
			return "([arg0[, ...]])";
		}
	};

	/**
	 * Static validator for verifying that we have no arguments
	 */
	public static final ArgumentsValidator NO_ARGS = new ArgumentsValidator() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void validate(
				List<? extends SqmTypedNode<?>> arguments,
				String functionName,
				BindingContext bindingContext) {
			if ( !arguments.isEmpty() ) {
				throw new FunctionArgumentException(
						String.format(
								Locale.ROOT,
								"Function %s has no parameters, but %d arguments given",
								functionName,
								arguments.size()
						)
				);
			}
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getSignature() {
			return "()";
		}
	};

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator min(int minNumOfArgs) {
		if (minNumOfArgs<1) {
			throw new IllegalArgumentException();
		}
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				if ( arguments.size() < minNumOfArgs ) {
					throw new FunctionArgumentException(
							String.format(
									Locale.ROOT,
									"Function %s() requires at least %d arguments, but only %d arguments given",
									functionName,
									minNumOfArgs,
									arguments.size()
							)
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getSignature() {
				final var sig = new StringBuilder("(");
				for (int i=0; i<minNumOfArgs; i++) {
					if (i!=0) {
						sig.append(", ");
					}
					sig.append("arg").append(i);
				}
				sig.append("[, arg");
				sig.append(minNumOfArgs);
				sig.append("[, ...]])");
				return sig.toString();
			}
		};
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator exactly(int number) {
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				if ( arguments.size() != number ) {
					throw new FunctionArgumentException(
							String.format(
									Locale.ROOT,
									"Function %s() has %d parameters, but %d arguments given",
									functionName,
									number,
									arguments.size()
							)
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getSignature() {
				final var sig = new StringBuilder("(");
				for (int i=0; i<number; i++) {
					if (i!=0) {
						sig.append(", ");
					}
					sig.append("arg");
					if (number>1) {
						sig.append(i);
					}
				}
				sig.append(")");
				return sig.toString();
			}

		};
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator max(int maxNumOfArgs) {
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				if ( arguments.size() > maxNumOfArgs ) {
					throw new FunctionArgumentException(
							String.format(
									Locale.ROOT,
									"Function %s() allows at most %d arguments, but %d arguments given",
									functionName,
									maxNumOfArgs,
									arguments.size()
							)
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getSignature() {
				final var sig = new StringBuilder("([");
				for (int i=0; i<maxNumOfArgs; i++) {
					if (i!=0) {
						sig.append(", ");
					}
					sig.append("arg").append(i);
				}
				sig.append("])");
				return sig.toString();
			}
		};
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator between(int minNumOfArgs, int maxNumOfArgs) {
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				if ( arguments.size() < minNumOfArgs || arguments.size() > maxNumOfArgs ) {
					throw new FunctionArgumentException(
							String.format(
									Locale.ROOT,
									"Function %s() requires between %d and %d arguments, but %d arguments given",
									functionName,
									minNumOfArgs,
									maxNumOfArgs,
									arguments.size()
							)
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getSignature() {
				final var sig = new StringBuilder("(");
				for (int i=0; i<maxNumOfArgs; i++) {
					if (i==minNumOfArgs) {
						sig.append("[");
					}
					if (i!=0) {
						sig.append(", ");
					}
					sig.append("arg").append(i);
				}
				sig.append("])");
				return sig.toString();
			}
		};
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator of(Class<?> javaType) {
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				for ( var argument : arguments ) {
					var argType = argument.getNodeJavaType().getJavaTypeClass();
					if ( !javaType.isAssignableFrom( argType ) ) {
						throw new FunctionArgumentException(
								String.format(
										Locale.ROOT,
										"Function %s() has parameters of type %s, but argument of type %s given",
										functionName,
										javaType.getName(),
										argType.getName()
								)
						);
					}
				}
			}
		};
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ArgumentsValidator composite(ArgumentsValidator... validators) {
		return composite( asList( validators ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ArgumentsValidator composite(List<ArgumentsValidator> validators) {
		return new ArgumentsValidator() {
			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public void validate(
					List<? extends SqmTypedNode<?>> arguments,
					String functionName,
					BindingContext bindingContext) {
				validators.forEach( individualValidator ->
						individualValidator.validate( arguments, functionName, bindingContext ) );
			}
		};
	}
}
