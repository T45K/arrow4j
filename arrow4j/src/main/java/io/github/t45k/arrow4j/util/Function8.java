package io.github.t45k.arrow4j.util;

@FunctionalInterface
public interface Function8<A, B, C, D, E, F, G, H, Z> {
    Z apply(final A a, final B b, final C c, final D d, final E e, final F f, final G g, final H h);
}

