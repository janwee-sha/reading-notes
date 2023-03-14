# Java线程池

> Reference Link: <https://dzone.com/articles/getting-the-most-out-of-the-java-thread-pool>

## Overview

创建和启动线程池时JVM所做的工作：

- 为一个线程栈分配内存，线程栈对每个线程方法的调用都持有一个栈帧。
- 每个栈帧由一个本地变量数组，返回值，操作数栈和常量池组成。
- 某些支持本地方法的JVM也会分配一个本地方法栈。
- 每个线程获取一个用于指示当前字节码的行号的程序计数器。
- 系统为Java线程创建相应的本地线程。
- 向JVM内部数据结构添加与线程有关的描述符。
- 线程共享堆和方法区。

## Java thread pools

Java 通过名为 `Executors` 的对象提供了对线程池模式的实现。

The `java.util.concurrent` package contains the following interfaces:

*   `Executor` - a simple interface for executing tasks.
*   `ExecutorService` - a more complex interface which contains additional methods for managing the tasks and the executor itself.
*   `ScheduledExecutorService` - extends `ExecutorService` with methods for scheduling the execution of a task.

Alongside these interfaces, the package also provides the `Executors` helper class for obtaining `Executor` instances, as well as implementations for these interfaces.

Generally, a Java thread pool is composed of:

*   The pool of worker threads, responsible for managing the threads.
*   A thread factory that is responsible for creating new threads.
*   A queue of tasks waiting to be executed.

### The `Executors` class and `Executor` interface

The `Executors` class contains factory methods for creating different types of thread pools, while `Executor` is the simplest thread pool interface, with a single `execute()` method.

They are used as the following example:
```
Executor executor = Executors.newSingleThreadExecutor();
executor.execute(() -> System.out.println("Hey, buddy!"));
```

The `execute()` method runs the statement if a worker thread is available or places the `Runnable` task in a queue to wait for a thread to become available.

The factory methods in the `Executors` class can create serval types of thread pools:

*   `newSingleThreadExecutor()` - A thread pool with only one thread with an unbounded queue, which only executrs one task at a time.
*   `newFixedThreadPool()` - A thread pool with a fixed number of threads that share an unbounded queue; If all threads are active when a new task is submitted, they will wait in the queue until a thread becomes available.
*   `newCachedThreadPool()` - A thread pool that creates new threads as they are needed.
*   `newWorkStealingThreadPool()` - A thread pool based on a "work-stealing" algorithm.

### The `ExecutorService`

Besides the `execute()` method, this interface also defines a similar `submit()` method that can return a future object:

    <T> Future<T> submit(Callable<T> task);

The `ExecutorService` is not automatically destroyed when there are no tasks waiting to be executed, so to shut it down explicitly, you can use the `shutdown()` or `shutdownNow()` APIs.

### The `ScheduledExecutorService`

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

The `schedule()` method specifies a task to be executed, a delay value and a `TimuUnit` for the value:

    Future<Double> future = scheduledExecutor.schedule(callable, 2, SECONDS);

Furthermore, the interface defines two additional methods:

    executor.scheduleAtFixedRate(
      () -> System.out.println("fixed rate scheduled"), 2, 2000, TimeUnit.MILLISECONDS);

    executor.scheduleWithFixedDelay(
      () -> System.out.println("fixed delay scheduled"), 2, 2000, TimeUnit.MILLISECONDS);

The `scheduleAtFixedRate()` method executes the task after a 2 ms delay, then repeats it every 2 seconds. Similarly, the `scheduleWithFixedDelay()` method starts the first execution after 2 ms, then repeats the task 2 seconds after the previous execution ends.

### The `ForkJoinPool`

Another implementation of a thread pool is the `ForkJoinPool` class. This implements the `ExecutorService` interface and represents the central component of the fork/join framework introduced in java 7.

The fork/join framework is based on a “work-stealing algorithm” . In simple terms, what this means is that threads that run out of tasks can “steal” work from other busy threads.

A `ForkJoinPool` is well-suited for cases when most tasks create other subtasks or when many small tasks are added to the pool from external clients.

The workflow for using this thread pool:

*   Create a `ForkJoinTask` subclass.
*   Split the tasks into subtasks according to a condition.
*   Invoke the tasks.
*   Join the results of each task.
*   Create an instance of the class and add it to the pool.

To create a `ForkJoinTask`, you can choose one of its more commonly used subclasses, `RecursiveAction` or `RecursiveTask`, if you need to return a result.

    public class FactorialTask extends RecursiveTask<BigInteger> {
        private static final int threshold = 20;
        private int start = 1;
        private int n;

        //Standard constructors

        @Override
        protected BigInteger compute() {
            if ((n - start) >= threshold) {
                return invokeAll(createSubtasks())
                        .stream()
                        .map(ForkJoinTask::join)
                        .reduce(BigInteger.ONE, BigInteger::multiply);
            } else {
                return calculate(start, n);
            }
        }

        private Collection<FactorialTask> createSubtasks() {
            List<FactorialTask> tasks = new ArrayList<>();
            int mid = (start + n) / 2;
            tasks.add(new FactorialTask(start, mid));
            tasks.add(new FactorialTask(mid + 1, n));
            return tasks;
        }

        private BigInteger calculate(int start, int n) {
            return IntStream.rangeClosed(start, n)
                    .mapToObj(BigInteger::valueOf)
                    .reduce(BigInteger.ONE, BigInteger::multiply);
        }
    }

Next, tasks can be added to a Thread pool:

    ForkJoinPool pool =ForkJoinPool.commonPool();
    BigInteger result = pool.invoke(new FactorialTask(100));

## `ThreadPoolExecutor` vs. `ForkJoinPool`

The `ThreadPoolExecutor` provides more control over the number of threads and the tasks that are executed by each thread.

By comparison, the `ForkJoinPool` is based on threads “stealing” tasks from other threads.

To implement the work-stealing algorithm, the fork/join framework uses two types of queues:

*   A central queue for all tasks
*   A task queue for each thread

One last thing to remember is that choosing a `ForkJoinPool` is only useful if the tasks create subtasks. Otherwise, it will function the same as a `ThreadPoolExecutor` , but with extra overhead.