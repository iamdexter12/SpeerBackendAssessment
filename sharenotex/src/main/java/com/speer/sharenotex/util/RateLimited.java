package com.speer.sharenotex.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to specify rate limit constraints on methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimited {

	/**
	 * Maximum number of method invocations allowed within the specified duration.
	 *
	 * @return Maximum number of method invocations.
	 */
	int value() default 5;

	/**
	 * Duration (in milliseconds) within which the maximum number of invocations is
	 * allowed.
	 *
	 * @return Duration in milliseconds.
	 */
	long duration() default 5000;
}
