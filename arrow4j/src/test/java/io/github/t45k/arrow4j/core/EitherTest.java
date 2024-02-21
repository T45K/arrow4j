package io.github.t45k.arrow4j.core;

import org.junit.jupiter.api.Test;

import static io.github.t45k.arrow4j.core.Option.None;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EitherTest {
    private static final Either<Integer, Integer> left = Either.left(1);
    private static final Either<Integer, Integer> right = Either.right(1);

    @Test
    public void isLeft_should_return_ture_if_Left_and_false_if_Right() {
        assertTrue(left.isLeft());
        assertFalse(right.isLeft());
    }

    @Test
    public void isLeft_with_predicate() {
        assertTrue(Either.left("java").isLeft(it -> it.startsWith("j")));
        assertFalse(Either.left("java").isLeft(it -> it.startsWith("k")));
        assertFalse(Either.<String, String>right("java").isLeft(it -> it.startsWith("java")));
    }

    @Test
    public void isRight_should_return_true_if_Right_and_false_if_Left() {
        assertTrue(right.isRight());
        assertFalse(left.isRight());
    }

    @Test
    public void isRight_with_predicate() {
        assertTrue(Either.right("java").isRight(it -> it.startsWith("j")));
        assertFalse(Either.right("java").isRight(it -> it.startsWith("k")));
        assertFalse(Either.<String, String>left("java").isRight(it -> it.startsWith("j")));
    }

    @Test
    public void fold_should_apply_first_op_if_Left_and_second_op_if_Right() {
        assertEquals(2, right.fold(it -> it + 2, it -> it + 1).intValue());
        assertEquals(3, left.fold(it -> it + 2, it -> it + 1).intValue());
    }

    @Test
    public void swap_should_interchange_values() {
        assertEquals(Either.right(1), Either.left(1).swap());
        assertEquals(Either.left(2), Either.right(2).swap());
    }

    @Test
    public void map_should_alter_right_instance_only() {
        assertEquals(Either.right(2), right.map(it -> it + 1));
        assertEquals(Either.left(1), left.map(it -> it + 1));
    }

    @Test
    public void mapLeft_should_alter_left_instance_only() {
        assertEquals(Either.right(1), right.mapLeft(it -> it + 1));
        assertEquals(Either.left(2), left.mapLeft(it -> it + 1));
    }

    @Test
    public void onRight() {
        final int[] effect = new int[1];

        final Either<Integer, Integer> resRight = right.onRight(it -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(right, resRight);

        final Either<Integer, Integer> resLeft = left.onRight(it -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(left, resLeft);
    }

    @Test
    public void onLeft() {
        final int[] effect = new int[1];

        final Either<Integer, Integer> resRight = right.onLeft(it -> effect[0]++);
        assertEquals(0, effect[0]);
        assertEquals(right, resRight);

        final Either<Integer, Integer> resLeft = left.onLeft(it -> effect[0]++);
        assertEquals(1, effect[0]);
        assertEquals(left, resLeft);
    }

    @Test
    public void getOrNull() {
        assertEquals(1, right.getOrNull());
        assertNull(left.getOrNull());
    }

    @Test
    public void leftOrNull() {
        assertNull(right.leftOrNull());
        assertEquals(1, left.leftOrNull());
    }

    @Test
    public void getOrNone() {
        assertEquals(new Option.Some<>(1), right.getOrNone());
        assertEquals(None, left.getOrNone());
    }

    @Test
    public void flatMap_should_map_right_instance_only() {
        assertEquals(Either.right(2), right.flatMap(it -> Either.right(it + 1)));
        assertEquals(left, left.flatMap(it -> Either.right(it + 1)));
        assertEquals(left, right.flatMap(unused -> left));
    }

    @Test
    public void getOrElse() {
        assertEquals(1, right.getOrElse(it -> 2));
        assertEquals(2, left.getOrElse(it -> 2));
    }

    @Test
    public void combine_should_combine_2_eithers() {
        final Either<Integer, Integer> right1 = Either.right(1);
        final Either<Integer, Integer> right2 = Either.right(2);
        final Either<Integer, Integer> left1 = Either.left(3);
        final Either<Integer, Integer> left2 = Either.left(4);

        assertEquals(Either.right(3), right1.combine(right2, Integer::sum, Integer::sum));
        assertEquals(left2, right1.combine(left2, Integer::sum, Integer::sum));
        assertEquals(left1, left1.combine(right2, Integer::sum, Integer::sum));
        assertEquals(Either.left(7), left1.combine(left2, Integer::sum, Integer::sum));
    }
}
