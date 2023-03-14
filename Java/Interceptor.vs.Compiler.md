# 解释器 vs 编译器

> Reference Link: <https://www.guru99.com/difference-compiler-vs-interpreter.html>

## 编译器与解释器的关键区别

- 编译器在程序运行前一次性地将接近高层级的编程语言编译为机器码，解释器则是在程序运行期间逐一地将高层级的编程语言转译为机器代码。
- 编译后的代码运行速度比解释后的代码快。
- 编译器在编译结束后显示所有的错误，解释器单独显示每一行的错误。
- 编译器基于翻译链接加载模型，解释器基于解释方法。
- 编译器读取整个程序，而解释器读取单行代码。

**编译器的工作方式**

```mermaid
flowchart LR
    A[Source Code] --> B[Compiler] --> C[Machine Code] --> D[ Output ]
    style A fill:#696969,stroke:#333,stroke-width:4px;
    style B fill:#A020F0,stroke:#333,stroke-width:4px;
    style C fill:#458B74,stroke:#333,stroke-width:4px;
    style D fill:##000000,stroke:#333,stroke-width:4px;
```

**解释器的工作方式**

```mermaid
flowchart LR
    A[Source Code] --> B[Interpreter] --> C[Output]
    style A fill:#696969,stroke:#333,stroke-width:4px;
    style B fill:#A020F0,stroke:#333,stroke-width:4px;
    style C fill:##000000,stroke:#333,stroke-width:4px;
```