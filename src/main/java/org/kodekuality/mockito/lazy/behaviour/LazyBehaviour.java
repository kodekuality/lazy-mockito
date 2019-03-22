package org.kodekuality.mockito.lazy.behaviour;

import org.mockito.invocation.InvocationOnMock;

import java.util.function.Predicate;

public class LazyBehaviour {
    private final Predicate<InvocationOnMock> predicate;
    private final LazyAction action;

    public LazyBehaviour(Predicate<InvocationOnMock> predicate, LazyAction action) {
        this.predicate = predicate;
        this.action = action;
    }

    public Predicate<InvocationOnMock> getPredicate() {
        return predicate;
    }

    public LazyAction getAction() {
        return action;
    }
}
