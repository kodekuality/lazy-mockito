package org.kodekuality.mockito.lazy.answer;

import org.kodekuality.mockito.lazy.LazyMockitoRegistry;
import org.kodekuality.mockito.lazy.behaviour.LazyAction;
import org.kodekuality.mockito.lazy.behaviour.LazyBehaviour;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class LazyAnswer implements Answer<Object> {
    private final LazyMockitoRegistry registry;
    private final LazyAction defaultAction;

    public LazyAnswer(LazyMockitoRegistry registry, LazyAction defaultAction) {
        this.registry = registry;
        this.defaultAction = defaultAction;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        for (LazyBehaviour behaviour : registry.getBehaviours()) {
            if (behaviour.getPredicate().test(invocation)) {
                return behaviour.getAction().perform(invocation);
            }
        }

        return defaultAction.perform(invocation);
    }
}
