package info.psuj.toolbox.uuid;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SystemUuidProviderTest {

    @Test
    void should_generate_random_uuids() {
        // given
        UuidProvider provider = new SystemUuidProvider();

        // when
        UUID first = provider.randomUuid();
        UUID second = provider.randomUuid();

        // then
        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
        assertThat(first).isNotEqualTo(second);
    }
}
