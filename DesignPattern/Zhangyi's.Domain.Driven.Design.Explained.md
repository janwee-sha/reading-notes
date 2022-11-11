# 2.1.2 问题空间和解空间

Allen Newell和Herbert Simon的**问题空间**理论：“人类是通过在问题空间（problem space）中寻找解决方案来解决问题的”。

构建软件也就是从真实世界的问题空间中寻找解决方案，将其映射为理念世界的**解空间**。

从问题空间到解空间：

问题空间（真实世界）——软件构建——>解空间（理念世界）

# 2.3.1 控制规模

**限界上下文**和**上下文映射**是DDD中控制系统规模的手段。

# 2.3.2 清晰结构

通过**分层结构**将领域分离出来，在业务逻辑和技术实现之间划定明确一条的边界。

业务逻辑封装到**领域层**，支撑业务逻辑的技术实现放到**基础设施层**，领域层之上的**应用层**一方面作为业务逻辑的外观，暴露能够体现业务用例的应用服务接口，另一方面又是业务逻辑与技术实现的黏合剂。

# 4.2.2 统一语言

形成统一的领域术语，尤其是基于模型的语言概念，是让沟通达成一致的前提。

# 6.2.2 业务服务

**业务服务**是角色主动向目标系统发起服务请求完成的一次完整的功能交互，体现了服务价值的业务行为。

业务服务的**角色**通常包括**用户**、**策略**或**伴生系统**。

例如：

**文学平台的业务服务**

- **驻站作者申请业务服务**：

申请人->提交驻站作者申请

审批人->审批驻站作者申请

- **作品创作业务服务**：

驻站作者->创作作品、预览作品、发布作品、调整收费模式

编辑->添加作品建议

# 6.3.1 子领域模型

1.子领域的共识

- 子领域属于问题空间的范畴；

- 子领域用于分辨问题问题空间的核心问题还是次要问题。

**核心子领域**是目标系统最为核心的业务资产，体现了目标系统的核心价值。

**通用子领域**包含的内容缺乏领域个性，如各行各业的领域都需要授权认证、企业组织等业务。

**支撑子领域**包含的内容为核心子领域提供了支撑，如物流系统的路径规划业务需要用到地图服务。

# 7.1.2 架构方案的推演

当变化不可避免时，一种行之有效的方法是**共同顺应变化的方向**，借此降低变化带来的影响。在领域驱动设计中，**限界上下文**作为一种软件元素**将业务架构与应用架构绑定起来**。

# 8.2 系统上下文

**系统上下文**代表了目标系统的解空间。

# 9.1 限界上下文的定义

**上下文**其实是动态的业务流程被边界静态切分的产物。

**封装了领域知识的领域对象组成了领域模型，在知识语境的界定下，不同的领域对象扮演不同的角色，执行不同的业务活动，并与限界上下文内的其他非领域模型对象一起，对外提供完整的业务能力。**

限界上下文之间的复用体现为对业务能力的复用，而非对知识语境边界内领域模型的复用。

# 9.2 限界上下文的特征

在识别限界上下文时，必须考虑它的业务特征：

- 它是领域模型的知识语境；
- 它是业务能力的纵向切分。

在设计限界上下文时，必须考虑它的设计特征：

- 它是自治的架构单元。

# 10.2.1 防腐层

防腐层往往位于下游，通过它隔离上游限界上下文可能发生的变化。

防腐层仅仅起到“隔离”的作用，**防腐层从未提供真正的业务实现**，业务实现被放到了另一个限界上下文中，防腐层会向它发起调用。

# 10.2.2 开放主机服务

设计开放主机服务，就是定义公共服务的协议，包括通信的方式、传递消息的格式（协议）。

# 10.2.3 发布语言

**发布语言**是一种公共语言，用于两个限界上下文之间的模型转换。防腐层和开放主机服务都是访问领域模型时建立的一层包装。

防腐层和开放主机服务操作的对象都不应该时各自的领域模型。
# 第11章 服务契约设计

全局分析阶段输出的业务需求即是**业务服务**（问题空间）。业务服务满足了角色的服务请求，在解空间体现为服务与客户的协作关系，形成的协作接口即是**契约**。

## 11.1 消息契约

消息契约对应上下文映射的发布语言模式，根据客户端发起对服务操作的类型，分为命令、查询和事件。

- **命令**：是一个动作，要求其他服务完成某些操作，会改变系统的状态。
- **查询**：是一个请求，查看是否发生了什么事。不会改变系统的状态。
- **事件**：既是事实又是触发器，用通知的方式向外部表明发生了某些事。

## 11.2 服务契约

### 11.2.1 应用服务

Eric Evans定义了领域驱动设计的分层架构，在领域层和用户界面层之间引入了**应用层**。

应用层要尽量简单，不包含业务规则或者知识，只为领域层中的领域对象协调任务，分配工作，使它们互相协作。

应用层存在的意义在于引入它作为间接的层来封装细粒度的领域模型对象以利于客户端调用，使得调用者遵循最小知识原则。

****应用服务准则一****：不包含领域服务的业务服务应被定义为应用服务。

**应用服务准则二**：与横切关注点协作的服务应被定义为应用服务。

# 15.2 实体

## 15.2.3 领域行为

**1.变更状态的领域行为**

领域驱动设计认为，由业务代码组成的实现模型是领域模型的一部分，业务代码的类名、方法名应从业务角度表达领域逻辑。如果不考虑一些框架对实体类get/set访问器的限制，应让变更状态的方法名满足业务含义。

例如：修改产品价格的领域行为应该定义为changePriceTo(newPrice)而非setPrice(price)。


```
public class Product extends Entity<ProductId> {
    public void changePriceTo(Price newPrice) {
        if (this.price.sameCurrency(newPrice)) {
            throw new CurrencyException("Cannot change the price of this product to a different currency");
        }
        this.sellingPrice = newPrice;
    }
}
```


此时的领域行为不再是一个简单的设置操作，它蕴含了领域逻辑。

**2.自给自足的领域行为**

自给自足意味着实体对象只操作自己的属性，不外求于别的对象。

**3.互相协作的领域行为**

实体不可能都做到自给自足，有时也需要调用者提供必要的信息。这些信息往往通过方法参数传入，这就形成了领域对象间互相协作的领域行为。

# 15.4 聚合

## 15.4.1 类的关系

对象建模需要表达的类关系包括：

- **泛化**（generalization）：体现了通用的父类与特定的子类之间的关系
- **关联**（association）：类之间的一种结构关系，用以指定一个类的对象与另一个类之间的对象那个之间存在连接关系。
- **依赖**（dependency）：代表一个类使用了另一个类的信息或服务。

## 15.4.2 模型的设计约束

控制类的关系的手段：

- 去除不必要的关系；
- 降低耦合的强度；
- 避免双向耦合。

## 15.4.3 聚合的定义和特征

Eric Evans：“将实体和值对象划分为聚合并围绕聚合定义边界。选择一个实体作为每个聚合的根，并允许外部对象仅能持有聚合根的引用”。

聚合的基本特征：

- 聚合是包含了实体和值对象的一个边界。
- 聚合内包含的实体和值对象形成了一棵树，只有实体才能作为这棵树的根。
- 外部对象只允许持有聚合根的引用，以起到边界的控制作用。
- 聚合作为一个完整的领域概念整体，其内部维护这个领域概念的完整性，体现业务上的不变量约束。
- 由聚合根统一对外提供履行该领域概念职责的行为方法，实现内部各个对象之间的行为协作。

聚合是边界，不是对象。

## 15.4.4 聚合的设计原则

**1.完整性**

聚合作为一个受到边界控制的领域共同体，对外由聚合根体现为一个统一的概念，对内则管理和维护着高内聚的对象关系。对内和对外具有一致的生命周期。

**2.独立性**

考虑独立性时，可以针对聚合内的非聚合根实体询问：

- 目标聚合是否足够完整；
- 待合并实体是否会被调用者单独调用。

**3.不变量**

在数据变化时必须白痴的一致性规则，设置聚合成员间的内部关系。

**4.一致性**

可以理解为事务的一致性，是一种特殊的不变量。

**5.最高原则**

只有聚合根才是访问聚合边界的唯一入口。

## 15.4.5 聚合的协作

聚合的协作形式：

- 聚合根的对象引用；
- 聚合根身份标识的引用。

## 15.5.1 工厂

**1.由被依赖聚合担任工厂**

聚合工厂往往由被引用的聚合来承担，如此就可以将自己拥有的信息传给被创建的聚合产品。


```
public class Blog {
    public Post createPost(String title,String content) {
        return new Post(this.id,title,content,this.authorId);
    }
}
```

**2.引入专门的聚合工厂**

由于不建议聚合依赖于访问外部资源的端口，专门的聚合工厂的好处是可以依赖端口获取创建聚合时必须的值。


```
public class ProductFactory {
    private final ExchangeRateConverter converter;
    
    @Autowired
    public ProductFactory(ExchangeRateConverter converter) {
        this.converter = converter; 
    }
    
    public Product createProduct(String name, String description, Price price) {
        Money valueOfprice = converter.convert(price.value());
        return new Product(name,description,new Price(valueOfPrice));
    }
}
```

**3.聚合自身担任工厂**

使用简单工厂模式在聚合类中创建聚合实例。

**4.消息契约模型或装配器担任工厂**

```
public class PlacingOrderRequest {
    public Order toOrder() {...}
}
```

**5.使用构建者组装聚合**

```
Flight flight = Flight.prepareBuilder("CA4116")
                .beCarriedBy("CA")
                .departFrom("PEK")
                .arriveAt("CTU")
                .boardOn("C29")
                .flyingAt(LocalDate.of(2019, 8, 8))
                .build();
```