# 5. Java Garbage Collection Tuning

> Quoted from <https://sematext.com/blog/java-garbage-collection-tuning/>

*   `-XX:+PrintFlagsInitial` flag, shows available options and their default values.
*   `-XX:+PrintFlagsFinal` flag, shows available options and their current values.
*   `jinfo` command, shows the value of a particular parameter.
*   `-XX:+PrintCommandLineFlags` flag, shows all customized parameter values.

## Heap Size

*   `-Xms`, `-Xmx` parameters: The minimum and maximum size of heap
*   `-XX:+AlwaysPreTouch` flag: Add this flag to load the memory pages into memory at the start of the application.
*   `-Xmn` parameter: It allows us to explicitly define the size of the young generation heap size.

## Serial Garbage Collector

The Serial Garbage Collector is the simplest, single-threaded garbage collector.

*   `-XX:+UseSerialGC` flag: Turn on the Serial garbage collector.

For example, after adding the  `-XX:+UseSerialGC` flag, the output of `jmap` shows that Mark Sweep Compact GC is used:

    $ jmap -heap 14804
    ...
    Mark Sweep Compact GC
    ...

## Parallel Garbage Collector

The Parallel garbage collector similar in its roots to the Serial garbage collector but uses multiple threads to perform garbage collection on your application heap.

*   `-XX:+UseParallelGC` flag: Enable the Parallel garbage collector.
*   `-XX:-UseParallelGC` flag: Diable the Parallel garbage collector.

    \$ jmap -heap 4296
    ...
    Parallel GC with 8 thread(s)
    ...

## Tuning the Parallel Garbage Collector

*   `-XX:ParallelGCThreads` flag: Set the number of threads that the garbage collector can use.

For example, after adding `-XX:ParallelGCThreads=4` flag to our application parameters, we get:

    $ jmap -heap 13204
    ...
    Parallel GC with 4 thread(s)
    ...

*   `-XX:MaxGCPauseMillis`: Specify the maximum pause time goal between two consecutive garbage collection events.

For example, with a flag `-XX:MaxGCPauseMillis=100` we tell the Parallel garbage collector that we would like to have the maximum pause of 100 milliseconds between garbage collections. The longer the gap between garbage collections the more garbage can be left on the heap making the next garbage collection more expensive. If the value is too small, the application will spend the majority of its time in garbage collection.

*   `XX:GCTimeRatio` flag: It is defined as `1/(1 + GC_TIME_RATIO_VALUE)` and it’s a percentage of time spent in garbage collection.

For example, setting -XX\:GCTimeRatio=9 means that 10% of the application’s working time may be spent in the garbage collection.

By default, the value of `-XX:GCTimeRatio` flag is set to 99 by the JVM.

&lt;To be continued&gt;