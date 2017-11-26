package com.besmart.result.annimon;


public final class Unit {

    private static Unit instance = new Unit();

    public static Unit unit() {
        return instance;
    }
}
