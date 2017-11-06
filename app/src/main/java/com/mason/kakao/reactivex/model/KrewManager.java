package com.mason.kakao.reactivex.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kakao on 2017. 10. 25..
 */

public class KrewManager {
    private static KrewManager INSTANCE;

    private List<Krew> krews;

    public static KrewManager getInstance() {
        if(INSTANCE == null) {
            synchronized (KrewManager.class) {
                if(INSTANCE == null) {
                    INSTANCE = new KrewManager();
                }
            }
        }
        return INSTANCE;
    }

    private KrewManager() {
        krews = new ArrayList<>();
        String[] names = { "Mason", "Daniel", "Sian", "Lupin", "Landon", "Aiden", "Coutney", "Jaidon", "Jimmy", "Tedy", "Ryan", "Apeach", "Frodo", "Neo", "Tube", "Con", "Jay-G"};
        int[] counts = new int[names.length];
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int index = Math.abs(random.nextInt()) % names.length;
            counts[index]++;
            krews.add(new Krew(names[index] + counts[index]));
        }
    }

    public Observable<List<Krew>> getKrews() {
        return Observable.just(krews)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Krew>> findKrews(final String name) {
        return Observable.just(krews)
                .flatMap(Observable::fromIterable)
                .filter(krew -> krew.getName().startsWith(name))
                .toList()
                .toObservable()
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void removeKrew(Krew krew) {
        int index = krews.indexOf(krew);
        if(index > -1) {
            krews.remove(index);
        }
    }
}
