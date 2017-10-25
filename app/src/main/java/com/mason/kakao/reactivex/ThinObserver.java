package com.mason.kakao.reactivex;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by kakao on 2017. 10. 25..
 */

public abstract class ThinObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // Empty
    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public abstract void onError(@NonNull Throwable e);

    @Override
    public void onComplete() {
        // Empty
    }
}
