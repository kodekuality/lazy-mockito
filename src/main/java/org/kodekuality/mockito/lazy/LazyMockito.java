package org.kodekuality.mockito.lazy;

import org.kodekuality.mockito.lazy.answer.LazyAnswer;
import org.kodekuality.mockito.lazy.behaviour.LazyBehaviour;
import org.kodekuality.mockito.lazy.behaviour.LazyBehaviourBuilder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.function.Predicate;

import static org.mockito.Mockito.withSettings;

public class LazyMockito {
    private static final LazyMockitoRegistry registry = new LazyMockitoRegistry();
    private static LazyAnswer answer = new LazyAnswer(registry, InvocationOnMock::callRealMethod);

    public static void with (LazyBehaviour behaviour) {
        registry.define(behaviour);
    }
    public static <T> LazyBehaviourBuilder<T> when (Predicate<InvocationOnMock> predicate) {
        return new LazyBehaviourBuilder<>(registry, predicate);
    }

    public static void reset () {
        registry.reset();
    }

    public static Answer answer () {
        return answer;
    }

    public static <T> T lazyMock (Class<T> type) {
        return Mockito.mock(type, answer());
    }

    public static <T> T lazySpy (T object) {
        return Mockito.mock((Class<T>) object.getClass(), withSettings()
                .spiedInstance(object)
                .defaultAnswer(answer()));
    }
}
