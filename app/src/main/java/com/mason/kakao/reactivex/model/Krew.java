package com.mason.kakao.reactivex.model;

/**
 * Created by kakao on 2017. 10. 25..
 */

public class Krew {
    private String name;

    public Krew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
