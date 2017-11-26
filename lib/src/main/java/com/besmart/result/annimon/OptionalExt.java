package com.besmart.result.annimon;


import com.annimon.stream.Optional;

public class OptionalExt {

    public static <T> Optional<T> some(T value) {
        return Optional.of(value);
    }

    public static <T> Optional<T> none() {
        return Optional.empty();
    }
}
