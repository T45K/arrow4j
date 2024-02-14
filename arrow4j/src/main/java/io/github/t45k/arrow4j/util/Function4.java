package io.github.t45k.arrow4j.util;

@FunctionalInterface
public interface Function4<A, B, C, D, Z> {
    Z apply(final A a, final B b, final C c, final D d);
}

