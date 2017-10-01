package com.oxsoft.sample

import io.reactivex.Maybe
import io.reactivex.Single

/** emptyの時は[mapper]がnullで呼び出される。[mapper]が[R]型のNonNull値を返すことで[Single]に変換する。 */
fun <T, R> Maybe<T>.mapToSingle(mapper: (T?) -> R) = this.map(mapper).switchIfEmpty(Single.fromCallable { mapper(null) })

/** 以下のようにしてしまうと、値がある時に[mapper]が2回評価されてしまう */
fun <T, R> Maybe<T>.mapToSingle2(mapper: (T?) -> R) = this.map(mapper).toSingle(mapper(null))
