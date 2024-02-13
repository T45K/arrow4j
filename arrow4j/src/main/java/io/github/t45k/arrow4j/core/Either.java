package io.github.t45k.arrow4j.core;

import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "unchecked"})
public sealed interface Either<A, B> {

    record Left<A, B>(A value) implements Either<A, B> {
    }

    record Right<A, B>(B value) implements Either<A, B> {
    }

    default boolean isLeft() {
        return this instanceof Left<A, B>;
    }

    default boolean isRight() {
        return this instanceof Right<A, B>;
    }

    default boolean isLeft(final Predicate<A> predicate) {
        return this instanceof Left<A, B> left && predicate.test(left.value);
    }

    default boolean isRight(final Predicate<B> predicate) {
        return this instanceof Right<A, B> right && predicate.test(right.value);
    }

    default <C> C fold(final Function<A, C> ifLeft, final Function<B, C> ifRight) {
        return switch (this) {
            case Right<A, B>(var value) -> ifRight.apply(value);
            case Left<A, B>(var value) -> ifLeft.apply(value);
        };
    }

    default Either<B, A> swap() {
        return fold(Right::new, Left::new);
    }

    default <C> Either<A, C> map(final Function<B, C> f) {
        return switch (this) {
            case Either.Right<A, B>(var value) -> new Right<>(f.apply(value));
            case Either.Left<A, B> left -> (Either<A, C>) left;
        };
    }

    default <C> Either<A, C> flatMap(final Function<B, Either<A, C>> f) {
        return switch (this) {
            case Right<A, B>(var value) -> f.apply(value);
            case Left<A, B> left -> (Either<A, C>) left;
        };
    }
}
