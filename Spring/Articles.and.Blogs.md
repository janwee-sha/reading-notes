# 1. Spring Transaction Propagation

> Quoted from https://dzone.com/articles/spring-boot-transactions-tutorial-understanding-tr

## What Is Transaction Propagation?

Any application involves a number of services or components making a call to other services or components. Transaction propagation indicates if any component or service will or will not participate in a transaction and how will it behave if the calling component/service already has or does not have a transaction created already.

## Transacton Propagation -- REQUIRED (By default)

The method will meake use of the calling service transaction if it exists else create its own. 

For example, `AuthorService.throwIfAuthorNotFound()` method is called by `BookService.edit()` method:

The `edit()` method in `BookService`:

```
@Transactional(propagation = Propagation.REQUIRED)
public void edit(Book book) {
    log.info("Modifying book.");
    try {
        remoteAuthorService.throwIfAuthorNotFound(book.getAuthorId());
    } catch (RuntimeException e) {
        System.err.println("Exception caught.");
    }
    bookRepo.save(book);
}
```

The `throwIfAuthorNotFound()` method will be shown as below:

```
@Transactional(propagation = Propagation.REQUIRED)
public void throwIfAuthorNotFound(Long authorId) {
    if (true) {
        throw new RuntimeException("Author not found");
    }
}
```

Here is a part of the output:
```
Transaction silently rolled back because it has been marked as rollback-only
```

## Transaction Propagation — SUPPORTS

The method will make use of the calling service transaction if it exists. 

## Transaction Propagation — NOT_SUPPORTED

In this case the mothod never run in transaction.

## Transaction Propagation — REQUIRES_NEW

The method always creates a new transaction.

## Transaction Propagation — NEVER

The method never uses transaction and if the calling service has a transaction then the  method throws an exception.

For example:

The `edit()` method in `BookService`:

```
@Transactional(propagation = Propagation.REQUIRED)
public void edit(Book book) {
    log.info("Modifying book.");
    remoteAuthorService.throwIfAuthorNotFound(book.getAuthorId());
    bookRepo.save(book);
}
```

The `throwIfAuthorNotFound()` method in `AuthorService` will be shown as below:

```
@Transactional(propagation = Propagation.NEVER)
public void throwIfAuthorNotFound(Long authorId) {
    if (true) {
        throw new RuntimeException("Author not found");
    }
}
```

Part of the output may looks like the following:

```
org.springframework.transaction.IllegalTransactionStateException: Existing transaction found for transaction marked with propagation 'never'
```

## Transaction Propagation — MANDATORY

In this case the method always needs the calling service to have a transaction else exception is thrown.

If the mathod is called directly or its calling service does not have a transaction, the output would be like the following:

```
IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'
```

# 2. Understanding the worker ants of Spring Aspect Oriented Programming: Proxies

> Quoted from https://medium.com/walmartglobaltech/understanding-the-worker-ants-of-spring-aop-proxies-902208881493#:~:text=A%20Proxy%20is%20an%20object%20created%20by%20the,auto-magically%20add%20additional%20behaviour%20without%20modifying%20existing%20code.

## Proxies and Run Time Weaving

This is achieved using either of two ways:

- JDK dynamic proxy —Spring AOP defaults to JDK dynamic proxies which enable any interface (or set of interfaces) to be proxied. Whenever the targeted object implements even one interface, then the JDK dynamic proxy will be used.

- CGLIB proxy — This is used by default if a business object does not implement any interface.

## Proxy in Action

Consider this example for the creation of Logger aspect:

```
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Loggable {
}
```

```
@Component
@Aspect
public class LoggerAspect {
    @Pointcut("@annotation(Loggable)")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object logExecutionTime(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return jp.proceed();
        } finally {
            stopWatch.stop();
            System.out.println("Execution time for " + className + "." + methodName + " :: "
                    + stopWatch.getTotalTimeMillis() + " ms");
        }
    }
}
```

```
@Component
public class A {
    @Loggable
    public void a() {
        System.out.println("method a called");
        this.b();
    }

    @Loggable
    public void b() {
        System.out.println("method b called");
    }
}
```

Let's test them: 

```
@SpringBootTest
public class ExecutingATest {
    @Autowired
    private A a;

    @Test
    public void testLogging() {
        a.a();
        a.b();
    }
}
```

This yields the following output:

```
method a called
method b called
Execution time for A.a :: 7 ms
method b called
Execution time for A.b :: 0 ms
```

When spring determines that the `A` bean is advised by one or more aspects, it automatically generates a proxy for it to intercept all method invocations and to execute any associated advice when needed. However, it’s observable from the output that method execution time is logged for `a.a()` call but not for `a.b()` call in the `a()` method. That’s because the latter is not intercepted by the proxy but by the actual target class. As a result, the advice is never triggered.

Thus, **self invocation does not result in associated advice to run**.

> **Note:** The `@Aspect` annotation on a class marks it as an aspect and hence excludes it from auto-proxying. Consequently, **it is not possible to have aspects themselves be the target of advice from other aspects in Spring AOP**.