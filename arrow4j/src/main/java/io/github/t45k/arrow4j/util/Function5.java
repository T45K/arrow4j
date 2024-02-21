package io.github.t45k.arrow4j.util;

@FunctionalInterface
public interface Function5<A, B, C, D, E, Z> {
    Z apply(final A a, final B b, final C c, final D d, final E e);
}

