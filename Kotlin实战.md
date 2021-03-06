# 1.Kotlin：定义和目的

Kotlin是一种针对Java平台的新编程语言。Kotlin可以很好地和所有现存的Java库和框架一起工作。

## 1.2 Kotlin的主要特征

### 1.2.1 目标平台：服务器端、Android及任何Java运行的地方

Kotlin的首要目标是提供一种更简洁、更高效、更安全的替代Java的语言，并且适用于现今使用java的所有环境。

Kotlin应用场景：

- 编写服务器端代码。
- 创建Android设备上运行的移动应用。
- 使用Inet Multi-OS Engine让Kotlin代码运行在iOS设备上。
- 使用Kotlin和TornadoFX一起来构建桌面应用程序。
- 编译成JavaScript，在浏览器上运行Kotlin代码。

### 1.2.2 静态类型

Kotlin和Java一样是一种静态类型的编程语言。

与Java不同的是，Kotlin不需要你在源代码中显式地生命每个变量的类型。很多情况下，变量类型可以根据上下文来自动判断。

如：


```
val x = 1
```
在声明这个变量时，由于变量初始化为整型值，Kotlin自动判断出它的类型是int。

静态类型带来的好处：

- 性能——方法调用速度更快，因为不需要在运行时才来判断调用的是哪个方法。
- 可靠性——编译器验证了程序的正确性，运行时崩溃概率更低。
- 可维护性——可以看到代码中用到的对象的类型，陌生代码更容易维护。
- 工具支持——静态类型使IDE能提供可靠的重构、精确的代码补全以及其他特性。

### 1.2.3 函数式和面向对象

函数式编程的核心概念如下：

- 头等函数——把函数当作值使用，可以用变量保存它，把它当作参数传递，或者当作其他函数的返回值。
- 不可变性——使用不可变对象，这保证了它们的状态在其创建之后不能再变化。
- 无副作用——使用的是纯函数。

函数式编程的优点：

- 简洁——把函数当作值可以获得更强大的抽象能力，从而避免重复代码。
- 多线程安全——使用不可变的数据结构和纯函数，函数式编程使多线程下的不安全修改根本不会发生。
- 测试更加容易——没用副作用的函数可以独立地进行测试。

Kotlin对函数式地支持：

- 函数类型，允许函数接收其他函数作为参数，或者返回其他函数。
- lambda表达式，让你用最少的样板代码方便地传递代码块。
- 数据类，提供了创建不可变值对象地简明语法。
- 标准库包含了丰富的API集合。

# 2 Kotlin基础

## 2.1 基本要素：函数和变量

### 2.1.2 函数

Kotlin中函数的声明以关键字fun开始，紧随其后是函数名称、参数、返回类型。

**语句和表达式**

语句和表达式的区别在于，表达式有值，且能作为另一个表达式的一部分使用；而语句总是包围着它的代码块中的顶层元素，且没有自己的值。

在Kotlin中，if是表达式，而不是语句。在Java中所有控制结构都是语句。Kotlin中出循环外大多数控制结构都是表达式。

Java的赋值操作是表达式，而在Kotlin中是语句。这有助于避免比较和赋值之间的混淆。

**表达式函数体**

Kotlin可以用单个表达式作为完整的函数体，并去掉花括号和return语句，如：

```
fun max(a: Int, b: Int): Int = if (a > b) a else b
```

### 2.1.3 变量

Kotlin中声明变量以关键字开始，然后是变量名称，最后可以加上类型，如：

```
val question = "How old are you?"
val answer = 24
```

也可以显式地指定变量的类型，如：

```
val answer: Int= 24
```

如果变量没有初始化器，需要显式地指定它的类型：

```
val answer: Int
answer = 24
```

**可变变量和不可变量**

声明变量的关键字有两个：

- val（来自value）——不可变引用。使用val声明的变量不能在初始化之后再次赋值。
- var（来自variable）——可变引用。这种变量的值可以被改变。

### 2.1.4 更简单的字符串格式化：字符串模板

