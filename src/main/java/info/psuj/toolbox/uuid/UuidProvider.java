package info.psuj.toolbox.uuid;

import java.util.UUID;

/**
 * Abstraction for generating UUIDs.
 * Designed to improve testability by avoiding direct usage of {@link UUID#randomUUID()}.
 *
 * <p>Example Spring Boot integration:</p>
 *
 * <pre>{@code
 * @Configuration
 * public class UuidConfig {
 *
 *     @Bean
 *     public UuidProvider uuidProvider() {
 *         return new SystemUuidProvider();
 *     }
 * }
 * }</pre>
 *
 * <p>For testing purposes:</p>
 *
 * <pre>{@code
 * @TestConfiguration
 * public class FixedUuidConfig {
 *
 *     @Bean
 *     public UuidProvider uuidProvider() {
 *         return new FixedUuidProvider(List.of(
 *             UUID.fromString("11111111-1111-1111-1111-111111111111")
 *         ));
 *     }
 * }
 * }</pre>
 */
@FunctionalInterface
public interface UuidProvider {

    /**
     * Returns a random or predefined UUID.
     * @return UUID value
     */
    UUID randomUuid();
}
