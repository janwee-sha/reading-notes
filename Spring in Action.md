# 第10章 理解反应式编程

## 10.1 反应式编程概览

反应式编程是一种可以替代命令式编程的编程范式。这种可替代性的原因在于反应式编程解决了命令式编程的一些限制。

**定义反应式流**

反应式流是由Netflix、Lightbend和Pivotal的工程师于2013年年底开始制定的一种规范。反应式流旨在提供无阻塞回压的异步流处理标准。

反应式编程具有异步特性，使我们能够并行执行任务，从而实现更高的可伸缩性。通过回压，数据消费者可以限制它们想要处理的数据数量，避免被过快的数据源所淹没。

反应式流规范可以总结为4个接口：Publisher、Subscriber、Subscription和Processor。

Publisher负责生成数据，并将数据发送给Subscription（每个Subscriber对应一个Subscription）。

```
public interface Publisher< T> {
    void subscribe( Subscriber<? super T> subscriber);
}
```
一旦Subscriber订阅成功，就可以接收来自Publisher的事件。

```
public interface Subscriber< T> {
    void onSubscribe( Subscription sub);
    void onNext( T item);
    void onError( Throwable ex);
    void onComplete();
}
```

通过Subscription，Subscriber可以管理其订阅情况：

```
public interface Subscription {
    void request( long n);
    void cancel();
}
```

Processor接口是Subscriber和Publisher：

```
public interface Processor<T, R>
    extends Subscriber<T>, Publisher<R>{}
```
当作为Subscriber时，Processor会接收数据并以某种方式对数据进行处理。然后将角色转变为Publisher，并将处理的结果发布给它的Subsriber。



```
Mono.just("Dune")
    .map(String::toUpperCase)
    .map(s -> "\"" + s + "\"")
    .subscribe(System.out::println);
```

