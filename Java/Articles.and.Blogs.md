# 1. 为什么Java能够实现平台无关性？ 

> 摘抄自[知乎博文视点的回答](https://www.zhihu.com/question/374019991#:~:text=%E6%80%BB%E7%BB%93%20%E8%99%9A%E6%8B%9F%E6%9C%BA%E4%B8%8E%E5%AD%97%E8%8A%82%E7%A0%81%E6%98%AFJava%E5%AE%9E%E7%8E%B0%E6%97%A0%E5%85%B3%E6%80%A7%E7%9A%84%E5%9F%BA%E7%A1%80%20%E3%80%82%20%E9%A6%96%E5%85%88%EF%BC%8C%E4%B8%8E%E4%B8%8D%E5%90%8C%E4%BA%8EC%2FC%2B%2B%EF%BC%8CJava%E5%B0%86%E7%A8%8B%E5%BA%8F%E5%AD%98%E5%82%A8%E6%A0%BC%E5%BC%8F%E4%BB%8E%E6%9C%AC%E5%9C%B0%E4%BB%A3%E7%A0%81%E8%BD%AC%E5%8F%98%E4%B8%BA%E5%AD%97%E8%8A%82%E7%A0%81%EF%BC%9B%E5%85%B6%E6%AC%A1%EF%BC%8C%E4%B8%8D%E5%90%8C%E5%B9%B3%E5%8F%B0%E7%9A%84%E8%99%9A%E6%8B%9F%E6%9C%BA%E9%83%BD%E7%BB%9F%E4%B8%80%E9%87%87%E6%A0%B7%E5%AD%97%E8%8A%82%E7%A0%81%E4%BD%9C%E4%B8%BA%E8%BE%93%E5%85%A5%E8%AF%AD%E8%A8%80%EF%BC%8C%E5%B9%B6%E7%BB%9F%E4%B8%80%E9%81%B5%E5%AE%88%E3%80%8AJava%20%E8%99%9A%E6%8B%9F%E6%9C%BA%E8%A7%84%E8%8C%83%E3%80%8B%EF%BC%8C%E6%9C%80%E7%BB%88%E6%8F%90%E4%BE%9B%E4%BA%86%E4%B8%80%E4%B8%AA%E4%B8%8D%E4%BE%9D%E8%B5%96%E4%BA%8E%E7%89%B9%E5%AE%9A%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F,%26%20%E7%A1%AC%E4%BB%B6%E7%9A%84%E8%BF%90%E8%A1%8C%E7%8E%AF%E5%A2%83%EF%BC%8C%E5%8D%B3%E5%B9%B3%E5%8F%B0%E6%97%A0%E5%85%B3%E6%80%A7%E3%80%82%20Java%20%7C%20%E4%B8%BA%E4%BB%80%E4%B9%88%E6%B5%AE%E7%82%B9%E6%95%B0%E8%BF%90%E7%AE%97%E4%B8%8D%E7%B2%BE%E7%A1%AE%EF%BC%9F)

## 什么是平台无关性

平台无关性就是一种语言在计算机上的运行不受平台的约束，Write Once, Run Anywhere。

## 平台无关性的实现

对于Java的平台无关性的支持，是分布在整个Java体系结构中的。包括Java语言规范、Class文件、Java虚拟机等。

### 编译原理基础

在计算机世界中，真正被计算机执行的实际上是由0和1组成的二进制文件。

在Java平台，要把Java文件编译成二进制文件，需要经过前端编译和后端编译两个步骤。

前端编译指与源语言有关但与目标语言无关的部分，主要功能是把 *.java* 代码转换为 *Class* 文件。

后端编译是将中间代码翻译成机器语言。后端编译由Java虚拟机执行。

**Java虚拟机**

对于不同的硬件和操作系统，最主要的区别就是其二进制指令不同。要做到跨平台，最重要的就是可以根据对应的硬件和操作系统生成对应的二进制指令。

而这一工作是由Java虚拟机完成。虽然Java语言是平台无关的，但是JVM却是平台相关的，不同的操作系统上面要安装对应的JVM。

所以，Java之所以可以做到跨平台，是因为Java虚拟机扮演了Java程序与其下的硬件和操作系统之间的“桥梁”角色。

**字节码**

各种不同平台的虚拟机都使用统一的程序存储格式——字节码（ByteCode）。字节码是构成Java平台无关性的另一基石。Java虚拟机只与由字节码组成的Class文件进行交互。

Java语言可以“Write Once, Run Anywhere”，这里的Write指的就是生成 *Class* 文件。

因为Java的Class文件可以在任何平台创建，也可以被任何平台的Java虚拟机装载并执行，所以才有了Java的平台无关性。

**Java语言规范**

Java语言在跨平台方面也做了一些努力，这些努力被定义在Java语言规范中。

比如，Java中基本数据类型的值域和行为都是由其自己定义的。而在C/C\+\+中，基本数据类型是由它的占位宽度决定的，占位宽度则是由所在平台决定的。不同平台上对于C\+\+程序的编译结果会出现不同的行为。

举一个例子，对于 `int` 类型，Java中固定占4个字节。但是在C\+\+中却不是固定的。在16位计算机上，int类型的长度可能为2字节；在32位计算机上，int类型的长度可能为4字节；当64位计算机流行起来后，int类型的长度可能会达到8字节。

# 2. Bytecode in Java

> A part of the following is quoted from  https://dzone.com/articles/introduction-to-java-bytecode

## What is Java Bytecode?

Java bytecode is the bytecode-structured instruction set of the JVM.

## JVM Data Types

Java is statically typed, which affects the design of the bytecode instructions such that an instruction expects itself to operate on values of specific types. For example, there are several add instructions to add two numbers: `iadd`, `ladd`, `fadd`, `dadd`. They expect operands of type, respectively, `int`, `long`, `float`, and `double`.

The data types defined by the JVM are:

1. Primitive types:

    - Numeric types: `byte` (8-bit 2's complement), `short` (16-bit 2's complement), `int` (32-bit 2's complement), `long` (64-bit 2's complement), `char` (16-bit unsigned Unicode), `float` (32-bit IEEE 754 single precision FP), `double` (64-bit IEEE 754 double precision FP)
    - `boolean` type
    - `returnAddress`: pointer to instruction
2. Reference types:

    - Class types
    - Array types
    - Interface types

## Bytecode Explored

Each bytecode instruction has the following format:

```
opcode (1 byte)      operand1 (optional)      operand2 (optional)      ...
```

That is an instruction that consists of one-byte opcode and zero or more operands that contain the data to operate.

Within the stack frame of the currently executing method, an instruction can push or pop values onto the operand stack, and it can potentially load or store values in the array local variables. For example:

```
public static void main(String[] args) {
    int a = 1;
    int b = 2;
    int c = a + b;
}
```

And we get the bytecode instruction after compiling the class as followed:

```
public static void main(java.lang.String[]);
descriptor: ([Ljava/lang/String;)V
flags: (0x0009) ACC_PUBLIC, ACC_STATIC
Code:
stack=2, locals=4, args_size=1
0: iconst_1
1: istore_1
2: iconst_2
3: istore_2
4: iload_1
5: iload_2
6: iadd
7: istore_3
8: return
```

The descriptor indicates that the method takes an array of Strings (`[Ljava/lang/String;`), and has a `void` return type (`V`). A set of flags follow that describe the method as public (`ACC_PUBLIC`) and static (`ACC_STATIC`).

The `Code` attribute contains the instructions for the method along with information such as the maximum depth of the operand stack (2 in this case), and the number of local variables allocated in the frame for this method (4 in this case). 

The instructions from address 0 to 8 will do the following:

`iconst_1`: Push the integer constant 1 onto the operand stack.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_iconst_1.png)

`istore_1`: Pop the top operand (an int value) and store it in local variable at index 1, which corresponds to variable `a`.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_istore_1.png)

`iconst_2`: Push the integer constant 2 onto the operand stack.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_iconst_2.png)

`istore_2`: Pop the top operand int value and store it in local variable at index 2, which corresponds to variable `b`.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_istore_2.png)

`iload_1`: Load the int value from local variable at index 1 and push it onto the operand stack.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_iload_1.png)

`iload_2`: Load the int value from the local variable at index 1 and push it onto the operand stack.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_iload_2.png)

`iadd`: Pop the top two int values from the operand stack, add them, and push the result back onto the operand stack.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_iadd.png)

`istore_3`: Pop the top operand int value and store it in local variable at index 3, which corresponds to variable `c`.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_istore_3.png)

`return`: Return from the void method.

## Method Invocations

For the below example:

```
public static void main(String[] args) {
    int a = 1;
    int b = 2;
    int c = calc(a,b);
}

static int calc(int a, int b) {
    return (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
}
```

Let's see the resulting bytecode:

```
public static void main(java.lang.String[]);
  descriptor: ([Ljava/lang/String;)V
  flags: (0x0009) ACC_PUBLIC, ACC_STATIC
  Code:
    stack=2, locals=4, args_size=1
         0 iconst_1
         1 istore_1
         2 iconst_2
         3 istore_2
         4 iload_1
         5 iload_2
         6 invokestatic #2 <Sample.calc>
         9 istore_3
        10 return
static int calc(int, int);
  descriptor: (II)I
  flags: (0x0008) ACC_STATIC
  Code:
    stack=6, locals=2, args_size=2
         0 iload_0
         1 i2d
         2 ldc2_w #3 <2.0>
         5 invokestatic #5 <java/lang/Math.pow>
         8 iload_1
         9 i2d
        10 ldc2_w #3 <2.0>
        13 invokestatic #5 <java/lang/Math.pow>
        16 dadd
        17 invokestatic #6 <java/lang/Math.sqrt>
        20 d2i
        21 ireturn
```

Instead of having the `iadd` instruction, we now get an `invokestatic` instruction, which simply invokes the static method `calc`.

We notice that the `invokestatic` instruction occupies 3 bytes by looking at the address, which jumped from 6 to 9. This is because, `invokestatic` includes two additional bytes to construct the reference to the method to be invoked (in addition to the opcode). The reference is shown by `javap` as `#2`, which is a symbolic reference to the `calc` method.

The other new information is the code for the `calc` method itself. It first loads the first integer argument onto the operand stack (`iload_0`). The next instruction, `i2d`, converts it to a `double` by applying widening conversion. The resulting `double` replaces the top of the operand stack.

The next instruction pushes a `double` constant `2.0d` onto the operand stack. Then the static `Math.pow` method is invoked with the two operand values prepared so far (the first argument to `calc` and the constant `2.0d`). When the `Math.pow` method returns, its result will be stored on the operand stack of its invoker.

The same procedure is applied to compute `Math.pow(b, 2)`.

The next instruction, `dadd`, pops the top two intermediate results, adds them, and pushes the sum back to the top. Finally, `invokestatic` invokes `Math.sqrt` on the resulting sum, and the result is cast from `double` to `int` using narrowing conversion (`d2i`). The resulting `int` is returned to the main method, which stores it back to `c` (`istore_3`).

## Instance Creations

Let's introduce a class `Point` to encapsulate XY coordinates:

```
public class Sample {
    public static void main(String[] args) {
        Adder a = new Adder(1, 1);
        int c = a.result();
    }
}

class Adder {
    int x, y;

    Adder(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int result() {
        return x+y;
    }
}
```

The compiled bytecode for the `main` method:

```
public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=3, args_size=1
         0: new           #2                  // class Adder
         3: dup
         4: iconst_1
         5: iconst_1
         6: invokespecial #3                  // Method Adder."<init>":(II)V
         9: astore_1
        10: aload_1
        11: invokevirtual #4                  // Method Adder.result:()I
        14: istore_2
        15: return
```

The new instructions encountereted here are `new` , `dup`, and `invokespecial`. The new instruction creates an object of the type specified in the operand passed to it (which is a symbolic reference to the class `Adder`). Memory for the object is allocated on the heap, and a reference to the object is pushed on the operand stack.

The `dup` instruction duplicates the top operand stack value, which means that now we have two references of the `Adder` object on the top of the stack. The next three instructions push two arguments onto the operand stack, and then invoke a special initialization method, which corresponds with the constructor. The next method is where the fields `x` and `y` will get initialized. After the method is finished, the top three operand stack values are consumed, and what remains is the original reference to the created object.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_init.png)

Next, `astore_1` pops that `Adder` reference and assigns it to the local variable at index 1 (the `a` in `astore_1` indicates this is a reference value).

The last step invokes the `result` method using `invokevirtual`, which handles dispatching the call to the appropriate method based on the actual type of the object. For example, if the variable a contained an instance of type `SpecialAdder` that extends `Adder`, and the subtype overrides the `result` method, then the overriden method is invoked. In this case, there is no subclass, and hence only one `result` method is available.

# 3 Getting the Most Out of the Java Thread Pool

> Quoted from https://dzone.com/articles/getting-the-most-out-of-the-java-thread-pool

For a better understanding of the cost of creating and starting a thread, let’s see what the JVM actually does behind the scenes:

- It allocates memory for a thread stack that holds a frame for every thread method invocation.
- Each frame consists of a local variable array, return value, operand stack, and constant pool.
- Some JVMs that support native methods also allocate a native stack.
- Each thread gets a program counter that tells it what the current instruction executed by the processor is.
- The system creates a native thread corresponding to the Java thread.
- Descriptors relating to the thread are added to the JVM internal data structures.
- The threads share the heap and method area.

## Java thread pools

Java provideds its own implementations of the thread pool pattern, through objects called `Executors`.

The `java.util.concurrent` package contains the following interfaces:

- `Executor` - a simple interface for executing tasks.
- `ExecutorService` - a more complex interface which contains additional methods for managing the tasks and the executor itself.
- `ScheduledExecutorService` - extends `ExecutorService` with methods for scheduling the execution of a task.

Alongside these interfaces, the package also provides the `Executors` helper class for obtaining `Executor` instances, as well as implementations for these interfaces.

Generally, a Java thread pool is composed of:

- The pool of worker threads, responsible for managing the threads.
- A thread factory that is responsible for creating new threads.
- A queue of tasks waiting to be executed.

### The `Executors` class and `Executor` interface

The `Executors` class contains factory methods for creating different types of thread pools, while `Executor` is the simplest thread pool interface, with a single `execute()` method.

They are used as the following example:

```
Executor executor = Executors.newSingleThreadExecutor();
executor.execute(() -> System.out.println("Hey, buddy!"));
```

The `execute()` method runs the statement if a worker thread is available or places the `Runnable` task in a queue to wait for a thread to become available.

The factory methods in the `Executors` class can create serval types of thread pools:

- `newSingleThreadExecutor()` - A thread pool with only one thread with an unbounded queue, which only executrs one task at a time.
- `newFixedThreadPool()` - A thread pool with a fixed number of threads that share an unbounded queue; If all threads are active when a new task is submitted, they will wait in the queue until a thread becomes available.
- `newCachedThreadPool()` - A thread pool that creates new threads as they are needed.
- `newWorkStealingThreadPool()` - A thread pool based on a "work-stealing" algorithm.

### The `ExecutorService`

Besides the `execute()` method, this interface also defines a similar `submit()` method that can return a future object:

```
<T> Future<T> submit(Callable<T> task);
```

The `ExecutorService` is not automatically destroyed when there are no tasks waiting to be executed, so to shut it down explicitly, you can use the `shutdown()` or `shutdownNow()` APIs.

### The `ScheduledExecutorService`

```
ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
```

The `schedule()` method specifies a task to be executed, a delay value and a `TimuUnit` for the value:

```
Future<Double> future = scheduledExecutor.schedule(callable, 2, SECONDS);
```

Furthermore, the interface defines two additional methods:

```
executor.scheduleAtFixedRate(
  () -> System.out.println("fixed rate scheduled"), 2, 2000, TimeUnit.MILLISECONDS);

executor.scheduleWithFixedDelay(
  () -> System.out.println("fixed delay scheduled"), 2, 2000, TimeUnit.MILLISECONDS);
```

The `scheduleAtFixedRate()` method executes the task after a 2 ms delay, then repeats it every 2 seconds. Similarly, the `scheduleWithFixedDelay()` method starts the first execution after 2 ms, then repeats the task 2 seconds after the previous execution ends.

### The `ForkJoinPool`

Another implementation of a thread pool is the `ForkJoinPool` class. This implements the `ExecutorService` interface and represents the central component of the fork/join framework introduced in java 7.

The fork/join framework is based on a “work-stealing algorithm” . In simple terms, what this means is that threads that run out of tasks can “steal” work from other busy threads.

A `ForkJoinPool` is well-suited for cases when most tasks create other subtasks or when many small tasks are added to the pool from external clients.

The workflow for using this thread pool:

- Create a `ForkJoinTask` subclass.
- Split the tasks into subtasks according to a condition.
- Invoke the tasks.
- Join the results of each task.
- Create an instance of the class and add it to the pool.

To create a `ForkJoinTask`, you can choose one of its more commonly used subclasses, `RecursiveAction` or `RecursiveTask`, if you need to return a result.

```
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
```

Next, tasks can be added to a Thread pool:

```
ForkJoinPool pool =ForkJoinPool.commonPool();
BigInteger result = pool.invoke(new FactorialTask(100));
```

## `ThreadPoolExecutor` vs. `ForkJoinPool`

The `ThreadPoolExecutor` provides more control over the number of threads and the tasks that are executed by each thread. 

By comparison, the `ForkJoinPool` is based on threads “stealing” tasks from other threads.

To implement the work-stealing algorithm, the fork/join framework uses two types of queues:

- A central queue for all tasks
- A task queue for each thread

One last thing to remember is that choosing a `ForkJoinPool` is only useful if the tasks create subtasks. Otherwise, it will function the same as a `ThreadPoolExecutor` , but with extra overhead.

# 4. JVM Monitoring Tools

## `jps`

Java Virtual Machine Process Status Tool. You use the `jps` command to list the instrumented JVMs on the target system.

The usage is shown as following:

```
jps [-q] [-mlvV] [<hostid>]
```

For example：

```
$ jps -m -l
16208 sun.tools.jps.Jps -m -l
4340 com.janwee.bookstore.eurekaserver.EurekaServerApplication
```

## `jstack`

jstack主要用来查看某个Java进程内的线程堆栈信息。

The following is its usage：

```
Usage:
    jstack [-l] <pid>
        (to connect to running process)
    jstack -F [-m] [-l] <pid>
        (to connect to a hung process)
    jstack [-m] [-l] <executable> <core>
        (to connect to a core file)
    jstack [-m] [-l] [server_id@]<remote server IP or hostname>
        (to connect to a remote debug server)

Options:
    -F  to force a thread dump. Use when jstack <pid> does not respond (process is hung)
    -m  to print both java and native frames (mixed mode)
    -l  long listing. Prints additional information about locks
    -h or -help to print this help message
```

## `jmap` and `jhat`

`jmap`（Memory Map）用来查看堆内存使用状况，一般结合`jhat`（Java Heap Analysis Tool）使用。

Usage of `jmap`:

```
Usage:
    jmap [option] <pid>
        (to connect to running process)
    jmap [option] <executable <core>
        (to connect to a core file)
    jmap [option] [server_id@]<remote server IP or hostname>
        (to connect to remote debug server)

where <option> is one of:
    <none>               to print same info as Solaris pmap
    -heap                to print java heap summary
    -histo[:live]        to print histogram of java object heap; if the "live"
                         suboption is specified, only count live objects
    -clstats             to print class loader statistics
    -finalizerinfo       to print information on objects awaiting finalization
    -dump:<dump-options> to dump java heap in hprof binary format
                         dump-options:
                           live         dump only live objects; if not specified,
                                        all objects in the heap are dumped.
                           format=b     binary format
                           file=<file>  dump heap to <file>
                         Example: jmap -dump:live,format=b,file=heap.bin <pid>
    -F                   force. Use with -dump:<dump-options> <pid> or -histo
                         to force a heap dump or histogram when <pid> does not
                         respond. The "live" suboption is not supported
                         in this mode.
    -h | -help           to print this help message
    -J<flag>             to pass <flag> directly to the runtime system
```

使用`jmap -histo[:live] pid`查看堆内存中的对象数目、大小统计直方图，如果带上`live`则只统计活对象，如下：

```
$ jmap -histo:live 7976

 num     #instances         #bytes  class name
----------------------------------------------
   1:          5593         513872  [C
   2:           494         160840  [B
   3:          5432         130368  java.lang.String
   4:           979         112232  java.lang.Class
   5:           994          55472  [Ljava.lang.Object;
   6:           844          33760  java.util.TreeMap$Entry
   7:             1          32784  [Ljava.util.concurrent.ForkJoinTask;
   8:           654          26160  java.util.LinkedHashMap$Entry
...
```

用`jmap`把进程内存使用情况dump到文件中，再用 `jhat`分析查看：

```
$ jmap -dump:format=b,file=factorial-task-dump 7976
Dumping heap to C:\Users\Janwee\factorial-task-dump ...
Heap dump file created
```

使用`jhat`分析dump文件：

```
$ jhat C:\Users\Janwee\factorial-task-dump
Reading from C:\Users\Janwee\factorial-task-dump...
Dump file created Mon Feb 27 09:27:26 CST 2023
Snapshot read, resolving...
Resolving 65112 objects...
Chasing references, expect 13 dots.............
Eliminating duplicate references.............
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.
```

## `jstat`

Usage:

```
Usage: jstat -help|-options
       jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]

Definitions:
  <option>      An option reported by the -options option
  <vmid>        Virtual Machine Identifier. A vmid takes the following form:
                     <lvmid>[@<hostname>[:<port>]]
                Where <lvmid> is the local vm identifier for the target
                Java virtual machine, typically a process id; <hostname> is
                the name of the host running the target Java virtual machine;
                and <port> is the port number for the rmiregistry on the
                target host. See the jvmstat documentation for a more complete
                description of the Virtual Machine Identifier.
  <lines>       Number of samples between header lines.
  <interval>    Sampling interval. The following forms are allowed:
                    <n>["ms"|"s"]
                Where <n> is an integer and the suffix specifies the units as
                milliseconds("ms") or seconds("s"). The default units are "ms".
  <count>       Number of samples to take before terminating.
  -J<flag>      Pass <flag> directly to the runtime system.
```

For example:

```
$ jstat -gc 9528 250 4
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
```

What the columns mean:

- S0C, S1C, S0U, S1U: Survivor 0/1 区容量和使用量
- EC, EU: Eden区容量和使用量
- OC, OU: 老年代容量和使用量
- MC, MU: 元空间容量和使用量
- YGC、YGT：年轻代GC次数和GC耗时
- FGC、FGCT：Full GC次数和Full GC耗时
- GCT：GC总耗时

堆内存 = 年轻代 + 年老代 + 元空间
年轻代 = Eden区 + 两个Survivor区（From和To）


# 5. Java Garbage Collection Tuning

> Quoted from https://sematext.com/blog/java-garbage-collection-tuning/

- `-XX:+PrintFlagsInitial` flag, shows available options and their default values.
- `-XX:+PrintFlagsFinal` flag, shows available options and their current values.
- `jinfo` command, shows the value of a particular parameter.
- `-XX:+PrintCommandLineFlags` flag, shows all customized parameter values.

## Heap Size

- `-Xms`, `-Xmx` parameters: The minimum and maximum size of heap
- `-XX:+AlwaysPreTouch` flag: Add this flag to load the memory pages into memory at the start of the application.
- `-Xmn` parameter: It allows us to explicitly define the size of the young generation heap size.

## Serial Garbage Collector

The Serial Garbage Collector is the simplest, single-threaded garbage collector. 

- `-XX:+UseSerialGC` flag: Turn on the Serial garbage collector.

For example, after adding the  `-XX:+UseSerialGC` flag, the output of `jmap` shows that Mark Sweep Compact GC is used:

```
$ jmap -heap 14804
...
Mark Sweep Compact GC
...
```

## Parallel Garbage Collector

The Parallel garbage collector similar in its roots to the Serial garbage collector but uses multiple threads to perform garbage collection on your application heap.

- `-XX:+UseParallelGC` flag: Enable the Parallel garbage collector.
- `-XX:-UseParallelGC` flag: Diable the Parallel garbage collector.

```
$ jmap -heap 4296
...
Parallel GC with 8 thread(s)
...
```

## Tuning the Parallel Garbage Collector

- `-XX:ParallelGCThreads` flag: Set the number of threads that the garbage collector can use.

For example, after adding `-XX:ParallelGCThreads=4` flag to our application parameters, we get:

```
$ jmap -heap 13204
...
Parallel GC with 4 thread(s)
...
```

- `-XX:MaxGCPauseMillis`: Specify the maximum pause time goal between two consecutive garbage collection events.

For example, with a flag `-XX:MaxGCPauseMillis=100` we tell the Parallel garbage collector that we would like to have the maximum pause of 100 milliseconds between garbage collections. The longer the gap between garbage collections the more garbage can be left on the heap making the next garbage collection more expensive. If the value is too small, the application will spend the majority of its time in garbage collection.

- `XX:GCTimeRatio` flag: It is defined as `1/(1 + GC_TIME_RATIO_VALUE)` and it’s a percentage of time spent in garbage collection.

For example, setting -XX:GCTimeRatio=9 means that 10% of the application’s working time may be spent in the garbage collection.

By default, the value of `-XX:GCTimeRatio` flag is set to 99 by the JVM.

<To be continued>

# 6. Interpreter vs Compiler

> Quoted from https://www.guru99.com/difference-compiler-vs-interpreter.html

## Key Difference between Compiler and Interpreter

- Compiler transforms code written in a high-level programming language into the machine code at once before the program runs, whereas an Interpreter converts each high-level program statement, one by one, into the machine code, during program run.
- Compiled code runs faster, while interpreted code runs slower.
- Compiler displays all errors after compilation, on the other hand, the Interpreter displays errors of each line one by one.
- Compiler is based on translation linking-loading model, whereas the Interpreter is based on Interpretation Method.
- Compiler takes an entire program, whereas the Interpreter takes a single line of code.

**How Compiler Works**
```
flowchart LR
    A[Source Code] --> B[Compiler] --> C[Machine Code] --> D[ Output ]
    style A fill:#696969,stroke:#333,stroke-width:4px;
    style B fill:#A020F0,stroke:#333,stroke-width:4px;
    style C fill:#458B74,stroke:#333,stroke-width:4px;
    style D fill:##000000,stroke:#333,stroke-width:4px;
```

**How Interpreter Works**

```
flowchart LR
    A[Source Code] --> B[Interpreter] --> C[Output]
    style A fill:#696969,stroke:#333,stroke-width:4px;
    style B fill:#A020F0,stroke:#333,stroke-width:4px;
    style C fill:##000000,stroke:#333,stroke-width:4px;
```