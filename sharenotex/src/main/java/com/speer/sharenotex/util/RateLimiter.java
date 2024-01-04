package com.speer.sharenotex.util;

import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;

/**
 * Simple rate limiter implementation to control the frequency of method
 * invocations.
 */
@Data
public class RateLimiter {

	private final int limit;
	private final long durationInMillis;
	private static final AtomicLong lastRequestTime = new AtomicLong(0);
	private static final AtomicLong requestCount = new AtomicLong(0);

	/**
	 * Constructs a RateLimiter with the specified limit and duration.
	 *
	 * @param limit            Maximum number of method invocations allowed within
	 *                         the specified duration.
	 * @param durationInMillis Duration (in milliseconds) within which the maximum
	 *                         number of invocations is allowed.
	 */
	public RateLimiter(int limit, long durationInMillis) {
		this.limit = limit;
		this.durationInMillis = durationInMillis;
	}

	/**
	 * Attempts to acquire permission for a method invocation based on the rate
	 * limit constraints.
	 *
	 * @return true if permission is granted, false otherwise.
	 */
	public synchronized boolean tryAcquire() {
		long currentTime = System.currentTimeMillis();
		long lastRequest = lastRequestTime.getAndSet(currentTime);

		if (currentTime - lastRequest >= durationInMillis) {
			requestCount.set(1);
			return true;
		} else {
			return requestCount.getAndIncrement() < limit;
		}
	}

}
