package com.oxsoft.sample

import io.reactivex.Maybe
import io.reactivex.Single

/** emptyの時は[mapper]がnullで呼び出される */
fun <T, R> Maybe<T>.flatMapSingleSafe(mapper: (T?) -> Single<R>): Single<R> =
        this.flatMapSingleElement(mapper).switchIfEmpty(Single.just(Unit).flatMap { mapper(null) })

/** 以下のようにしてしまうと、mapper(null)が呼び出し時に評価されてしまう */
fun <T, R> Maybe<T>.flatMapSingleSafe2(mapper: (T?) -> Single<R>): Single<R> =
        this.flatMapSingleElement(mapper).switchIfEmpty(mapper(null))
