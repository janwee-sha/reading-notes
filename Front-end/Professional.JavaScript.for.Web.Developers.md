> The following is a reference to [Professional JavaScript for Web Developers, 4th Edition](https://www.oreilly.com/library/view/professional-javascript-for/9781119366447)

# 1. What is JavaScript?

When JavaScript first appeared in 1995, its main purpose was to handle some of the input validation that had previously been left to server-side languages such as Perl.

Since that time, JavaScript has grown into an important features of every major web browser on the market. No longer bound to simple data validation, JavaScript now interacts with nearly all aspects of the browser window and its contents.
## JavaScript Implementations

A complete JavaScript implementations is made up of the following three distinct parts:

- The Core(ECMAScript)
- The Document Object Model(DOM)
- The Browser Object Model(BOM)

### ECMAScript

ECMAScript，即ECMA-262定义的语言，并不局限于Web浏览器。ECMA-262将这门语言作为一个基准来定义，以便在它之上再构建更稳健的脚本语言。Web浏览器只是ECMAScript实现可能存在的一种宿主机环境。

ECMA-262描述了这门语言的如下部分：

- 语法
- 类型
- 语句
- 关键字
- 保留字
- 操作符
- 全局对象

**ECMAScript版本**

ECMA-262第1版本质上跟网景的JavaScript 1.1相同，只不过删除了所有浏览器特定的代码，外加少量细微的修改。

第2版只是做了一些编校工作。

第3版更新了字符串处理、错误处理和数值输出。增加了对正则表达式、新的控制语句、try/catch异常处理的支持。

第4版包括强类型变量、新语句和数据结构、真正的类和经典的继承、以及操作数据的新手段。

第5版包括原生的解析和序列化JSON数据的JSON对象、方便继承和高级属性定义的方法、以及新的增强ECMAScript引擎解释和执行代码能力的严格范式。

第6版包含了该规范有史以来最重要的一批增强性能，正式支持了类、模块、反射、代理和众多新的数据类型。

第7版包含少量的语法层面的增强。

第8版增加了异步函数（async/await）、SharedArrayBuffer及Atomics API，以及Object.values()/Object.entries()/Object.getOwnPropertyDescriptors()和字符串填充方法，另外明确支持对象字面量后面的逗号。

第9版包括异步迭代、剩余和扩展属性、一组新的正则表达式特性、Promise finally()，以及模板字面量修订。

第10版增加了Array.prototype.flat()/flatMap()、String.prototype.trimeStart()/trimEnd()、Object.fromEntries()方法，以及Symbol.prototype.description属性，明确定义了Function.prototype.toString()的返回值并固定了Array.prototype.sort()的顺序。另外，解决了与JSON字符串兼容的问题，并定义了catch子句的可选绑定。

### The Document Object Model

文档对象模型（DOM）是一个应用编程接口，用于在HTML中使用扩展的XML。DOM将整个页面抽象为一组分层节点。

DOM通过创建表示文档的树，让开发者可以随心所欲地控制网页中地内容和结构。

**为什么DOM是必需的**

由于网景和微软采用不同方式开发DHTML，开发者写一个HTML页面就可以在任何浏览器中运行的好日子就此终结。

为了保持Web跨平台的也行，万维网联盟（W3C）开始了指定DOM标准的进程。

**DOM级别**

DOM Level 1规范由两个模块：DOM Core和DOM HTML。前者提供了一种映射XML文档，从而方便访问和操作文档任意部分的方式；后者扩展了前者，并增加了特定于HTML的对象和方法。

DOM Level 1的目标是映射文档结构与，而DOM Level 2的目标则宽泛得多。后者增加了对鼠标和用户界面事件、范围、遍历（迭代DOM节点的方法）的支持，且通过对象接口支持了层叠样式表（CSS）。另外，DOM Level 1中的DOM Core也被扩展已包含对XML命名空间的支持。

DOM Level 2新增模块：

- DOM视图
- DOM事件
- DOM样式
- DOM遍历和范围

DOM Level 3增加了以统一的方式加载和保存文档的方法（包含在一个叫DOM Load and Save的新模块中），还有验证文档的方法（DOM Validation）。在Level 3中，DOM Core经过扩展支持了所有XML 1.0的特性，包括XML Infoset、XPath和XML Base。

目前，W3C不再按照Level来维护DOM了，而是作为DOM Living Standard来维护，其快照成为DOM 4。新增内容包括替代Mutation Events的Mutation Observers。

**其他DOM**

除了DOM Core和DOM HTML接口，有些其他语言也发布了自己的DOM标准：

- 可伸缩矢量图（SVG，Scalable Vector Graphics）
- 数学标记语言（MathML，Mathematical Markup Language）
- 同步多媒体集成语言（SMIL，Synchronized Multimedia Integration Language）

### BOM

IE3和Netscape Navigator 3提供了**浏览器对象模型** API，用于支持访问和操作浏览器的窗口。使用BOM，开发者可以操控浏览器显示页面之外的部分。BOM是唯一一个没有相关标准的JavaScript实现。

BOM的常见范畴：

- 弹出新浏览器窗口的能力；
- 移动、缩放和关闭浏览器窗口的能力；
- navigator对象，提供关于浏览器加载页面的详尽信息；
- screen对象，提供关于用户屏幕分辨率的详尽信息；
- performance对象，提供浏览器内存占用、导航行为和时间统计的详尽信息；
- 对cookie的支持；
- 其他自定义对象，如XMLHttpRequest和IE的ActiveXObject。

# 2. JavaScript in HTML

## The `<script>` Element

`<script>`元素有8个属性：

- async：可选。表示应该立即开始下载脚本，但不能阻止其他页面动作。只对外部脚本文件有效。
- charset：可选。使用src属性指定的代码字符集。
- crossorigin：可选。配置相关请求的CORS（跨域资源共享）设置。
- defer：可选。表示脚本可以延迟到文档被完全解析和显示之后再执行。只对外部脚本有效。
- integrity：可选。允许比对接收到的资源的和指定的加密签名以验证子资源完整性。
- language：废弃。最初用于表示代码中的脚本语言。
- src：可选，表示包含要执行的代码的外部文件。
- type：可选。代替language，表示代码块中脚本语言的内容类型（也称MIME类型）。

使用`<script>`的方式：

- 直接在网页中嵌入；
- 在外部文件中包含。

嵌入行内的JavaScript代码，直接把代码放在`<script>`元素中就行：

```
<script>
    function hi() {
      console.log("Hi!");
    }
</script>
```

要包含外部文件中的JavaScript，就必须使用src属性。如：

```
<script src="example.js"></script>
```

在XHTML文档中，可以忽略结束标签，比如：

```
<script src="example.js"/>
```

另外，使用了src属性的`<script>`标签中不应该再包含其他JavaScript代码。

`src`属性指向的URL代表的资源可以与父页面不在同一个域中。`integrity`属性用于方法不同域中的一个武器，但并不是所有浏览器都支持。

### Tag Placement

过去，所有`<script>`元素都被放在页面中的`<head>`标签内，主要目的是把外部的CSS和JavaScript文件都集中放在一起。但这种方式意味着必须把所有JavaScript代码都下载、解析和解释完成后才能开始渲染页面。

现代Web应用通常将所有JavaScript引用放在`<body>`元素中的页面内容后面。

### Deferred Scripts

HTML 4.0为`<script>`元素定义了`defer`属性。这个属性表示脚本在执行的时候不会改变页面的结构。脚本会被延迟到整个页面都解析完毕后再执行。该属性只对外部脚本文件才有效。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter2/DeferredScripts)查看示例代码。

### Asynchronous Scripts

HTML5为`<script>`元素定义了`async`属性。标记`async`的外部脚本会立即开始下载，但不保证能按照它们出现的次序执行。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter2/AsyncronousScripts)查看示例代码。

### Dynamic Script Loading

除了`<script>`标签，还有其他方式可以加载脚本。因为JavaScript可以使用DOM API，所以通过向DOM中动态添加script元素同样可以加载指定的脚本。

这种方式创建的`<script>`默认是以异步方式加载的，相当于添加了`async`属性。

可以使用`script.async = false;`明确将脚本设置为同步加载。但会影响它们再资源获取队列中的优先级，可能会严重影响性能。想让预加载器直到这些动态请求文件的存在，可以在文件头显式声明它们：

```
<link rel="preload" href="source.js">
```

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter2/DynamicScriptLoading)查看示例代码。

## Document Mode

IE5.5发明了文档模式的概念，即可以使用`doctype`切换文档模式。不同文档模式的主要区别在于CSS渲染的内容方面，但对JavaScript也有一些关联影响。

**混杂模式**

让IE像IE5一样（支持一些非标准的特性）。以省略文档开头的`doctype`声明作为开关。

**标准模式**

让IE具有兼容标准的行为。

**准标准模式**

IE初次支持文档模式切换以后，随着其他浏览器的普遍实现而出现的第三种文档模式。该模式下浏览器支持很多标准的特性，但是没有标准规定得那么严格。主要区别在于如何对待图片元素周围的空白。

相关说明参考[W3C站点文档](https://www.w3.org/wiki/Doctypes_and_markup_styles)。

## `<noscript>`元素

`<noscript>`元素可以包含任何可以出现在`<body>`中的HTML元素，`<script>`除外。浏览器将显示包含在`<noscript>`中的内容的情况：

- 浏览器不支持脚本；
- 浏览器对脚本的支持被关闭。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter2/NoScript)查看示例代码。

# 3. Language Basics

## Syntax

### Case-Sensitivity

ECMAScript中的一切都区分大小写。无论是变量、函数名还是操作符，都区分大小写。

### Identifiers

**标识符**是变量、函数、属性或函数参数的名称。标识符规范：

- 第一个字符必须是一个字母、下划线（_）或美元符号（$）；
- 剩下的其他字符可以是字母、下划线、美元符号或数字。

其中字母可以是扩展ASCII中的字母，也可以是Unicode的字母字符。

按照惯例，ECMAScript标识符使用驼峰大小写形式。

### Strict Mode

ECMAScript 5增加了严格模式的概念。ECMAScript 3的一些不规范写法在这种模式下会被处理，对于不安全的活动将抛出错误。要对整个脚本启用严格模式，可在脚本开头加上：

```
"use strict";
```

也可以单独指定一个函数在严格模式下执行，只要把该预处理指令放到函数体的开头即可：

```
function doSomething() {
    "use strict";
}
```

### Statements

ECMAScript中的语句以分号结尾。省略分号意味着由解析器确定语句在哪里结尾。

多条语句可以合并到一个C语言风格的代码块中：

```
if(test) {
    test = false;
    console.log(test);
}
```

## Variables

ECMAScript变量是松散类型的，意思是变量可以用于保存任何类型的数据。每个表变量只不过是一个用于保存任意值的命名占位符。

声明变量的3个关键字：`var`、`const`和`let`。

### The 'var' Keyword

`var`操作符定义变量的方式：

```
var message; //定义一个名为message的变量，可保存任何类型的值，此时变量值为特殊值undefined
```
```
var message = "hi"; //message被定义为一个保存字符串hi的变量
```

```
var message;
message = "hi"; //合法，但不推荐
```

**1. var声明作用域**

使用`var`在一个函数内部定义一个变量，该变量将在函数退出时被销毁。

但在函数内定义变量时省略 `var` 操作符，可以创建一个全局变量：

可以使用逗号分隔以同时定义多个变量。

**2. var声明提升**

在调用 `var` 变量的代码之后声明该 `var` 变量时，代码可以正常运行。因为使用这个关键字声明的变量会自动提升到函数作用域顶部。

另外，反复多次使用 `var` 声明同一个变量也没有问题。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter3/Var)查看示例代码。

### 'let' Declarations

`let` 跟 `var` 的作用差不多，但有着非常重要的区别。

最明显的区别是， `let` 声明的范围是块作用域，而 `var` 声明的范围是函数作用域。

`let` 不允许同一块作用域中出现冗余声明。

同一函数内的不同块中可以重复声明 `let` 变量。

**1. 暂时性死区**

`let` 声明的变量不会再作用域中被提升。

在解析代码时，JavaScript引擎也会注意出现在块后面的 `let` 声明，只不过在此前不能以任何方式来引用未引用的变量。在 `let` 声明之前的执行瞬间被称为“暂时性死区”，在此阶段引用任何后面才声明的变量都会抛出ReferenceError。

**2. 全局声明**

有别于 `var` 关键字，使用 `let` 在全局作用域中声明的变量不会成为 `window` 对象的属性。

但 `let` 声明仍然是在全局作用域中发生的，相应变量会在页面的生命周期内存续。

**3. 条件声明**

因为 `let` 的作用域是块，所以不可能检查前面十分已经使用过同名变量，同时也就不可能在没有声明的情况下使用它。

使用 `try/catch` 语句或 `typeof` 操作符也不能解决，因为条件块中 `let` 声明的作用域仅限于该块。

所以，对于 `let` 这个新的ES6声明关键字，不能依赖条件声明模式。

**4. for循环中的let声明**

for循环定义的 `var` 迭代变量会渗透到循环体外部， `let`变量则不会：

在使用 `var` 时，最常见的问题就是对迭代变量的奇特声明和修改：

```
for (var k = 0; k < 5; ++k) {
    setTimeout(() => console.log(k), 0);
} //预期输出是0、1、2、3、4，但实际会输出5、5、5、5、5
```

而在使用 `let` 声明迭代变量时，JavaScript引擎在后台会为每个迭代循环声明一个新的迭代变量。所以 `console.log` 输出的是我们期望的值：

```
for (let l = 0; l < 5; ++l) {
    setTimeout(() => console.log(l), 0);
} //输出0、1、2、3、4
```

> `setTimeout` 函数的API说明可参考[这里](https://developer.mozilla.org/zh-CN/docs/Web/API/setTimeout)。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter3/Let)查看示例代码。

### 'const' Declarations

`const` 的行为与 `let` 基本相同，唯一一个重要区别是用它声明变量时必须同时初始化变量，且尝试修改 `const` 声明的变量会导致运行时错误。

`const` 声明的限制只适用于它指向的变量的引用。如果 `const` 变量引用的是一个对象，那么修改该对象内部的属性并不违反 `const` 的限制。

不能用 `const` 来声明迭代变量。

点击[这里](https://github.com/janwee-sha/hello-javascript/tree/main/Chapter3/Const)查看示例代码。

### Declaration Styles and Best Practices

**1. 不使用var。**

**2. const优先，let次之。**

## Data Types

ECMAScript的6种简单数据类型（也称为**原始类型**）：

- Undefined
- Null
- Bollean
- Number
- String
- Symbol

1种复杂数据类型：

- Object

### The `typeof` Operator

`typeof` 语法：

```
typeof [变量|字面量|函数];
```

> 注意：函数在ECMAScript中被认为是对象，并不代表一种数据类型。为此，有必要通过 `typeof` 操作符来区分函数和其他对象。

点击[这里](https://github.com/janwee-sha/hello-javascript/blob/main/Chapter3/DataTypes/TypeOfOperator.html)查看示例代码。

### The Undefined Type

`var` 或 `let` 未初始化时，其类型值为 `undefined`。

包含 `undefined` 值得变量并不等同于未定义变量。

`undefined` 是一个假值。因此，可以使用布尔测试的方式检测变量是否被赋值。

点击[这里](https://github.com/janwee-sha/hello-javascript/blob/main/Chapter3/DataTypes/UndefinedType.html)查看示例代码。

### The Null Type

逻辑上，`null` 值表示一个空对象指针。

`undefined` 值是由 `null` 值派生而来的，因此ECMAScript-262将它们定义为表面上相等。

`null` 也是一个假值。

点击[这里](https://github.com/janwee-sha/hello-javascript/blob/main/Chapter3/DataTypes/NullType.html)查看示例代码。

### The Boolean Type

`Boolean` 类型有两个字面值：`true` 和 `false`。

其他ECMAScript类型的值都有相应布尔值的等价形式。要将一个其他类型的值转换未布尔值，可以调用特定的 `Boolean()` 转型函数。

不同类型与布尔值之间的转换规则：


| DATA TYPE | VALUES CONVERTED TO TRUE | VALUES CONVERTED TO FALSE |
| --- | --- | --- |
| Boolean | true | false |
| String | Any nonempty string | "" (empty string) |
| Number | Any nonzero number (including infinity) | 0, NaN |
| Object | Any object | null |
| Undefined | n/a | undefined |

`if` 等流控制语句会自动执行其他类型值到布尔值的转换。

### The Number Type

Number类型使用IEEE 754格式表示整数和浮点值（在某些语言中也叫双精度值）。不同的数值类型相应地也有不同的数值字面量格式。

**Integer**

- Decimal Integer——最基本的数值字面量是十进制整数，直接写出来即可。
- Octal Integer——八进制字面量的第一个数字必须是0，后接相应的八进制数值，若字面量中包含的数字超出了应有的范围，则会忽略前缀0，后面的数字序列被当作十进制数。ES6中的八进制通过前缀0o来标识；严格模式下，前缀0会被视为语法错误，若要表示八进制值，应该使用前缀0o。
- Hexadecimal Integer——To create a hexadecimal literal, you must make the first two characters `0x` , followed by any number of hexadecimal digits (0 through 9, and A through F).

> Note: 由于JavaScript保存数值的方式，实际上存在正零（+0）和负零（-0）。两者在任何情况下都被认为是等同的。

**Floating-Point Values**

要定义浮点值，数值中必须包含小数点，而且小数点后必须至少有一个数字。小数点前面的整数不是必需的，但推荐加上。

存储浮点值使用的内存空间是存储整数值的两倍，所以ECMAScript总是尽量把值转换为整数。小数点后面没有数字或只跟着0的情况下，该浮点值会被转换为整数值。

对于非常大或非常小的数值，浮点值可以用科学计数法来表示。ECMAScript中科学计数法的格式为一个数值（整数或浮点数）后跟一个大写或小写的字母e，再加上一个要乘的10的次幂数。

默认情况下，ECMAScript会将小数点后至少包含6个零的浮点值转换为科学计数法。

浮点值的精确度最高可达17位小数，但在算术计算中远不如整数精确。例如，0.1加0.2得到的不是0.3，而是0.30000000000000004。

**Range of Values**

ECMAScript并不支持表示所有数值。最小数值保存在 `Number.MIN_VALUE` 中，这个值在多数浏览器中是 `5e-324`；最大数值保存在 `Number.MAX_VALUE` 中，这个值在多数浏览器中是 `1.7976931348623157e+308`。

超过JavaScript可表示范围的数值会被自动转换位一个特殊的 `Infinity` 值，负数为 `-Infinity`，正数为 `Infinity`。

`Infinity` 值不能再做进一步的计算。可以使用 `isFinite()` 函数测试某数值是否无穷数。

**3. NaN**

有一个特殊的数值叫 `NaN` ，意思是“Not a Number”，用以表示本来要返回数值的操作失败了。

任何涉及 `NaN` 的操作始终返回 `NaN`。`NaN` 不等于包括 `NaN` 在内的任何值。

ECMAScript提供了 `isNaN()` 函数，用于判断数值是否“不是数值”。该函数会尝试将给定参数转换为一个数值。不能转换的会返回 `false`。

**4. 数值转换**

有3个函数可以将非数值转换为数值： `Number()`、`parseInt()` 和 `parseFloat()`。`Number()` 参数可以是任何类型，后两者用于将字符串转换为数值。

点击[这里](https://github.com/janwee-sha/hello-javascript/blob/main/Chapter3/DataTypes/NumberType.html)查看示例代码。

### The String Type

字符串数据类型表示零或多个16位Unicode字符序列。字符串可以用双引号（"）、单引号（'）或反引号（`）标识。

**Character Literals**

Character literals to represent nonprintable or otherwise useful characters:


| LITERAL | MEANING |
| --- | --- |
| \\n | New line |
| \\t | Tab |
| \\b | Backspace |
| \\r | Carriage return |
| \\f | Form feed |
| \\\\ | Backslash |
| \\' | Single quote |
| \\" | Double quote |
| \\xnn | A character represented by hexadecimal code `nn` |
| \\unnnn | A unicode character represented by the hexadecimal code `nnnn` |


**The Nature of Strings**

ECMAScript中的字符串是不可变的。修改某个变量中的字符串值实际上是将包含新值的另一个字符串保存到该变量。

**Converting to a String**

- `toString()` 函数。`toString()` 多数情况下不接受任何参数，但对数值调用这个方法时，`toString()` 可以接收一个底数参数，即以什么为底数来输出数值的字符串表示。
- `String()`函数。若参数值有`toString()`方法，则调用该方法；若为`null`/`undefined`，则返回`null`/`undefined`。

**Template Literals**

ECMAScript 6新增了使用模板字面量定义字符串的能力。模板字面量保留换行字符，可以跨行定义字符串。

模板字面量在定义模板时特别有用。如下面的HTML模板：

```
let pageHtml = `
    <div>
        <a href="#">A link</a>
    </div>`;
```

**Interpolation**

模板字面量最常用的一个特性时支持字符串插值，也就是可以在一个连续定义中插入一个或多个值。

字符串插值通过在`${}`中使用一个JavaScript表达式实现。

```
let string = `${ a } to the ${ b } power is ${ a * b }`;
```

**Template Literal Tag Functions**

> To be continued

**Raw Strings**

> To be continued

### The Symbol Type

> To be continued

### The Object Type

> To be continued

