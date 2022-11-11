# 概述

## 并发与并行

- 并发程序含有多个逻辑上的独立执行块，它们可以独立地并行执行，也可以串行执行；并行程序可能有多个独立执行块，也可能仅有一个。
- 并发是问题域中的概念——程序需要被设计成能够处理多个同时（或者几乎同时）发生的事件；而并行则是方法域中的概概念——通过将问题中的多个部分并行执行，来加速解决问题。
- 并发程序的执行通常是不确定的，它会随着事件时序的改变而给出不同的结果；并行程序可能是确定的。

## 并行架构

### 位级（bit-level）并行

为什么32位计算机的运行速度比8位计算机更快？因为并行。对于两个32位数的加法，8位计算机必须进行多次8位计算，而32位计算机可以一步完成，即并行地处理32位数的4字节。

### 指令级（instruction-level）并行

现代CPU的并行度很高，其中使用的技术包括流水线、乱序执行和猜测执行等。

而这种“看上去像串行”的设计逐渐变得不适用。进入多核时代，我们必须面对的情况是：无论是表面上还是实质上，指令都不再串行执行了。

### 数据级（data）并行

数据级并行（也称为“单指令多数据”，SIMD）架构，可以并行地在大量数据上施加同一操作。这并不适合解决所有问题，但在适合的场景却可以大展身手。 图像处理就是一种适合进行数据级并行的场景。比如，为了增加图片亮度就需要增加每一个像素的亮度。现代GPU也因图像处理的特点而演化成了极其强大的数据并行处理器。

### 任务级（task-level）并行

终于来到了大家所认为的并行形式——多处理器。从程序员的角度来看，多处理器架构最明显的分类特征是其内存模型（共享内存模型或分布式内存模型）。 对于共享内存的多处理器系统，每个处理器都能访问整个内存，处理器之间的通信主要通过内存进行。

## 并发：不只是多核

### 并发的世界，并发的软件

### 分布式的世界，分布式的软件。 

软件在非同步运行的多台计算机上分布式地运行，其本质是并发。 此外，分布式软件还具有容错性。

### 不可预测的世界，容错性强的软件

### 复杂的世界，简单的软件