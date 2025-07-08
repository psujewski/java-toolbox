package info.psuj.toolbox.time;

import java.time.Clock;
import java.time.Instant;

/**
 * Abstraction for system time access, based on {@link Clock}.
 * Useful for improving testability and for consistent time access throughout the application.
 *
 * <p>Example Spring Boot integration:</p>
 *
 * <pre>{@code
 * @Configuration
 * public class TimeConfig {
 *
 *     @Bean
 *     public TimeProvider timeProvider() {
 *         return new SystemTimeProvider();
 *     }
 * }
 * }</pre>
 *
 * <p>For testing purposes:</p>
 *
 * <pre>{@code
 * @TestConfiguration
 * public class FixedTimeConfig {
 *
 *     @Bean
 *     public TimeProvider timeProvider() {
 *         return new FixedTimeProvider(Instant.parse("2025-01-01T00:00:00Z"));
 *     }
 * }
 * }</pre>
 */
@FunctionalInterface
public interface TimeProvider {
    /**
     * Exposes the underlying clock.
     * @return the clock instance
     */
    Clock clock();

    /**
     * Returns the current instant from the clock.
     * @return the current time
     */
    default Instant now() {
        return Instant.now(clock());
    }
}
