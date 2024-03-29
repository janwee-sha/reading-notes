> Refernce: Brian Goetz's [Java Concurrency in Practice ](https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601)

# 3 对象的共享

## 3.1 可见性

#### Volatile变量

Java语言提供了一种稍弱的同步机制，即 `volatile` 变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为 `volatile` 类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存一起重排序。`volatile` 变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此读取 `volatile` 类型的变量时总会返回最新写入的值。

`volatile` 变量对可见性的影响比 `volatile` 变量本身更为重要。当线程A首先写入一个 `volatile` 变量且线程B随后读取该变量时，在写入 `volatile` 变量之前对A可见的所有变量的值，在B读取了 `volatile` 变量后，对B也是可见的。从内存可见性的角度来看，写入 `volatile` 变量相当于退出同步代码块，而读取 `volatile` 变量就相当于进入同步代码块。

`volatile` 变量的正确使用方式包括：

*   确保它们自身状态的可见性；
*   确保它们所引用对象的状态的可见性；
*   标识一些重要的程序生命周期事件的发生（例如，初始化或关闭）。

[此处](./src/main/java/objectsharing/CountSleep.java)的代码给出了 `volatile` 变量的一种典型用法：检查某个状态标记以判断是否退出循环。

加锁机制既可以确保可见性又可以确保原子性，而 `volatile` 变量只能确保可见性。

当且仅当满足以下所有条件时，才应该使用 `volatile` 变量：

*   对变量的写入操作不依赖变量的当前值，或者能确保只有单个线程更新变量的值。
*   该变量不会与其他状态变量一起纳入不变性条件中。
*   在访问变量时不需要加锁。

> 以下内容引用自 <https://www.cnblogs.com/zhengbin/p/5654805.html>
>
> 当一个变量定义为 `volatile` 之后，将具备两种特性：
>
> *   保证此变量对所有的线程的可见性。“可见性”指当一个线程修改了这个变量的值，`volatile` 保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。但普通变量做不到这点，普通变量的值在线程间传递均需要通过主内存来完成。
>
> *   禁止指令重排序优化。有 `volatile` 修饰的变量，赋值后多执行了一个“`load addl $0x0, (%esp)`”操作，这个操作相当于一个内存屏障（指令重排序时不能把后面的指令重排序到内存屏障之前的位置），只有一个CPU访问内存时，并不需要内存屏障；（什么是指令重排序：是指CPU采用了允许将多条指令不按程序规定的顺序分开发送给各相应电路单元处理）。

# 5 基础构建模块

## 5.1 同步容器类

### 同步容器类的问题

同步容器类是线程安全的，但在某些情况下可能需要额外的客户端加锁来保护复合操作（迭代、跳转以及条件运算等）。

### 迭代器与ConcurrentModificationException

Java中进行迭代的标准方式都是使用Iterator。然而如果有其他线程并发地修改容器，那么即使是使用迭代器也无法避免在迭代期间对容器加锁。对于并发修改，同步容器类表现出的行为是“即时失败”，即抛出一个ConcurrentModificationException异常。“即时失败”的迭代器并不是一种完备的处理机制。

## 5.2 并发容器

Java 5.0提供了多种并发容器类来改进同步容器的性能。同步容器将所有对容器状态的访问都串行化，以实现它们的线程安全性，代价是严重降低并发性。

并发容器则是针对多个线程并发访问设计的。

<TBC>