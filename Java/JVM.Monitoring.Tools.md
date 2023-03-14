# JVM Monitoring Tools

## `jps`

Java Virtual Machine Process Status Tool. You use the `jps` command to list the instrumented JVMs on the target system.

The usage is shown as following:

    jps [-q] [-mlvV] [<hostid>]

For example：

    $ jps -m -l
    16208 sun.tools.jps.Jps -m -l
    4340 com.janwee.bookstore.eurekaserver.EurekaServerApplication

## `jstack`

jstack主要用来查看某个Java进程内的线程堆栈信息。

The following is its usage：

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

## `jmap` and `jhat`

`jmap`（Memory Map）用来查看堆内存使用状况，一般结合`jhat`（Java Heap Analysis Tool）使用。

Usage of `jmap`:

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

使用`jmap -histo[:live] pid`查看堆内存中的对象数目、大小统计直方图，如果带上`live`则只统计活对象，如下：

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

用`jmap`把进程内存使用情况dump到文件中，再用 `jhat`分析查看：

    $ jmap -dump:format=b,file=factorial-task-dump 7976
    Dumping heap to C:\Users\Janwee\factorial-task-dump ...
    Heap dump file created

使用`jhat`分析dump文件：

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

## `jstat`

Usage:

```bash
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

    $ jstat -gc 9528 250 4
     S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
    10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
    10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
    10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000
    10240.0 10240.0  0.0    0.0   64512.0  16997.9   171008.0     0.0     4480.0 769.8  384.0   75.8       0    0.000   0      0.000    0.000

What the columns mean:

*   S0C, S1C, S0U, S1U: Survivor 0/1 区容量和使用量
*   EC, EU: Eden区容量和使用量
*   OC, OU: 老年代容量和使用量
*   MC, MU: 元空间容量和使用量
*   YGC、YGT：年轻代GC次数和GC耗时
*   FGC、FGCT：Full GC次数和Full GC耗时
*   GCT：GC总耗时

堆内存 = 年轻代 + 年老代 + 元空间
年轻代 = Eden区 + 两个Survivor区（From和To）