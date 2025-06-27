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

    public FixedTimeProvider(Instant fixedInstant) {
        this.fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
    }

    public FixedTimeProvider(Clock fixedClock) {
        this.fixedClock = fixedClock;
    }

    @Override
    public Clock clock() {
        return fixedClock;
    }
}
