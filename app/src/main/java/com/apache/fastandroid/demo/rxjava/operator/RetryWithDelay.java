package com.apache.fastandroid.demo.rxjava.operator;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * Created by Jerry on 2022/1/2.
 */
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
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Throwable {
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    Logger.d( "get error, it will try after " + retryDelayMillis
                            + " millisecond, retry count " + retryCount);
                    return Observable.timer(retryDelayMillis,
                            TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);

            }
        });
    }



}

