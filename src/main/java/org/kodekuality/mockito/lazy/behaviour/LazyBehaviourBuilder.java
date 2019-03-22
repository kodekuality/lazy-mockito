package org.kodekuality.mockito.lazy.behaviour;

import org.kodekuality.mockito.lazy.LazyMockitoRegistry;
import org.kodekuality.mockito.lazy.behaviour.transform.Transform;
import org.mockito.invocation.InvocationOnMock;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyBehaviourBuilder<T> {
    private final LazyMockitoRegistry registry;
    private final Predicate<InvocationOnMock> predicate;

    public LazyBehaviourBuilder(LazyMockitoRegistry registry, Predicate<InvocationOnMock> predicate) {
        this.registry = registry;
        this.predicate = predicate;
    }

    public void thenReturn (T value) {
        registry.define(new LazyBehaviour(
                predicate,
                x -> value
        ));
    }

    public void thenThrow (Supplier<Throwable> throwable) {
        registry.define(new LazyBehaviour(
                predicate,
                x -> {
                    throw throwable.get();
                }
        ));
    }

    public void thenTransform (Transform<T> transform) {
        registry.define(new LazyBehaviour(
                predicate,
                x -> transform.apply((T) x.callRealMethod())
        ));
    }

    public void then (LazyAction action) {
        registry.define(new LazyBehaviour(
                predicate,
                action
        ));
    }
}
