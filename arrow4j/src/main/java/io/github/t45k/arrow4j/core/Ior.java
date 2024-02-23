package io.github.t45k.arrow4j.core;

import io.github.t45k.arrow4j.util.Pair;
import jakarta.annotation.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Ior<A, B> {
    record Left<A, B>(A value) implements Ior<A, B> {
    }

    record Right<A, B>(B value) implements Ior<A, B> {
    }

    record Both<A, B>(A leftValue, B rightValue) implements Ior<A, B> {
    }

    @Nullable
    static <A, B> Ior<A, B> fromNullables(@Nullable final A a, @Nullable B b) {
        throw new RuntimeException();
    }

    static <A, B> Left<A, B> left(A value) {
        throw new RuntimeException();
    }

    static <A, B> Right<A, B> right(B value) {
        throw new RuntimeException();
    }

    static <A, B> Both<A, B> both(final A a, final B b) {
        throw new RuntimeException();
    }

    static <A, B> Both<A, B> both(final Pair<A, B> pair) {
        throw new RuntimeException();
    }

    // leftNel
    // bothNel

    static <A, B> Ior<A, B> flatten(final Ior<A, Ior<A, B>> nested) {
        throw new RuntimeException();
    }

    default boolean isLeft() {
        throw new RuntimeException();
    }

    default boolean isLeft(final Predicate<A> predicate) {
        throw new RuntimeException();
    }

    default boolean isRight() {
        throw new RuntimeException();
    }

    default boolean isRight(final Predicate<B> predicate) {
        throw new RuntimeException();
    }

    default boolean isBoth() {
        throw new RuntimeException();
    }

    default boolean isBoth(final Predicate<A> leftPredicate, final Predicate<B> rightPredicate) {
        throw new RuntimeException();
    }

    default <C> C fold(final Function<A, C> fa, final Function<B, C> fb, final BiFunction<A, B, C> fab) {
        throw new RuntimeException();
    }

    default <C> Ior<A, C> map(final Function<B, C> f) {
        throw new RuntimeException();
    }

    default <C> Ior<C, B> mapLeft(final Function<A, C> fa) {
        throw new RuntimeException();
    }

    default <C> Ior<B, C> flatMap(final BiFunction<A, A, A> combine, final Function<B, Ior<A, C>> f) {
        throw new RuntimeException();
    }

    default Ior<B, A> swap() {
        throw new RuntimeException();
    }

    default Either<Either<A, B>, Pair<A, B>> unwrap() {
        throw new RuntimeException();
    }

    default Pair<A, B> toPair() {
        throw new RuntimeException();
    }

    default Either<A, B> toEither() {
        throw new RuntimeException();
    }

    default B getOrElse(final Function<A, B> defaultValue) {
        throw new RuntimeException();
    }

    @Nullable
    default B getOrNull() {
        throw new RuntimeException();
    }

    @Nullable
    default B leftOrNull() {
        throw new RuntimeException();
    }

    default Ior<A, B> combine(final Ior<A, B> other, final BiFunction<A, A, A> combineA, final BiFunction<B, B, B> combineB) {
        throw new RuntimeException();
    }
}
