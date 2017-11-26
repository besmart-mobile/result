package com.besmart.result.annimon;

import com.annimon.stream.Optional;
import com.annimon.stream.function.BiFunction;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.annimon.stream.function.Supplier;
import com.besmart.result.annimon.functions.*;

/**
 * Result of a computation or any other action. Can be successful and contain a value (TSuccess) or
 * failed and contain an error (TFailure).
 *
 * @param <TSuccess> The type of the contained value.
 * @param <TFailure> The type of the error object in case of a failure.
 */
public abstract class Result<TSuccess, TFailure> {
    /**
     * Creates a new Result with the given error.
     *
     * @param error The error.
     * @return Failed Result.
     */
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> withError(final TFailure error) {
        assertParameterNotNull(error, "Error");
        return new Failure<>(error);
    }

    /**
     * Creates a successful Result with the given value. The value may not be null.
     *
     * @param value The value.
     * @return Successful Result with the given value.
     * @throws IllegalArgumentException If value is null.
     */
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> withValue(final TSuccess value) {
        assertParameterNotNull(value, "Value");
        return new Success<>(value);
    }

    /**
     * Creates a Result with an optional value. If the value is null, a failed Result will be
     * created.
     *
     * @param value The value.
     * @param error The error to set, if value is null.
     * @return Successful Result with value or failed Result.
     */
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> with(final TSuccess value, final TFailure error) {
        if (value != null) {
            return withValue(value);
        }
        return withError(error);
    }

    /**
     * Creates a Result with an optional value. If the value is null or an empty Optional, a failed
     * Result is created.
     *
     * @param valueOrNothing The optional value.
     * @param error          The error to set, if value is null or an empty Optional.
     * @return Successful Result with value or failed Result.
     */
    public static <TSuccess, TFailure> Result<TSuccess, TFailure> with(
            final Optional<TSuccess> valueOrNothing, final TFailure error) {
        if (valueOrNothing == null || !valueOrNothing.isPresent()) {
            return withError(error);
        }
        return withValue(valueOrNothing.get());
    }

    protected static void assertParameterNotNull(final Object parameter, final String name) {
        if (parameter == null) {
            throw new IllegalArgumentException(String.format("%s may not be null.", name));
        }
    }

    /**
     * Checks whether the Result is failed.
     *
     * @return Whether the Result is failed.
     */
    public abstract boolean isFailure();

    /**
     * Checks whether the Result is successful.
     *
     * @return Whether the Result is successful.
     */
    public final boolean isSuccess() {
        return !isFailure();
    }

    /**
     * Returns the Result's value.
     *
     * @return The value.
     * @throws FailedResultHasNoValueException If Result is failed.
     */
    public abstract TSuccess getValue();

    /**
     * Returns the Result's error.
     *
     * @return The error.
     * @throws SuccessfulResultHasNoErrorException If Result is successful.
     */
    public abstract TFailure getError();

    /**
     * Returns the Result as a string.
     * <p>
     * <pre>
     * Result (Success with value &lt;The value&gt;)
     * </pre>
     * <p>
     * <pre>
     * Result (Error: The error)
     * </pre>
     *
     * @return The Result as a string.
     */
    @Override
    public abstract String toString();

    /**
     * Combines multiple Results. Returns the first failed Result or a successful Result without a
     * value, if all Results are successful.
     *
     * @param otherResult The Result to combine with the current one.
     * @return Result of the combination.
     */
    public abstract Result<?, TFailure> combine(final Result<?, TFailure> otherResult);

    public abstract <U, R> Result<R, TFailure> combine(final Result<U, TFailure> otherResult,
                                                       final BiFunction<TSuccess, U, R> biFunction);

    public abstract <T1, T2, R> Result<R, TFailure> combine(final Result<T1, TFailure> otherResult1,
                                                            final Result<T2, TFailure> otherResult2,
                                                            final Func3<TSuccess, T1, T2, R> combiner);

    public abstract <T1, T2, T3, R> Result<R, TFailure> combine(final Result<T1, TFailure> otherResult1,
                                                                final Result<T2, TFailure> otherResult2,
                                                                final Result<T3, TFailure> otherResult3,
                                                                final Func4<TSuccess, T1, T2, T3, R> combiner);

    public abstract <T1, T2, T3, T4, R> Result<R, TFailure> combine(final Result<T1, TFailure> otherResult1,
                                                                    final Result<T2, TFailure> otherResult2,
                                                                    final Result<T3, TFailure> otherResult3,
                                                                    final Result<T4, TFailure> otherResult4,
                                                                    final Func5<TSuccess, T1, T2, T3, T4, R> combiner);

    public abstract <T> T match(
            final Function<TSuccess, T> onSuccess,
            final Function<TFailure, T> onFailure);

    /**
     * Runs the given function, if the Result is successful.
     *
     * @param function The function to run.
     * @return Result of the function.
     */
    public abstract <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function);

    /**
     * Runs the given function and wraps its return value in a Result, if the Result is successful.
     *
     * @param function The function to run.
     * @param clazz    The return value of the function.
     * @return Return value of the function wrapped in a Result.
     */
    public abstract <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz);

    /**
     * Runs the given function, if the Result is successful.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function);

    /**
     * Runs the given function, if the Result is failed.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<?, TFailure> onFailure(final Runnable function);

    /**
     * Runs the given function, if the Result is failed.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<?, TFailure> onFailure(final Consumer<TFailure> function);

    /**
     * Runs the given function, if the Result is failed and the error matches the given predicate.
     *
     * @param predicate The predicate to check the error with.
     * @param function  The function to run.
     * @return The current Result.
     */
    public abstract Result<?, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function);

    /**
     * Runs the given function, regardless of the Result's outcome.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public Result<?, ?> onBoth(
            final Consumer<? super Result<TSuccess, TFailure>> function) {
        function.accept(this);
        return this;
    }

    /**
     * Runs the given predicate, if the Result is successful.
     *
     * @param predicate The predicate to run.
     * @param error     Error, if the predicate returns false.
     * @return Result with checked value or failed Result.
     */
    public abstract Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error);

    /**
     * Extracts a <code>Result&lt;T&gt;</code> from a <code>Result&lt;Result&lt;T&gt;&gt;</code>, if
     * the Result is successful.
     *
     * @param function A function that returns a <code>Result&lt;Result&lt;T&gt;&gt;</code>.
     * @return The extracted <code>Result&lt;T&gt;</code>.
     */
    public abstract <T> Result<T, TFailure> flatMap(
            final Function<TSuccess, Result<T, TFailure>> function);

    /**
     * Maps the Result to a Result with another value, if the Result is successful.
     *
     * @param function A function that returns the new value.
     * @return The Result of the function's value or a failed Result.
     */
    public abstract <T> Result<T, TFailure> map(
            final Function<TSuccess, T> function);

    /**
     * Extracts the inner value from an Optional value of the Result.
     *
     * @param innerValue Type of the Optional's value.
     * @param error      Error if inner value cannot be extracted.
     * @return Result of the inner value of the Optional or a failed Result.
     */
    public abstract <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error);

    public abstract Optional<TSuccess> toOptional();

    public abstract Optional<TFailure> getOptionalError();

    public abstract <T> Result<TSuccess, T> mapError(
            final Function<TFailure, T> function);

    public abstract Result<TSuccess, TFailure> onSuccess(final Runnable function);

    public abstract TSuccess requireSuccess(String message);
}
