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