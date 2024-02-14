package io.github.t45k.arrow4j.core

import spock.lang.Specification

class EitherTest extends Specification {

    def 'aa'() {
        Either<String, String> a = new Either.Right<>('aaa')
    }
}
