package io.github.t45k.arrow4j.core;

import io.github.t45k.arrow4j.util.Function10;
import io.github.t45k.arrow4j.util.Function3;
import io.github.t45k.arrow4j.util.Function4;
import io.github.t45k.arrow4j.util.Function5;
import io.github.t45k.arrow4j.util.Function6;
import io.github.t45k.arrow4j.util.Function7;
import io.github.t45k.arrow4j.util.Function8;
import io.github.t45k.arrow4j.util.Function9;
import jakarta.annotation.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.t45k.arrow4j.core.Option.None;
import static io.github.t45k.arrow4j.core.Option.Some;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public sealed interface Either<A, B> {

    record Left<A>(A value) implements Either {
    }

    static <A, B> Either<A, B> left(final A value) {
        return new Left<>(value);
    }

    record Right<B>(B value) implements Either {
    }

    static <A, B> Either<A, B> right(final B value) {
        return new Right<>(value);
    }

    Right<Void> voidRight = new Right<>(null);

    static <A, B> Either<A, B> flatten(final Either<A, Either<A, B>> either) {
        return either.flatMap(Function.identity());
    }

    static <A> A merge(final Either<A, A> either) {
        return either.fold(Function.identity(), Function.identity());
    }

    // static <R> Either<Throwable, R> tryCatch(final Supplier<R> f)

    static <E, A, B, Z> Either<A, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final BiFunction<A, B, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, voidRight, (aa, bb, unused) -> transform.apply(aa, bb)
        );
    }

    static <E, A, B, C, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Function3<A, B, C, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, voidRight, (aa, bb, cc, unused) -> transform.apply(aa, bb, cc)
        );
    }

    static <E, A, B, C, D, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Function4<A, B, C, D, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, voidRight, (aa, bb, cc, dd, unused) -> transform.apply(aa, bb, cc, dd)
        );
    }

    static <E, A, B, C, D, EE, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Function5<A, B, C, D, EE, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, e, voidRight,
            (aa, bb, cc, dd, ee, unused) -> transform.apply(aa, bb, cc, dd, ee)
        );
    }

    static <E, A, B, C, D, EE, F, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Either<E, F> f,
        final Function6<A, B, C, D, EE, F, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, e, f, voidRight,
            (aa, bb, cc, dd, ee, ff, unused) -> transform.apply(aa, bb, cc, dd, ee, ff)
        );
    }

    static <E, A, B, C, D, EE, F, G, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Either<E, F> f,
        final Either<E, G> g,
        final Function7<A, B, C, D, EE, F, G, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, e, f, g, voidRight,
            (aa, bb, cc, dd, ee, ff, gg, unused) -> transform.apply(aa, bb, cc, dd, ee, ff, gg)
        );
    }

    static <E, A, B, C, D, EE, F, G, H, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Either<E, F> f,
        final Either<E, G> g,
        final Either<E, H> h,
        final Function8<A, B, C, D, EE, F, G, H, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, e, f, g, h, voidRight,
            (aa, bb, cc, dd, ee, ff, gg, hh, unused) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh)
        );
    }

    static <E, A, B, C, D, EE, F, G, H, I, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Either<E, F> f,
        final Either<E, G> g,
        final Either<E, H> h,
        final Either<E, I> i,
        final Function9<A, B, C, D, EE, F, G, H, I, Z> transform
    ) {
        return zipOrAccumulate(combine, a, b, c, d, e, f, g, h, i, voidRight,
            (aa, bb, cc, dd, ee, ff, gg, hh, ii, unused) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh, ii)
        );
    }

    @SuppressWarnings("DuplicatedCode")
    static <E, A, B, C, D, EE, F, G, H, I, J, Z> Either<E, Z> zipOrAccumulate(
        final BiFunction<E, E, E> combine,
        final Either<E, A> a,
        final Either<E, B> b,
        final Either<E, C> c,
        final Either<E, D> d,
        final Either<E, EE> e,
        final Either<E, F> f,
        final Either<E, G> g,
        final Either<E, H> h,
        final Either<E, I> i,
        final Either<E, J> j,
        final Function10<A, B, C, D, EE, F, G, H, I, J, Z> transform
    ) {
        if (a instanceof Either.Right<A>(var valueA) &&
            b instanceof Either.Right<B>(var valueB) &&
            c instanceof Either.Right<C>(var valueC) &&
            d instanceof Either.Right<D>(var valueD) &&
            e instanceof Either.Right<EE>(var valueE) &&
            f instanceof Either.Right<F>(var valueF) &&
            g instanceof Either.Right<G>(var valueG) &&
            h instanceof Either.Right<H>(var valueH) &&
            i instanceof Either.Right<I>(var valueI) &&
            j instanceof Either.Right<J>(var valueJ)
        ) {
            return new Right<>(transform.apply(valueA, valueB, valueC, valueD, valueE, valueF, valueG, valueH, valueI, valueJ));
        }

        E accumulatedError = null;
        final BiFunction<E, E, E> combineIfNotNull = (e1, e2) -> e1 == null ? e2 : combine.apply(e1, e2);
        if (a instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (b instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (c instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (d instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (e instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (f instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (g instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (h instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (i instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);
        if (j instanceof Either.Left<E>(var error)) accumulatedError = combineIfNotNull.apply(accumulatedError, error);

        return new Left(accumulatedError);
    }

    default boolean isLeft() {
        return this instanceof Left<A>;
    }

    default boolean isRight() {
        return this instanceof Right<A>;
    }

    default boolean isLeft(final Predicate<A> predicate) {
        return this instanceof Left<A> left && predicate.test(left.value);
    }

    default boolean isRight(final Predicate<B> predicate) {
        return this instanceof Right<B> right && predicate.test(right.value);
    }

    default <C> C fold(final Function<A, C> ifLeft, final Function<B, C> ifRight) {
        return switch (this) {
            case Right<B>(var value) -> ifRight.apply(value);
            case Left<A>(var value) -> ifLeft.apply(value);
        };
    }

    default Either<B, A> swap() {
        return fold(Right::new, Left::new);
    }

    default <C> Either<A, C> map(final Function<B, C> f) {
        return switch (this) {
            case Either.Right<B>(var value) -> new Right<>(f.apply(value));
            case Either.Left<B> left -> (Either<A, C>) left;
        };
    }

    default <C> Either<C, B> mapLeft(final Function<A, C> f) {
        return this.fold(it -> new Left<>(f.apply(it)), Right::new);
    }

    default Either<A, B> onRight(final Consumer<B> action) {
        if (this instanceof Right<B>(var value)) {
            action.accept(value);
        }
        return this;
    }

    default Either<A, B> onLeft(final Consumer<A> action) {
        if (this instanceof Left<A>(var value)) {
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

    // default Ior<A, B> toIor()

    default <C> Either<A, C> flatMap(final Function<B, Either<A, C>> f) {
        return switch (this) {
            case Right<B>(var value) -> f.apply(value);
            case Left<A> left -> (Either<A, C>) left;
        };
    }

    default B getOrElse(final Function<A, B> defaultValue) {
        return this.fold(defaultValue, Function.identity());
    }
}
