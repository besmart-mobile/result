package com.besmart.result.annimon;


import com.annimon.stream.Optional;
import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.annimon.stream.function.Supplier;
import com.besmart.result.annimon.functions.*;

public class Success<TSuccess, TFailure> extends Result<TSuccess, TFailure> {
    private final TSuccess value;

    public Success(final TSuccess value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value = value;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public TSuccess getValue() {
        return value;
    }

    @Override
    public TFailure getError() {
        throw new SuccessfulResultHasNoErrorException();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Result (");
        result.append("Success");
        result.append(" with value <");
        result.append(getValue());
        result.append('>');
        result.append(')');
        return result.toString();
    }

    @Override
    public Result<?, TFailure> combine(
            final Result<?, TFailure> otherResult) {
        if (otherResult.isFailure()) {
            return otherResult;
        }
        return this;
    }

    @Override
    public <U, R> Result<R, TFailure> combine(final Result<U, TFailure> otherResult,
                                              final BiFunction<TSuccess, U, R> biFunction) {
        if (otherResult.isFailure()) {
            return withError(otherResult.getError());
        }
        return withValue(biFunction.apply(this.getValue(), otherResult.getValue()));
    }

    @Override
    public <T1, T2, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1,
                                                   Result<T2, TFailure> otherResult2,
                                                   Func3<TSuccess, T1, T2, R> combiner) {
        if (otherResult1.isFailure()) {
            return withError(otherResult1.getError());
        }
        if (otherResult2.isFailure()) {
            return withError(otherResult2.getError());
        }
        return withValue(combiner.apply(this.getValue(), otherResult1.getValue(), otherResult2.getValue()));
    }

    @Override
    public <T1, T2, T3, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1,
                                                       Result<T2, TFailure> otherResult2,
                                                       Result<T3, TFailure> otherResult3,
                                                       Func4<TSuccess, T1, T2, T3, R> combiner) {
        if (otherResult1.isFailure()) {
            return withError(otherResult1.getError());
        }
        if (otherResult2.isFailure()) {
            return withError(otherResult2.getError());
        }
        if (otherResult3.isFailure()) {
            return withError(otherResult3.getError());
        }
        return withValue(
                combiner.apply(this.getValue(), otherResult1.getValue(), otherResult2.getValue(), otherResult3.getValue()));
    }

    @Override
    public <T1, T2, T3, T4, R> Result<R, TFailure> combine(Result<T1, TFailure> otherResult1,
                                                           Result<T2, TFailure> otherResult2,
                                                           Result<T3, TFailure> otherResult3,
                                                           Result<T4, TFailure> otherResult4,
                                                           Func5<TSuccess, T1, T2, T3, T4, R> combiner) {
        if (otherResult1.isFailure()) {
            return withError(otherResult1.getError());
        }
        if (otherResult2.isFailure()) {
            return withError(otherResult2.getError());
        }
        if (otherResult3.isFailure()) {
            return withError(otherResult3.getError());
        }
        if (otherResult4.isFailure()) {
            return withError(otherResult4.getError());
        }
        return withValue(
                combiner.apply(this.getValue(), otherResult1.getValue(), otherResult2.getValue(), otherResult3.getValue(), otherResult4.getValue()));
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function) {
        return function.get();
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz) {
        return onSuccess(new Supplier<Result<T, TFailure>>() {
            @Override
            public Result<T, TFailure> get() {
                return new Success<>(function.get());
            }
        });
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function) {
        function.accept(value);
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error) {
        try {
            if (!predicate.test(getValue())) {
                return new Failure<>(error);
            }
        } catch (final Exception exception) {
            return new Failure<>(error);
        }
        return this;
    }

    @Override
    public <T> Result<T, TFailure> flatMap(final Function<TSuccess, Result<T, TFailure>> function) {
        return function.apply(getValue());
    }

    @Override
    public <T> Result<T, TFailure> map(final Function<TSuccess, T> function) {
        return new Success<>(function.apply(getValue()));
    }

    @Override
    public <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error) {
        if (!(getValue() instanceof Optional)) {
            return new Failure<>(error);
        }
        @SuppressWarnings("unchecked")
        final Optional<T> optional = (Optional<T>) getValue();
        if (!optional.isPresent()) {
            return new Failure<>(error);
        }
        return new Success<>(optional.get());
    }

    @Override
    public Optional<TSuccess> toOptional() {
        return Optional.of(getValue());
    }

    @Override
    public Optional<TFailure> getOptionalError() {
        return Optional.empty();
    }

    @Override
    public <T> Result<TSuccess, T> mapError(Function<TFailure, T> function) {
        return new Success<>(value);
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(Runnable function) {
        function.run();
        return this;
    }

    @Override
    public TSuccess requireSuccess(String message) {
        return getValue();
    }

    @Override
    public Result<?, TFailure> onFailure(final Runnable function) {
        return this;
    }

    @Override
    public Result<?, TFailure> onFailure(final Consumer<TFailure> function) {
        return this;
    }

    @Override
    public Result<?, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function) {
        return this;
    }
}
