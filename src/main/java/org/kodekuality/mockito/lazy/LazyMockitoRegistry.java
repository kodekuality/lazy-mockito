package org.kodekuality.mockito.lazy;

import org.kodekuality.mockito.lazy.behaviour.LazyBehaviour;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class LazyMockitoRegistry {
    private final Collection<LazyBehaviour> behaviours = new CopyOnWriteArrayList<>();

    public void define (LazyBehaviour behaviour) {
        behaviours.add(behaviour);
    }

    public Collection<LazyBehaviour> getBehaviours() {
        return behaviours;
    }

    public void reset () {
        behaviours.clear();
    }
}
