package info.psuj.toolbox.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SystemTimeProviderTest {

    @Test
    void should_return_current_time_using_system_clock() {
        // given
        TimeProvider provider = new SystemTimeProvider();

        // when
        Instant before = Instant.now();
        Instant now = provider.now();
        Instant after = Instant.now();

        // then
        assertThat(now).isBetween(
                before.minusSeconds(1),
                after.plusSeconds(1)
        );
    }
}
