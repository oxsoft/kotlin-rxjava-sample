package com.oxsoft.sample

import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable

/** emptyの時は[mapper]がnullで呼び出される。また[mapper]がnullを返すとemptyになる。 */
fun <T, R> Maybe<T>.mapNullable(mapper: (T?) -> R?): Maybe<R> = this.lift { observer ->
    object : MaybeObserver<T> {
        override fun onSuccess(t: T) {
            val r = mapper(t)
            if (r == null) {
                observer.onComplete()
            } else {
                observer.onSuccess(r)
            }
        }

        override fun onError(e: Throwable) = observer.onError(e)

        override fun onComplete() {
            val r = mapper(null)
            if (r == null) {
                observer.onComplete()
            } else {
                observer.onSuccess(r)
            }
        }

        override fun onSubscribe(d: Disposable) = observer.onSubscribe(d)
    }
}

/** 以下のようにしてしまうと、[mapper]がnullを返す時に[mapper]が2回評価されてしまう */
fun <T, R> Maybe<T>.mapNullable2(mapper: (T?) -> R?): Maybe<R> =
        this.flatMap { Maybe.fromCallable<R> { mapper(it) } }.switchIfEmpty(Maybe.fromCallable { mapper(null) })
