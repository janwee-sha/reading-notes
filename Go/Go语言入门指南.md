# 4 基本结构和基本数据类型

## 4.1 文件名、关键字与标识符

Go代码中会使用到的25个关键字或保留字

```
break    default     func   interface select
case     defer       go     map       struct
chan     else        goto   package   switch
const    fallthrough if     range     type
continue for         import return    var
```

除了上面的关键字，Go语言还有36个预定义标识符：

```
常量：true false iota nil
类型：int int8 int16 int32 int64 uint uint8 uint16 uint32 uint64 uintptr float32 float64 complex128 complex64 bool byte rune string error
函数：make len cap new append copy close delete complex real imag panic recover
```

## 4.2 Go程序的基本结构和要素

### 4.2.1 包的概念、导入与可见性

Go语言的源文件需要在非注释的第一行指明这个文件属于哪个包，如：`package main`。`package main`表示一个可独立执行的程序，每个Go程序都包含一个名为`main`的包。

### 4.2.6 类型转换

Go语言不存在隐式类型转换，因此所有的转换都必须显式说明，就像调用一个函数一样。

```
valueOfTypeB = typeB(valueOfTypeA)
```

## 4.3 常量

常量的定义格式： `const identifier [type] = value`。

常量的值必须实能够在编译时就能够确定的；你可以在其赋值表达式中涉及计算过程，但是所有用于计算的值必须在编译期间就能获得。因为在编译期间自定义函数均属于未知，因此无法用于常量的赋值，但内置函数可以使用。

```
// 正确的常量定义
const c1 = -0.2879998977377777878777777787
const c2 = 2/3
const c3 = len("abc")
// 错误的常量定义
const c2 = getNumber()
```

常量还可以用作枚举：

```
const (
    Unknown = 0
    Female = 1
    Male =2
)
```

`iota`可以被用作枚举值：

```
const (
    l1 = iota // 0
    l2        // 1
    l3        // 2
    l4 = 5    // 5
    l5        // 5
)
```

## 4.4 变量

### 4.4.5 init函数

变量除了可以在全局生命中初始化，也可以在init函数中初始化。这是一类非常特殊的函数，它不能够被人为调用，而是在每个包完成初始化后自动执行，并且执行优先级比main函数高。

```
import (
	"fmt"
	"math"
)

var Pi float64

func init() {
	Pi = 4 * math.Atan(1)
}
```

## 4.5 基本类型和预算符

### 4.5.2 数字类型

Go也有基于架构的类型，例如：int、uint和uintptr。

这些类型的长度根据运行程序所在的操作系统决定：

- `int`和`uint`在32位操作系统上，它们均使用32位（4个字节），在64位系统上，它们均使用64位（8个字节）。
- `uintptr`的长度被设定位足够存放一个指针即可。

Go语言中没有float类型（只有float32和float64）。没有double类型。

与操作系统架构无关的类型都有固定的大小，并在类型的名称中就可以看出来：

整数：

- int8（-128 -> 127）
- int16（-32768 -> 32767）
- int32（-2,147,483,648 -> 2,147,483,647）
- int64（-9,223,372,036,854,775,808 -> 9,223,372,036,854,775,807）

无符号整数：

- uint8（0 -> 255）
- uint16（0 -> 65,535）
- uint32（0 -> 4,294,967,295）
- uint64（0 -> 18,446,744,073,709,551,615）

浮点型（IEEE-754 标准）：

- float32（+- 1e-45 -> +- 3.4 * 1e38）
- float64（+- 5 * 1e-324 -> 107 * 1e308）

你可以通过增加前缀 0 来表示 8 进制数（如：077），增加前缀 0x 来表示 16 进制数（如：0xFF），以及使用 e 来表示 10 的连乘（如： 1e3 = 1000，或者 6.022e23 = 6.022 x 1e23）。