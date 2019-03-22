package org.kodekuality.mockito.lazy.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComposePredicate<T> implements Predicate<T> {
    public static <T> ComposePredicate<T> compose() {
        return new ComposePredicate<>();
    }

    private final List<Predicate<T>> predicates = new ArrayList<>();

    public ComposePredicate<T> with (Predicate<T> predicate) {
        predicates.add(predicate);
        return this;
    }

    public <I> ComposePredicate<T> with (Function<T, I> func, Predicate<I> predicate) {
        predicates.add(new CompositionPredicate<>(func, predicate));
        return this;
    }

    @Override
    public boolean test(T input) {
        for (Predicate<T> predicate : predicates) {
            if (!predicate.test(input)) return false;
        }

        return true;
    }
}
