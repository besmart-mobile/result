package com.besmart.result.annimon;


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
}
