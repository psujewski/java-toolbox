package info.psuj.toolbox.uuid;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedUuidProviderTest {

    @Test
    void should_return_uuids_in_given_order_from_constructor() {
        // given
        UUID uuid1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID uuid2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UuidProvider provider = new FixedUuidProvider(List.of(uuid1, uuid2));

        // when
        UUID first = provider.randomUuid();
        UUID second = provider.randomUuid();

        // then
        assertThat(first).isEqualTo(uuid1);
        assertThat(second).isEqualTo(uuid2);
    }

    @Test
    void should_return_fallback_uuid_when_list_is_exhausted() {
        // given
        UuidProvider provider = new FixedUuidProvider(List.of());

        // when
        UUID result = provider.randomUuid();

        // then
        assertThat(result.toString()).isEqualTo("00000000-0000-0000-0000-000000000000");
    }
}
