package info.psuj.toolbox.time;

import java.time.Clock;

/**
 * Default implementation of {@link TimeProvider} that uses the system UTC clock.
 * <p>
 * Suitable for production use.
 *
 * <p>Usage:</p>
 *
 * <pre>{@code
 * TimeProvider timeProvider = new SystemTimeProvider();
 * Instant now = timeProvider.now();
 * }</pre>
 */
public class SystemTimeProvider implements TimeProvider {

    private final Clock clock;

    public SystemTimeProvider() {
        this.clock = Clock.systemUTC();
    }

    @Override
    public Clock clock() {
        return clock;
    }

}
