package com.besmartmobile.result.annimon;


import com.annimon.stream.Optional;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;

public class OptionalExt {

    public static <T> Optional<T> some(T value) {
        return Optional.of(value);
    }

    public static <T> Optional<T> none() {
        return Optional.empty();
    }

    public static  <TOut, TValue> TOut match(Optional<TValue> optional,
                                             Function<TValue, TOut> onPresent,
                                             Supplier<TOut> onAbsent) {
        return optional.isPresent()
                ? onPresent.apply(optional.get())
                : onAbsent.get();
    }
}
