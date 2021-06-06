package com.caldremch.laboratory.entry

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.functions.Function
import java.io.EOFException
import java.util.concurrent.TimeUnit

class RetryWhenEOF(private val maxRetries: Int, private val retryDelayMillis: Int) :
    Function<Observable<out Throwable?>, Observable<*>> {
    private var retryCount = 0

    @Throws(Throwable::class)
    override fun apply(observable: Observable<out Throwable?>): Observable<*> {
        return observable
            .flatMap(Function<Throwable?, ObservableSource<*>> { throwable -> //                        if (++retryCount <= maxRetries && throwable instanceof EOFException) {
                if (++retryCount <= maxRetries && throwable != null && (throwable is EOFException || throwable.cause is EOFException)) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    Log.d(
                        "applyTAG", "get error, it will try after " + retryDelayMillis
                                + " millisecond, retry count " + retryCount
                    )
                    return@Function Observable.timer(
                        0,
                        TimeUnit.MILLISECONDS
                    )
                }
                // Max retries hit. Just pass the error along.
                Observable.error<Any>(throwable)
            })
    }
}