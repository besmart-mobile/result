package com.besmartmobile.result.annimon.functions;


import com.annimon.stream.function.FunctionalInterface;

@FunctionalInterface
public interface Func5<T1, T2, T3, T4, T5, R> {
    R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5);
}