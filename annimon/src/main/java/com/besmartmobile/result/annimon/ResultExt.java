package com.besmartmobile.result.annimon;


import com.annimon.stream.function.BiFunction;
import com.besmartmobile.result.annimon.functions.Func3;
import com.besmartmobile.result.annimon.functions.Func4;
import com.besmartmobile.result.annimon.functions.Func5;

public class ResultExt {

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> ok(TSuccess success) {
        return Result.withValue(success);
    }

    public static <TFailure> Result<Unit, TFailure> ok() {
        return Result.withValue(Unit.unit());
    }

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> fail(TFailure error) {
        return Result.withError(error);
    }

    public static <TSuccess> Result<TSuccess, Unit> fail() {
        return Result.withError(Unit.unit());
    }

    public static <S1, S2, S, F> Result<S, F> combine(Result<S1, F> r1,
                                                      Result<S2, F> r2,
                                                      BiFunction<S1, S2, S> combiner) {
        return r1.combine(r2, combiner);
    }

    public static <S1, S2, S3, S, F> Result<S, F> combine(Result<S1, F> r1,
                                                          Result<S2, F> r2,
                                                          Result<S3, F> r3,
                                                          Func3<S1, S2, S3, S> combiner) {
        return r1.combine(r2, r3, combiner);
    }

    public static <S1, S2, S3, S4, S, F> Result<S, F> combine(Result<S1, F> r1,
                                                              Result<S2, F> r2,
                                                              Result<S3, F> r3,
                                                              Result<S4, F> r4,
                                                              Func4<S1, S2, S3, S4, S> combiner) {
        return r1.combine(r2, r3, r4, combiner);
    }

    public static <S1, S2, S3, S4, S5, S, F> Result<S, F> combine(Result<S1, F> r1,
                                                                  Result<S2, F> r2,
                                                                  Result<S3, F> r3,
                                                                  Result<S4, F> r4,
                                                                  Result<S5, F> r5,
                                                                  Func5<S1, S2, S3, S4, S5, S> combiner) {
        return r1.combine(r2, r3, r4, r5, combiner);
    }

}
