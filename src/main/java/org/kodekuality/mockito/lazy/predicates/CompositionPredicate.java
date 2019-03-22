package org.kodekuality.mockito.lazy.predicates;

import java.util.function.Function;
import java.util.function.Predicate;

public class CompositionPredicate<I, O> implements Predicate<I> {
    private final Function<I, O> extract;
    private final Predicate<O> predicate;

    public CompositionPredicate(Function<I, O> extract, Predicate<O> predicate) {
        this.extract = extract;
        this.predicate = predicate;
    }


    @Override
    public boolean test(I input) {
        return predicate.test(extract.apply(input));
    }
}
