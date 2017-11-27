package com.besmartmobile.result.annimon;


public final class Unit {

    private static Unit instance = new Unit();

    public static Unit unit() {
        return instance;
    }

    public static Unit ignore(Object object) {
        return instance;
    }
}
