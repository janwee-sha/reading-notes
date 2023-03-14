# Bytecode in Java

> Reference Link: <https://dzone.com/articles/introduction-to-java-bytecode>

## What is Java Bytecode?

Java bytecode is the bytecode-structured instruction set of the JVM.

## JVM Data Types

Java is statically typed, which affects the design of the bytecode instructions such that an instruction expects itself to operate on values of specific types. For example, there are several add instructions to add two numbers: `iadd`, `ladd`, `fadd`, `dadd`. They expect operands of type, respectively, `int`, `long`, `float`, and `double`.

The data types defined by the JVM are:

1.  Primitive types:

    *   Numeric types: `byte` (8-bit 2's complement), `short` (16-bit 2's complement), `int` (32-bit 2's complement), `long` (64-bit 2's complement), `char` (16-bit unsigned Unicode), `float` (32-bit IEEE 754 single precision FP), `double` (64-bit IEEE 754 double precision FP)
    *   `boolean` type
    *   `returnAddress`: pointer to instruction
2.  Reference types:

    *   Class types
    *   Array types
    *   Interface types

## Bytecode Explored

Each bytecode instruction has the following format:

    opcode (1 byte)      operand1 (optional)      operand2 (optional)      ...

That is an instruction that consists of one-byte opcode and zero or more operands that contain the data to operate.

Within the stack frame of the currently executing method, an instruction can push or pop values onto the operand stack, and it can potentially load or store values in the array local variables. For example:

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
    }

And we get the bytecode instruction after compiling the class as followed:

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

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = calc(a,b);
    }

    static int calc(int a, int b) {
        return (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

Let's see the resulting bytecode:

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

Instead of having the `iadd` instruction, we now get an `invokestatic` instruction, which simply invokes the static method `calc`.

We notice that the `invokestatic` instruction occupies 3 bytes by looking at the address, which jumped from 6 to 9. This is because, `invokestatic` includes two additional bytes to construct the reference to the method to be invoked (in addition to the opcode). The reference is shown by `javap` as `#2`, which is a symbolic reference to the `calc` method.

The other new information is the code for the `calc` method itself. It first loads the first integer argument onto the operand stack (`iload_0`). The next instruction, `i2d`, converts it to a `double` by applying widening conversion. The resulting `double` replaces the top of the operand stack.

The next instruction pushes a `double` constant `2.0d` onto the operand stack. Then the static `Math.pow` method is invoked with the two operand values prepared so far (the first argument to `calc` and the constant `2.0d`). When the `Math.pow` method returns, its result will be stored on the operand stack of its invoker.

The same procedure is applied to compute `Math.pow(b, 2)`.

The next instruction, `dadd`, pops the top two intermediate results, adds them, and pushes the sum back to the top. Finally, `invokestatic` invokes `Math.sqrt` on the resulting sum, and the result is cast from `double` to `int` using narrowing conversion (`d2i`). The resulting `int` is returned to the main method, which stores it back to `c` (`istore_3`).

## Instance Creations

Let's introduce a class `Point` to encapsulate XY coordinates:

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

The compiled bytecode for the `main` method:

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

The new instructions encountereted here are `new` , `dup`, and `invokespecial`. The new instruction creates an object of the type specified in the operand passed to it (which is a symbolic reference to the class `Adder`). Memory for the object is allocated on the heap, and a reference to the object is pushed on the operand stack.

The `dup` instruction duplicates the top operand stack value, which means that now we have two references of the `Adder` object on the top of the stack. The next three instructions push two arguments onto the operand stack, and then invoke a special initialization method, which corresponds with the constructor. The next method is where the fields `x` and `y` will get initialized. After the method is finished, the top three operand stack values are consumed, and what remains is the original reference to the created object.

![image](https://github.com/janwee-sha/reading-notes/blob/main/Java/images/Articles.and.Blogs/bytecode_init.png)

Next, `astore_1` pops that `Adder` reference and assigns it to the local variable at index 1 (the `a` in `astore_1` indicates this is a reference value).

The last step invokes the `result` method using `invokevirtual`, which handles dispatching the call to the appropriate method based on the actual type of the object. For example, if the variable a contained an instance of type `SpecialAdder` that extends `Adder`, and the subtype overrides the `result` method, then the overriden method is invoked. In this case, there is no subclass, and hence only one `result` method is available.