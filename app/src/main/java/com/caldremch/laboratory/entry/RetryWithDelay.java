package com.caldremch.laboratory.entry;


import android.util.Log;

import java.io.EOFException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {


    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }


    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Throwable {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Throwable {
//                        if (++retryCount <= maxRetries && throwable instanceof EOFException) {
                        if (++retryCount <= maxRetries && throwable != null && (throwable instanceof EOFException || throwable.getCause() instanceof EOFException)) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            Log.d("applyTAG", "get error, it will try after " + retryDelayMillis
                                    + " millisecond, retry count " + retryCount);
                            return Observable.timer(0,
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}