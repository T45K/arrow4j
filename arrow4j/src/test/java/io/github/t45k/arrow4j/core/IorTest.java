package io.github.t45k.arrow4j.core;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IorTest {

    @Test
    void fromNullables_should_build_a_correct_Ior() {
        assertEquals(new Ior.Left<Integer, String>(1), Ior.fromNullables(1, null));
        assertEquals(new Ior.Right<Integer, String>("1"), Ior.fromNullables(null, "1"));
        assertEquals(new Ior.Both<Integer, String>(1, "1"), Ior.fromNullables(1, "1"));
        assertNull(Ior.fromNullables(null, null));
    }

    @Test
    void isLeft_should_return_true_with_Left_and_false_otherwise() {
        assertTrue(Ior.left(1).isLeft());
        assertFalse(Ior.right(1).isLeft());
        assertFalse(Ior.both(1, "1").isLeft());
    }

    @Test
    void isLeft_with_predicate_should_return_true_with_Left_if_satisfies_the_predicate_and_false_otherwise() {
        final Predicate<Integer> predicate = i -> i % 2 == 0;

        assertTrue(Ior.left(2).isLeft(predicate));
        assertFalse(Ior.left(1).isLeft(predicate));
        assertFalse(Ior.<Integer, Integer>right(2).isLeft(predicate));
        assertFalse(Ior.both(2, 2).isLeft(predicate));
    }

    @Test
    void isRight_should_return_true_with_Right_and_false_otherwise() {
        assertFalse(Ior.left(1).isRight());
        assertTrue(Ior.right(1).isRight());
        assertFalse(Ior.both(1, "1").isRight());
    }

    @Test
    void isRight_with_predicate_should_return_true_with_Right_if_satisfies_the_predicate_and_false_otherwise() {
        final Predicate<Integer> predicate = i -> i % 2 == 0;
        assertFalse(Ior.<Integer, Integer>left(2).isRight(predicate));
        assertTrue(Ior.right(2).isRight(predicate));
        assertFalse(Ior.right(1).isRight(predicate));
        assertFalse(Ior.both(2, 2).isRight(predicate));
    }

    @Test
    void isBoth_should_return_true_with_Both_and_false_otherwise() {
        assertFalse(Ior.left(1).isBoth());
        assertFalse(Ior.right(1).isBoth());
        assertTrue(Ior.both(1, "1").isBoth());
    }

    @Test
    void isBoth_with_predicate_should_return_true_with_Both_if_satisfies_the_predicate_and_false_otherwise() {
        final Predicate<Integer> leftPredicate = i -> i % 2 == 0;
        final Predicate<String> rightPredicate = s -> (s.length() & 2) == 0;

        assertFalse(Ior.<Integer, String>left(2).isBoth(leftPredicate, rightPredicate));
        assertFalse(Ior.<Integer, String>right("11").isBoth(leftPredicate, rightPredicate));
        assertTrue(Ior.both(2, "11").isBoth(leftPredicate, rightPredicate));
        assertFalse(Ior.both(2, "1").isBoth(leftPredicate, rightPredicate));
        assertFalse(Ior.both(1, "11").isBoth(leftPredicate, rightPredicate));
        assertFalse(Ior.both(1, "1").isBoth(leftPredicate, rightPredicate));
    }

    @Test
    void fold() {
    }

    @Test
    void map() {
    }

    @Test
    void mapLeft() {
    }

    @Test
    void flatMap() {
    }

    @Test
    void swap() {
    }

    @Test
    void unwrap() {
    }

    @Test
    void toPair() {
    }

    @Test
    void toEither() {
    }

    @Test
    void getOrElse() {
    }

    @Test
    void getOrNull() {
    }

    @Test
    void leftOrNull() {
    }

    @Test
    void combine() {
    }
}