package info.psuj.toolbox.shared;

import java.util.*;

/**
 * Utility class for working with collections of {@link Result} objects.
 */
public class Results {
    private Results() {}

    /**
     * Convenience method to combine multiple {@link Result} objects into a single result.
     *
     * <p>If all results are successful, returns a success result containing a list of
     * all non-null entities and combined domain events. If any result is a failure,
     * returns a failure result with the aggregated error messages.
     *
     * @param results the results to combine
     * @param <T>     the entity type
     * @return combined result
     */
    public static <T> Result<List<T>> combine(Collection<Result<T>> results) {
        List<String> errors = new ArrayList<>();
        List<T> entities = new ArrayList<>();
        Set<DomainEvent> allEvents = new LinkedHashSet<>();

        for (Result<T> result : results) {
            if (result.isFailure()) {
                errors.addAll(result.errors());
            } else {
                result.entity().ifPresent(entities::add);
                allEvents.addAll(result.events());
            }
        }

        if (!errors.isEmpty()) {
            return Result.failure(new LinkedHashSet<>(errors));
        }

        return Result.success(entities, allEvents);
    }

    /**
     * Convenience method to combine multiple {@link Result} objects into a single result.
     *
     * <p>If all results are successful, returns a success result containing a list of
     * all non-null entities and combined domain events. If any result is a failure,
     * returns a failure result with the aggregated error messages.
     *
     * @param <T> the type of entity held in each result
     * @param results the results to combine
     * @return a combined result with a list of entities or error messages
     */
    @SafeVarargs
    public static <T> Result<List<T>> combine(Result<T>... results) {
        return combine(Arrays.asList(results));
    }
}
