package org.kodekuality.mockito.lazy;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.kodekuality.mockito.lazy.behaviour.LazyBehaviour;
import org.kodekuality.mockito.lazy.predicates.ComposePredicate;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class LazyMockitoTest {
    @Before
    public void setUp() {
        LazyMockito.reset();
    }

    @Test
    public void throwException() {
        RuntimeException runtimeException = new RuntimeException();

        LazyMockito
                .when(ComposePredicate.<InvocationOnMock>compose()
                        .with(InvocationOnMock::getMock, Connection.class::isInstance)
                        .with(x -> x.getArguments().length == 0)
                )
                .thenThrow(() -> runtimeException);

        Connection connection = LazyMockito.lazyMock(Connection.class);

        try {
            connection.getAutoCommit();
        } catch (Throwable e) {
            assertSame(runtimeException, e);
        }
    }

    @Test
    public void returnValue() throws SQLException {
        LazyMockito
                .<String>when(ComposePredicate.<InvocationOnMock>compose()
                        .with(InvocationOnMock::getMock, Connection.class::isInstance)
                        .with(x -> x.getArguments().length == 0)
                        .with(x -> x.getMethod().getReturnType().isAssignableFrom(String.class))
                )
                .thenReturn("oi");

        Connection connection = LazyMockito.lazyMock(Connection.class);

        String result = connection.getCatalog();

        assertThat(result, CoreMatchers.is("oi"));
    }

    @Test
    public void transform() throws SQLException {
        LazyMockito
                .<String>when(ComposePredicate.<InvocationOnMock>compose()
                        .with(x -> x.getArguments().length == 0)
                        .with(x -> x.getMethod().getReturnType().isAssignableFrom(String.class))
                )
                .thenTransform(x -> "oi");

        Bean bean = LazyMockito.lazySpy(new Bean());

        String result = bean.method();

        assertThat(result, CoreMatchers.is("oi"));
        assertThat(bean.method2("hi"), CoreMatchers.is("hi"));
    }

    @Test
    public void action() throws SQLException {
        LazyMockito
                .with(new LazyBehaviour(
                        ComposePredicate.<InvocationOnMock>compose()
                                .with(x -> x.getArguments().length == 0)
                                .with(x -> x.getMethod().getReturnType().isAssignableFrom(String.class)),
                        x -> "oi"
                ));

        Bean bean = LazyMockito.lazySpy(new Bean());

        String result = bean.method();

        assertThat(result, CoreMatchers.is("oi"));
    }

    @Test
    public void actionThen() throws SQLException {
        LazyMockito
                .when(
                        ComposePredicate.<InvocationOnMock>compose()
                                .with(x -> x.getArguments().length == 0)
                                .with(x -> x.getMethod().getReturnType().isAssignableFrom(String.class))
                ).then(x -> "oi");

        Bean bean = LazyMockito.lazySpy(new Bean());

        String result = bean.method();

        assertThat(result, CoreMatchers.is("oi"));
    }

    private static class Bean {
        String method (){ return "hi"; }
        String method2 (String input){ return input; }
    }
}