package com.besmartmobile.result.annimon;


import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.annimon.stream.function.Supplier;
import com.besmartmobile.result.annimon.functions.*;

public class Failure<TSuccess, TFailure> extends Result<TSuccess, TFailure> {
    private final TFailure error;

    public Failure(final TFailure error) {
        this.error = error;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public TSuccess getValue() {
        throw new FailedResultHasNoValueException(getError());
    }

    @Override
    public TFailure getError() {
        return error;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Result (");
        result.append("Error: ");
        result.append(getError());
        result.append(')');
        return result.toString();
    }

    @Override
    public Result<?, TFailure> combine(
            final Result<?, TFailure> otherResult) {
        return this;
    }

    @Override
    public <U, R> Result<R, TFailure> combine(final Result<U, TFailure> otherResult,
                                              final BiFunction<TSuccess, U, R> biFunction) {
        return Result.withError(getError());
    }

    @Override
    public <T1, T2, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1,
                                                   Result<T2, TFailure> otherResult2,
                                                   Func3<TSuccess, T1, T2, R> combiner) {
        return Result.withError(getError());
    }

    @Override
    public <T1, T2, T3, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1, Result<T2, TFailure> otherResult2, Result<T3, TFailure> otherResult3, Func4<TSuccess, T1, T2, T3, R> combiner) {
        return Result.withError(getError());
    }

    @Override
    public <T1, T2, T3, T4, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1, Result<T2, TFailure> otherResult2, Result<T3, TFailure> otherResult3, Result<T4, TFailure> otherResult4, Func5<TSuccess, T1, T2, T3, T4, R> combiner) {
        return Result.withError(getError());
    }

    @Override
    public <T> T match(Function<TSuccess, T> onSuccess, Function<TFailure, T> onFailure) {
        return onFailure.apply(error);
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz) {
        return new Failure<>(getError());
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function) {
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error) {
        return this;
    }

    @Override
    public <T> Result<T, TFailure> flatMap(
            final Function<TSuccess, Result<T, TFailure>> function)

    {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> map(final Function<TSuccess, T> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error) {
        return new Failure<>(getError());
    }

    @Override
    public Optional<TSuccess> toOptional() {
        return Optional.empty();
    }

    @Override
    public Optional<TFailure> getOptionalError() {
        return Optional.of(error);
    }

    @Override
    public <T> Result<TSuccess, T> mapError(Function<TFailure, T> function) {
        return new Failure<>(function.apply(error));
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(Runnable function) {
        return this;
    }

    @Override
    public TSuccess requireSuccess(String message) {
        throw new IllegalStateException(message);
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Runnable function) {
        function.run();
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Consumer<TFailure> function) {
        function.accept(getError());
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function) {
        if (predicate.test(getError())) {
            function.accept(getError());
        }
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Failure)) {
            return false;
        }

        Failure<?, ?> other = (Failure<?, ?>) obj;
        return Objects.equals(error, other.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(error);
    }
}
