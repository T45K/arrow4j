package io.github.t45k.arrow4j.util;

@FunctionalInterface
public interface Function3<A, B, C, Z> {
    Z apply(final A a, final B b, final C c);
}

