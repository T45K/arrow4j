package io.github.t45k.arrow4j.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Pair<T1, T2>(T1 first, T2 second) {
    public static <T1, T2> Map<T1, T2> toMap(final List<Pair<T1, T2>> list) {
        return list.stream().collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));
    }
}
