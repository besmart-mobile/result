package com.besmart.result.annimon.functions;


import com.annimon.stream.function.FunctionalInterface;

@FunctionalInterface
public interface Func3<T1, T2, T3, R> {
    R apply(T1 value1, T2 value2, T3 value3);
}