package com.oxsoft.sample

import io.reactivex.Maybe
import io.reactivex.Single

object Main {
    @JvmStatic
    fun main(vararg args: String) {
        println("-- MaybeMapNullable --")
        Maybe.empty<String>().mapNullable<String, String> {
            print("$it --> ")
            null
        }.subscribe(System.out::println, Throwable::printStackTrace, { println("empty") }) // null --> empty
        Maybe.just("value").mapNullable<String, String> {
            print("$it --> ")
            null
        }.subscribe(System.out::println, Throwable::printStackTrace, { println("empty") }) // value --> empty
        Maybe.empty<String>().mapNullable<String, String> {
            print("$it --> ")
            "value"
        }.subscribe(System.out::println, Throwable::printStackTrace, { println("empty") }) // null --> value
        Maybe.just("value").mapNullable<String, String> {
            print("$it --> ")
            "value"
        }.subscribe(System.out::println, Throwable::printStackTrace, { println("empty") }) // value --> value
        println()

        println("-- MaybeMapToSingle --")
        Maybe.empty<String>().mapToSingle {
            print("$it --> ")
            "value"
        }.subscribe(System.out::println, Throwable::printStackTrace) // null --> value
        Maybe.just("value").mapToSingle {
            print("$it --> ")
            "value"
        }.subscribe(System.out::println, Throwable::printStackTrace) // value --> value
        println()

        println(" -- MaybeFlatMapSingleSafe --")
        Maybe.empty<String>().flatMapSingleSafe {
            print("$it --> ")
            Single.just("value")
        }.subscribe(System.out::println, Throwable::printStackTrace) // null --> value
        Maybe.just("value").flatMapSingleSafe {
            print("$it --> ")
            Single.just("value")
        }.subscribe(System.out::println, Throwable::printStackTrace) // value --> value
        println()
    }
}
