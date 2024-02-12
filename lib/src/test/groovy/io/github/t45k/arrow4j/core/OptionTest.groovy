package io.github.t45k.arrow4j.core

import io.github.t45k.arrow4j.core.Option.Some
import io.github.t45k.arrow4j.util.Pair
import spock.lang.Specification

class OptionTest extends Specification {
    private static final Option<String> some = new Some("java")
    private static final Option<String> none = Option.@None

    def 'fromOptional should work for both present and empty values of optional'() {
        expect:
        Option.fromOptional(a) == created

        where:
        a                || created
        Optional.of(1)   || new Some(1)
        Optional.empty() || Option.@None
    }

    def 'fromNullable should work for both null and non-null values of nullable types'() {
        expect:
        Option.fromNullable(a) == created

        where:
        a    || created
        1    || new Some(1)
        null || Option.@None
    }

    def 'filterIsInstance'() {
        expect:
        Option.filterIsInstance(option, type) == filtered

        where:
        option | type   || filtered
        some   | String || new Some("java")
        some   | int    || Option.@None
        none   | String || Option.@None
        none   | int    || Option.@None
    }

    def 'flatten'() {
        expect:
        Option.flatten(new Some(option)) == flattened

        where:
        option || flattened
        some   || new Some("java")
        none   || Option.@None
    }

    def 'widen'() {
        given:
        // noinspection GroovyAssignabilityCheck
        Option<CharSequence> widen = Option.widen(some)

        expect:
        widen.map { it.length() } == new Some(4)
    }

    def 'toMap'() {
        given:
        Option<Pair<String, String>> some = new Some(new Pair("key", "value"))
        Option<Pair<String, String>> none = Option.@None

        expect: 'Some'
        Option.toMap(some) == Map.of("key", "value")

        and: 'None'
        Option.toMap(none) == Collections.emptyMap()
    }

    def 'tryCatch should return Some(result) when f does not throw'() {
        given:
        def recover = { Option.@None }

        expect:
        Option.tryCatch(recover) { 1 } == new Some(1)
    }

    def 'tryCatch should return Some(recoverValues) when f throws'() {
        given:
        def exception = new Exception("Boom!")
        def recoverValue = 10
        def recover = { new Some(recoverValue) }

        expect:
        Option.tryCatch(recover) { throw exception } == new Some(recoverValue)
    }

    def 'tryCatchOrNone should return Some(result) when f does not throw'() {
        expect:
        Option.tryCatchOrNone { 1 } == new Some(1)
    }

    def 'tryCatchOrNone should return None when f throws'() {
        given:
        def exception = new Exception("Boom!")

        expect:
        Option.tryCatchOrNone { throw exception } == Option.@None
    }

    def 'onNone applies effects returning the original value'() {
        given:
        def effect = 0

        when:
        def res = option.onNone { effect++ }

        then:
        effect == expectedEffect
        res == option

        where:
        option || expectedEffect
        some   || 0
        none   || 1
    }

    def 'onSome applies effects returning the original value'() {
        given:
        def effect = 0

        when:
        def res = option.onSome { effect++ }

        then:
        effect == expectedEffect
        res == option

        where:
        option || expectedEffect
        some   || 1
        none   || 0
    }

    def 'isNone should return true if None and false if Some'() {
        expect:
        !some.isNone()
        none.isNone()
    }

    def 'isSome should return true if Some and false if None'() {
        expect:
        some.isSome()
        !none.isSome()
    }

    def 'isSome with predicate'() {
        expect:
        option.isSome(predicate) == match

        where:
        option | predicate              || match
        some   | { it.startsWith('j') } || true
        some   | { it.startsWith('k') } || false
        none   | { it.startsWith('j') } || false
    }

    def 'map'() {
        expect:
        option.map(String::toUpperCase) == mapped

        where:
        option | mapped
        some   | new Some('JAVA')
        none   | Option.@None
    }

    def 'flatMap'() {
        expect:
        option.flatMap { new Some(it.toUpperCase()) } == flatMapped

        where:
        option || flatMapped
        some   || new Some('JAVA')
        none   || Option.@None
    }

    def 'fold'() {
        expect:
        option.fold { 0 } { it.length() } == sumOfStrLength

        where:
        option || sumOfStrLength
        some   || 4
        none   || 0
    }

    def 'filter'() {
        expect:
        option.filter(predicate) == filtered

        where:
        option | predicate              | filtered
        some   | { it.startsWith('j') } | new Some('java')
        some   | { it == 'kotlin' }     | Option.@None
        none   | { it.startsWith('j') } | Option.@None
    }

    def 'filterNot'() {
        expect:
        option.filterNot(predicate) == filtered

        where:
        option | predicate              | filtered
        some   | { it.startsWith('j') } | Option.@None
        some   | { it == 'kotlin' }     | new Some('java')
        none   | { it.startsWith('j') } | Option.@None
    }

    def 'getOrNull'() {
        expect:
        option.getOrNull() == obj

        where:
        option || obj
        some   || 'java'
        none   || null
    }

    def 'getOrElse'() {
        expect:
        option.getOrElse { 'kotlin' } == obj

        where:
        option || obj
        some   || 'java'
        none   || 'kotlin'
    }

    def 'toList'() {
        expect:
        option.toList() == list

        where:
        option || list
        some   || List.of('java')
        none   || Collections.emptyList()
    }
}
