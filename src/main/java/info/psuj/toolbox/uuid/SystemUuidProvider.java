package info.psuj.toolbox.uuid;

import java.util.UUID;

/**
 * Default implementation of {@link UuidProvider} that uses {@link UUID#randomUUID()}.
 * <p>
 * Suitable for production use.
 *
 * <p>Usage:</p>
 *
 * <pre>{@code
 * UuidProvider uuidProvider = new SystemUuidProvider();
 * UUID id = uuidProvider.randomUuid();
 * }</pre>
 */
public class SystemUuidProvider implements UuidProvider {
    @Override
    public UUID randomUuid() {
        return UUID.randomUUID();
    }
}
