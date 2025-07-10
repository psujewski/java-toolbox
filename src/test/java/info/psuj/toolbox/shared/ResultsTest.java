package info.psuj.toolbox.shared;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsTest {

    @Test
    void should_combine_successful_results() {
        // given
        Result<String> r1 = Result.success("One", new TestEvent("1"));
        Result<String> r2 = Result.success("Two", new TestEvent("1"), new TestEvent("2"));
        Result<String> r3 = Result.success("Two", Set.of(new TestEvent("1"), new TestEvent("2")));

        // when
        Result<List<String>> combined = Results.combine(r1, r2, r3);

        // then
        assertThat(combined.isSuccess()).isTrue();
        assertThat(combined.entity()).hasValueSatisfying(entity ->
                assertThat(entity).containsExactlyInAnyOrder("One", "Two", "Two")
        );
        assertThat(combined.events()).contains(new TestEvent("1"), new TestEvent("2"));
        assertThat(combined.errors()).isEmpty();
    }

    @Test
    void should_combine_failed_results_to_single_failed_result() {
        // given
        Result<String> r1 = Result.success("OK");
        Result<String> r2 = Result.failure("Missing name");
        Result<String> r3 = Result.failure("Invalid format");

        // when
        Result<List<String>> combined = Results.combine(r1, r2, r3);

        // then
        assertThat(combined.isFailure()).isTrue();
        assertThat(combined.errors()).containsExactlyInAnyOrder("Missing name", "Invalid format");
        assertThat(combined.entity()).isEmpty();
        assertThat(combined.events()).isEmpty();
    }

    @Test
    void should_return_fail_when_any_of_results_failing() {
        //. given
        Result<Void> r1 = Result.success(new TestEvent("1"));
        Result<Void> r2 = Result.success();
        Result<Void> r3 = Result.failure("Err");

        // when
        Result<List<Void>> combined = Results.combine(r1, r2, r3);

        // then
        assertThat(combined.isFailure()).isTrue();
        assertThat(combined.errors()).containsExactlyInAnyOrder("Err");
        assertThat(combined.entity()).isEmpty();
        assertThat(combined.events()).isEmpty();
    }

    @Test
    void should_combine_ignoring_null_entities_in_successes() {
        // given
        Result<String> r1 = Result.success((String)null);
        Result<String> r2 = Result.success("Defined");

        // when
        Result<List<String>> combined = Results.combine(r1, r2);

        // then
        assertThat(combined.isFailure()).isFalse();
        assertThat(combined.errors()).isEmpty();
        assertThat(combined.entity()).isPresent().hasValueSatisfying(entity ->
                assertThat(entity).containsExactlyInAnyOrder("Defined")
        );
        assertThat(combined.events()).isEmpty();
    }

    @Test
    void should_combine_empty_successes() {
        // when
        Result<List<Void>> combined = Results.combine(
                Result.success(),
                Result.success(),
                Result.success()
        );

        // then
        assertThat(combined.isSuccess()).isTrue();
        assertThat(combined.entity()).isPresent().hasValueSatisfying(entity ->
                assertThat(entity).isEmpty()
        );
        assertThat(combined.errors()).isEmpty();
        assertThat(combined.events()).isEmpty();
    }

    @Test
    void should_combine_empty_inputs() {
        // when
        Result<List<String>> combined = Results.combine(List.of());

        // then
        assertThat(combined.isSuccess()).isTrue();
        assertThat(combined.entity()).isPresent().hasValueSatisfying(entity ->
                assertThat(entity).isEmpty()
        );
        assertThat(combined.errors()).isEmpty();
        assertThat(combined.events()).isEmpty();
    }

    public record TestEvent(String event) implements DomainEvent {
    }
}