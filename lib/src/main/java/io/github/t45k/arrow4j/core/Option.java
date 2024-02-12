package io.github.t45k.arrow4j.core;

import io.github.t45k.arrow4j.util.Pair;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes", "OptionalUsedAsFieldOrParameterType", "unused"})
public sealed interface Option<A> {

    final class None implements Option {
        private None() {
        }

        public <A> Optional<A> toEmpty() {
            return Optional.empty();
        }
    }

    None None = new None();

    record Some<A>(@Nonnull A value) implements Option<A> {
    }

    // factory methods
    static <A> Option<A> fromOptional(final Optional<A> optional) {
        return optional.isPresent() ? new Some<>(optional.get()) : None;
    }

    static <A> Option<A> fromNullable(@Nullable final A value) {
        return value != null ? new Some<>(value) : None;
    }

    // static method like extension methods
    static <A> Option<A> filterIsInstance(final Option<?> option, final Class<A> clazz) {
        return option.filter(clazz::isInstance).map(clazz::cast);
    }

    static <A> Option<A> flatten(final Option<Option<A>> option) {
        return option.flatMap(it -> it);
    }

    static <B, A extends B> Option<B> widen(final Option<A> option) {
        return (Option<B>) option;
    }

    static <K, V> Map<K, V> toMap(final Option<Pair<K, V>> option) {
        return Pair.toMap(option.toList());
    }

    // try-catch methods
    static <A> Option<A> tryCatchOrNone(final Supplier<A> f) {
        final Function<Throwable, Option<A>> recover = t -> None;
        return tryCatch(recover, f);
    }

    static <A> Option<A> tryCatch(final Function<Throwable, Option<A>> recover, final Supplier<A> f) {
        try {
            return new Some(f.get());
        } catch (final Throwable t) {
            return recover.apply(t);
        }
    }

    // onXXX methods
    default Option<A> onNone(final Runnable action) {
        if (this == None) {
            action.run();
        }
        return this;
    }

    default Option<A> onSome(final Consumer<A> action) {
        if (this instanceof Some<A> some) {
            action.accept(some.value);
        }
        return this;
    }

    // isXXX methods
    default boolean isNone() {
        return this == None;
    }

    default boolean isSome() {
        return this instanceof Option.Some<A>;
    }

    default boolean isSome(final Predicate<A> predicate) {
        return this instanceof Option.Some<A> some && predicate.test(some.value);
    }

    default <B> Option<B> map(final Function<A, B> f) {
        return this.flatMap(value -> new Some(f.apply(value)));
    }

    default <B> Option<B> flatMap(final Function<A, Option<B>> f) {
        return switch (this) {
            case None none -> none;
            case Some<A>(A value) -> f.apply(value);
        };
    }

    default <R> R fold(final Supplier<R> ifEmpty, final Function<A, R> ifSome) {
        return switch (this) {
            case None none -> ifEmpty.get();
            case Some<A>(A value) -> ifSome.apply(value);
        };
    }

    default Option<A> filter(final Predicate<A> predicate) {
        return this.flatMap(a -> predicate.test(a) ? new Some<>(a) : None);
    }

    default Option<A> filterNot(final Predicate<A> predicate) {
        return this.flatMap(a -> !predicate.test(a) ? new Some<>(a) : None);
    }

    // getOrXXX methods
    @Nullable
    default A getOrNull() {
        return this.getOrElse(() -> null);
    }

    @Nonnull
    default A getOrElse(final Supplier<A> defaultValue) {
        return this.fold(defaultValue, Function.identity());
    }

//    public <L> Either<L, A> toEither...

    default List<A> toList() {
        return this.fold(Collections::emptyList, List::of);
    }

    default Option<A> combine(final Option<A> other, final BiFunction<A, A, A> combine) {
        return switch (this) {
            case None none -> other;
            case Some<A>(var value) -> switch (other) {
                case None none -> new Some(value);
                case Some<A>(var otherValue) -> new Some(combine.apply(value, otherValue));
            };
        };
    }
}
