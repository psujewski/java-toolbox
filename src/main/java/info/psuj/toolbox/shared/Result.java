package info.psuj.toolbox.shared;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

/**
 * Represents the result of a business operation.
 *
 * <p>A result can either be a success (with or without a value),
 * or a failure (with one or more error messages).</p>
 *
 * <p>Success results may also contain domain events.</p>
 *
 * @param <T> the type of the returned entity, or {@code Void} if there is none
 */
public class Result<T> {

    private static final Set<DomainEvent> NO_EVENTS = Set.of();
    private static final Set<String> NO_ERRORS = Set.of();

    private final boolean success;
    private final T entity;
    private final Set<DomainEvent> events;
    private final Set<String> errorMessages;

    private Result(boolean success, T entity, Set<DomainEvent> events, Set<String> errorMessages) {
        this.success = success;
        this.entity = entity;
        this.events = events;
        this.errorMessages = errorMessages;
    }

    /**
     * Creates a successful result with a value and optional domain events.
     *
     * @param entity the returned entity
     * @param events domain events to be emitted
     * @return success result with entity and events
     */
    public static <T> Result<T> success(T entity, DomainEvent... events) {
        Set<DomainEvent> domainEvents = new LinkedHashSet<>(asList(events));
        return new Result<>(true, entity, unmodifiableSet(domainEvents), NO_ERRORS);
    }

    /**
     * Creates a successful result with a value and a given set of domain events.
     *
     * @param entity the returned entity
     * @param events set of domain events
     * @return success result with entity and events
     */
    public static <T> Result<T> success(T entity, Set<DomainEvent> events) {
        return new Result<>(true, entity, unmodifiableSet(new LinkedHashSet<>(events)), NO_ERRORS);
    }

    /**
     * Creates a successful result without a value, containing a given set of domain events.
     *
     * @param events set of domain events
     * @return success result without entity
     */
    public static Result<Void> success(Set<DomainEvent> events) {
        return new Result<>(true, null, unmodifiableSet(new LinkedHashSet<>(events)), NO_ERRORS);
    }

    /**
     * Creates a successful result without a value, with one or more domain events.
     *
     * @param events domain events to be emitted
     * @return success result without entity
     */
    public static Result<Void> success(DomainEvent... events) {
        Set<DomainEvent> domainEvents = new LinkedHashSet<>(asList(events));
        return new Result<>(true, null, unmodifiableSet(domainEvents), NO_ERRORS);
    }

    /**
     * Creates a successful result without a value and without any domain events.
     *
     * @return success result with no payload
     */
    public static Result<Void> success() {
        return new Result<>(true, null, NO_EVENTS, NO_ERRORS);
    }

    /**
     * Creates a failed result with a given set of error messages.
     *
     * @param errorMessages reasons for failure
     * @return failed result
     */
    public static <T> Result<T> failure(Set<String> errorMessages) {
        return new Result<>(false, null, NO_EVENTS, unmodifiableSet(errorMessages));
    }

    /**
     * Creates a failed result with one or more error messages.
     *
     * @param errorMessages reasons for failure
     * @return failed result
     */
    public static <T> Result<T> failure(String... errorMessages) {
        return new Result<>(false, null, NO_EVENTS, unmodifiableSet(new LinkedHashSet<>(asList(errorMessages))));
    }

    /**
     * Returns {@code true} if the result represents a successful operation.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns {@code true} if the result represents a failed operation.
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Returns the result's entity wrapped in {@link Optional}.
     * <p>
     * Will be empty for failure results or for success results without a value.
     */
    public Optional<T> entity() {
        return Optional.ofNullable(entity);
    }

    /**
     * Returns an unmodifiable set of domain events.
     * <p>
     * Always empty for failure results.
     */
    public Set<DomainEvent> events() {
        return events;
    }

    /**
     * Returns an unmodifiable set of error messages.
     * <p>
     * Always empty for success results.
     */
    public Set<String> errors() {
        return errorMessages;
    }

    /**
     * Transforms the result entity using the given mapper function.
     * <p>
     * On failure, the same failure is returned without invoking the mapper.
     *
     * @param mapper function to transform entity
     * @param <R>    type of the mapped result
     * @return transformed result
     */
    public <R> Result<R> map(Function<T, R> mapper) {
        if (isSuccess() && entity != null) {
            return Result.success(mapper.apply(entity), events);
        }
        if (isSuccess()) {
            @SuppressWarnings("unchecked")
            Result<R> casted = (Result<R>) Result.success(events);
            return casted;
        }
        return Result.failure(errorMessages);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", events=" + events +
                ", errorMessages=" + errorMessages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result = (Result<?>) o;
        return success == result.success && Objects.equals(entity, result.entity) && events.equals(result.events) && errorMessages.equals(result.errorMessages);
    }

    @Override
    public int hashCode() {
        int result = Boolean.hashCode(success);
        result = 31 * result + Objects.hashCode(entity);
        result = 31 * result + events.hashCode();
        result = 31 * result + errorMessages.hashCode();
        return result;
    }
}
