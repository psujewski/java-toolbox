package info.psuj.toolbox.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * Test implementation of {@link TimeProvider} that always returns a fixed instant.
 * <p>
 * Useful for writing deterministic unit tests.
 *
 * <p>Usage:</p>
 *
 * <pre>{@code
 * TimeProvider timeProvider = new FixedTimeProvider(Instant.parse("2025-01-01T00:00:00Z"));
 * }</pre>
 */
public class FixedTimeProvider implements TimeProvider {
    private final Clock fixedClock;

    /**
     * Creates a FixedTimeProvider with a specific instant.
     * @param fixedInstant the fixed point in time
     */
    public FixedTimeProvider(Instant fixedInstant) {
        this.fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
    }

    /**
     * Creates a FixedTimeProvider using a fixed Clock.
     * @param fixedClock a clock returning a constant instant
     */
    public FixedTimeProvider(Clock fixedClock) {
        this.fixedClock = fixedClock;
    }

    public static FixedTimeProvider nowAt(Instant instant) {
        return new FixedTimeProvider(instant);
    }

    @Override
    public Clock clock() {
        return fixedClock;
    }
}
