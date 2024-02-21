package io.github.t45k.arrow4j.core;

import io.github.t45k.arrow4j.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static io.github.t45k.arrow4j.core.Option.None;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionTest {
    private static final Option<String> some = Option.fromNullable("java");
    private static final Option<String> none = Option.none();

    @Test
    public void fromOptional_should_work_for_both_present_and_empty_values_of_optional() {
        assertEquals(new Option.Some<>(1), Option.fromOptional(Optional.of(1)));
        assertEquals(None, Option.fromOptional(Optional.empty()));
    }

    @Test
    public void fromNullable_should_work_for_both_null_and_non_null_values() {
        assertEquals(new Option.Some<>(1), Option.fromNullable(1));
        assertEquals(None, Option.fromNullable(null));
    }

    @Test
    public void filterIsInstance() {
        assertEquals(new Option.Some<>("java"), Option.filterIsInstance(some, String.class));
        assertEquals(None, Option.filterIsInstance(some, Integer.class));
        assertEquals(None, Option.filterIsInstance(none, String.class));
        assertEquals(None, Option.filterIsInstance(none, Integer.class));
    }

    @Test
    public void flatten() {
        assertEquals(new Option.Some<>("java"), Option.flatten(new Option.Some<>(some)));
        assertEquals(None, Option.flatten(new Option.Some<>(none)));
    }

    @Test
    public void widen() {
        final Option<CharSequence> widen = Option.widen(some);
        assertEquals(new Option.Some<>(4), widen.map(CharSequence::length));
    }

    @Test
    public void toMap() {
        assertEquals(Map.of("key", "value"), Option.toMap(new Option.Some<>(new Pair<>("key", "value"))));
        assertEquals(Collections.emptyMap(), Option.toMap(Option.none()));
    }

    @Test
    public void tryCatch_should_return_Some_when_f_does_not_throw() {
        final Function<Throwable, Option<Integer>> recover = e -> Option.none();

        assertEquals(new Option.Some<>(1), Option.tryCatch(recover, () -> 1));
    }

    @Test
    public void tryCatch_should_return_Some_with_recover_value_when_f_throws() {
        final RuntimeException exception = new RuntimeException("Boom!");
        final int recoverValue = 10;
        final Function<Throwable, Option<Integer>> recover = e -> new Option.Some<>(recoverValue);

        assertEquals(
            new Option.Some<>(recoverValue),
            Option.tryCatch(recover, () -> {
                throw exception;
            }));
    }

    @Test
    public void tryCatchOrNone_should_return_Some_when_f_does_not_throw() {
        assertEquals(new Option.Some<>(1), Option.tryCatchOrNone(() -> 1));
    }

    @Test
    public void tryCatchOrNone_should_return_Some_when_f_throws() {
        final RuntimeException exception = new RuntimeException("Boom!");
        assertEquals(None, Option.tryCatchOrNone(() -> {
            throw exception;
        }));
    }

    @Test
    public void onNone_applies_effects_returning_the_original_value() {
        final int[] effect = new int[1];

        final Option<String> someRes = some.onNone(() -> effect[0]++);
        assertEquals(0, effect[0]);
        assertEquals(some, someRes);

        final Option<String> noneRes = none.onNone(() -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(none, noneRes);
    }

    @Test
    public void onSome_applies_effects_returning_the_original_value() {
        final int[] effect = new int[1];

        final Option<String> someRes = some.onSome((unused) -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(some, someRes);

        final Option<String> noneRes = none.onSome((unused) -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(none, noneRes);
    }

    @Test
    public void isNone_should_return_true_if_None_and_false_if_Some() {
        assertFalse(some.isNone());
        assertTrue(none.isNone());
    }

    @Test
    public void isSome_should_return_true_if_Some_and_false_if_None() {
        assertTrue(some.isSome());
        assertFalse(none.isSome());
    }

    @Test
    public void isSome_with_predicate() {
        assertTrue(some.isSome(it -> it.startsWith("j")));
        assertFalse(some.isSome(it -> it.startsWith("k")));
        assertFalse(none.isSome(it -> it.startsWith("j")));
    }

    @Test
    public void map() {
        assertEquals(new Option.Some<>("JAVA"), some.map(String::toUpperCase));
        assertEquals(None, none.map(String::toUpperCase));
    }

    @Test
    public void flatMap() {
        assertEquals(new Option.Some<>("JAVA"), some.flatMap(it -> new Option.Some<>(it.toUpperCase())));
        assertEquals(None, none.flatMap(it -> new Option.Some<>(it.toUpperCase())));
    }

    @Test
    public void fold() {
        assertEquals(4, some.fold(() -> 0, String::length));
        assertEquals(0, none.fold(() -> 0, String::length));
    }

    @Test
    public void filter() {
        assertEquals(new Option.Some<>("java"), some.filter(it -> it.startsWith("j")));
        assertEquals(None, some.filter(it -> it.startsWith("k")));
        assertEquals(None, none.filter(it -> it.startsWith("j")));
    }

    @Test
    public void filterNot() {
        assertEquals(None, some.filterNot(it -> it.startsWith("j")));
        assertEquals(new Option.Some<>("java"), some.filterNot(it -> it.startsWith("k")));
        assertEquals(None, none.filterNot(it -> it.startsWith("j")));
    }

    @Test
    public void getOrNull() {
        assertEquals("java", some.getOrNull());
        assertNull(none.getOrNull());
    }

    @Test
    public void getOrElse() {
        assertEquals("java", some.getOrElse(() -> "kotlin"));
        assertEquals("kotlin", none.getOrElse(() -> "kotlin"));
    }

    @Test
    public void toEither() {
        assertEquals(List.of("java"), some.toList());
        assertEquals(Collections.emptyList(), none.toList());
    }
}