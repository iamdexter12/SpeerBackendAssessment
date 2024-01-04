package com.speer.sharenotex.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.speer.sharenotex.exception.RateLimitExceedException;

/**
 * Aspect class for enforcing rate limits on methods annotated with RateLimited.
 */
@Aspect
@Component
public class RateLimitAspect {

	/**
	 * Intercepts method calls annotated with RateLimited and enforces rate limits.
	 *
	 * @param joinPoint   ProceedingJoinPoint representing the intercepted method.
	 * @param rateLimited RateLimited annotation specifying the rate limit
	 *                    constraints.
	 * @return The result of the intercepted method if rate limit is not exceeded.
	 * @throws Throwable NotFoundException with a message indicating rate limit
	 *                   exceeded if limit is exceeded.
	 */
	@Around("@annotation(rateLimited)")
	public Object enforceRateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
		RateLimiter rateLimiter = new RateLimiter(rateLimited.value(), rateLimited.duration());
		if (rateLimiter.tryAcquire()) {
			return joinPoint.proceed();
		} else {
			throw new RateLimitExceedException("rate", "Rate limit exceeded");
		}
	}
}
