# Lazy Mockito

![Lazy Mockito](https://github.com/kodekuality/lazy-mockito/raw/master/resources/icon.png)

Lazy Mockito enables you to prime behaviour which gets resolved at runtime. 
It's built on top of [mockito](https://site.mockito.org/) as an easier way to define lazy behaviour on top of the Answer API.
Specially useful for [deep spying](https://github.com/kodekuality/deep-spy).

## Build

[![Build Status](https://travis-ci.org/kodekuality/lazy-mockito.svg?branch=master)](https://travis-ci.org/kodekuality/lazy-mockito)
[![codecov](https://codecov.io/gh/kodekuality/lazy-mockito/branch/master/graph/badge.svg)](https://codecov.io/gh/kodekuality/lazy-mockito)
[![Download](https://api.bintray.com/packages/kodekuality/maven/lazy-mockito/images/download.svg)](https://bintray.com/kodekuality/maven/lazy-mockito/_latestVersion)


## Using

Include Dependency 

    <dependency>
        <groupId>org.kodekuality</groupId>
        <artifactId>lazy-mockito</artifactId>
        <version>1.0.0</version>
        <scope>test</scope>
    </dependency>

Write your test

```java
@Before
public void setUp() throws Exception {
    LazyMockito.reset();
}

@Test
public void test() throws SQLException {
    RuntimeException runtimeException = new RuntimeException();

    LazyMockito
            .when(ComposePredicate.<InvocationOnMock>compose().with(InvocationOnMock::getMock, Connection.class::isInstance))
            .thenThrow(() -> runtimeException);

    Connection connection = LazyMockito.lazyMock(Connection.class);

    try {
        connection.getAutoCommit();
    } catch (Throwable e) {
        assertSame(runtimeException, e);
    }
}
```

Example [here](https://github.com/kodekuality/lazy-mockito/blob/master/src/test/java/org/kodekuality/mockito/lazy/LazyMockitoTest.java)
