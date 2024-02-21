package io.github.t45k.arrow4j.core;

import io.github.t45k.arrow4j.util.Pair;
import jakarta.annotation.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.t45k.arrow4j.core.Option.None;
import static io.github.t45k.arrow4j.core.Option.Some;

@SuppressWarnings({"unused", "unchecked"})
public sealed interface Either<A, B> {

    // Unfortunately, Java doesn't have bottom type (e.g., Nothing in Kotlin).
    // Therefore, we need to let Left and Right types have both A and B.
    record Left<A, B>(A value) implements Either<A, B> {
    }

    static <A, B> Either<A, B> left(final A value) {
        return (new Left<>(value));
    }

    record Right<A, B>(B value) implements Either<A, B> {
    }

    static <A, B> Either<A, B> right(final B value) {
        return new Right<>(value);
    }

    static <A, B> Either<A, B> flatten(final Either<A, Either<A, B>> either) {
        return either.flatMap(Function.identity());
    }

    static <A> A merge(final Either<A, A> either) {
        return either.fold(Function.identity(), Function.identity());
    }

    // TODO: static <R> Either<Throwable, R> tryCatch(final Supplier<R> f) will be implemented after implementing `raise`
    // same as catchOrThrow

    static <A, C, B extends C> Either<A, C> widen(final Either<A, B> either) {
        return (Either<A, C>) either;
    }

    static <AA, A extends AA, B> Either<AA, B> leftWiden(final Either<A, B> either) {
        return (Either<AA, B>) either;
    }

    // bisequence, bisequenceOption, bisequenceNullable are omitted

    // TODO: zipOrAccumulate
    // TODO: zipOrAccumulateNonEmptyList will be implemented after implementing NonEmptyList

    default boolean isLeft() {
        return this instanceof Left;
    }

    default boolean isRight() {
        return this instanceof Right;
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
            case Right<A, B>(var value) -> new Right<>(f.apply(value));
            case Left<A, B> left -> (Either<A, C>) left;
        };
    }

    default <C> Either<C, B> mapLeft(final Function<A, C> f) {
        return this.fold(it -> new Left<>(f.apply(it)), Right::new);
    }

    default Either<A, B> onRight(final Consumer<B> action) {
        if (this instanceof Right<A, B>(var value)) {
            action.accept(value);
        }
        return this;
    }

    default Either<A, B> onLeft(final Consumer<A> action) {
        if (this instanceof Left<A, B>(var value)) {
            action.accept(value);
        }
        return this;
    }

    @Nullable
    default B getOrNull() {
        return this.getOrElse((unused) -> null);
    }

    @Nullable
    default A leftOrNull() {
        return this.fold(Function.identity(), (unused) -> null);
    }

    default Option<B> getOrNone() {
        return this.fold(unused -> None, Some::new);
    }

    // TODO: default Ior<A, B> toIor() will be implemented after implementing Ior

    default <C> Either<A, C> flatMap(final Function<B, Either<A, C>> f) {
        return switch (this) {
            case Right<A, B>(var value) -> f.apply(value);
            case Left<A, B> left -> (Either<A, C>) left;
        };
    }

    default B getOrElse(final Function<A, B> defaultValue) {
        return this.fold(defaultValue, Function.identity());
    }

    default Either<A, B> combine(final Either<A, B> other, final BiFunction<A, A, A> combineLeft, final BiFunction<B, B, B> combineRight) {
        return switch (new Pair<>(this, other)) {
            case Pair(Left<A, B>(var thisLeft), Left<A, B>(var otherLeft)) ->
                Either.left(combineLeft.apply(thisLeft, otherLeft));
            case Pair(Right<A, B>(var thisRight), Right<A, B>(var otherRight)) ->
                Either.right(combineRight.apply(thisRight, otherRight));
            case Pair(Right<A, B> unused, Left<A, B> otherLeft) -> otherLeft;
            case Pair(Left<A, B> thisLeft, Right<A, B> unused) -> thisLeft;
        };
    }
}
