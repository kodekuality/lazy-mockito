package org.kodekuality.mockito.lazy.behaviour;

import org.mockito.invocation.InvocationOnMock;

public interface LazyAction {
    Object perform (InvocationOnMock invocationOnMock) throws Throwable;
}
