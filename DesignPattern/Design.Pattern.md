# 设计模式按性质可以划分为

- 创造型（creational）
- 结构型（structural）
- 行为型（behavioral）


. | creational | structural | behavioral
---|--- | --- | ---
Class | Factory Method | Adapter(Class) | Interpreter,Template Method
Object | Abstract Factory,Builder,Prototype,Singleton |Adapter(Object),Bridge,Composite,Decorator,Facade,Flyweight,Proxy | Chain of Responsibility,Command,Iterator,Mediator,Memento,Observer,State,Strategy,Visitor


# 设计模式四要素

- 模式名：助记名
- 问题：应在何时使用模式
- 解决方案：设计的组成成分、他们之间的相互关系及各自的职责和协作方式
- 效果：模式应用的效果及使用模式应权衡的问题

入门：
Abstract Factory
Adapter
Composite
Decorator
Factory Method
Observer
Strategy
Template Method

# 设计模式怎样解决设计问题

## 寻找合适的对象
## 决定对象的粒度
## 指定对象接口
对象声明的每一个操作指定操作名、作为参数的对象和返回值，这就是所谓的操作的型构（signature）。对象操作所定义的所有操作型构的集合被称为该对象的接口（interface）。对象接口描述了该对象所能接受的全部请求的集合，任何匹配对象接口中型构的请求都可以发送给该对象。
    

# 面向对象设计需要考虑的因素

- 封装
- 粒度
- 灵活性
- 依赖关系
- 灵活性
- 性能
- 演化
- 复用
- 其他

# 面向对象设计原则

- 针对接口编程，而不是针对实现编程
- 优先使用对象组合（黑箱复用），而不是类继承（白箱复用）
