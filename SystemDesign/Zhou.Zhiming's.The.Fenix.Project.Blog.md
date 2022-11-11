# HTTP协议

HTTP协议即超文本传输协议（HyperText Transfer Protocol）是一种用于分布式、协作式和超媒体信息系统的应用层协议，所有WWW文件都必须遵守这个标准。基于TCP/IP通信协议来传递数据。

- HTTP是无连接的。
- HTTP是媒体独立的。
- HTTP是无状态的。

## HTTP消息结构

HTTP是基于C/S的架构模型，通过一个可靠的链接来交换信息，是一个无状态的请求/响应协议。

HTTP使用统一资源标识符（Uniform Resource Identifier，URI）来传输数据和建立连接。

### 客户端请求信息

客户端发送一个HTTP请求到服务器的请求消息包含以下格式：请求行（request line）、请求头部（header）、空行和请求数据四部分。

- **请求行**
 
  包含请求方法、URL、协议版本。

- **请求头部**

  包含头部字段名和值的键值对组。

- **请求数据**

  媒体资源。

### 服务器响应信息

  包含状态行、消息报头、空行和响应正文四部分。

## HTTP请求方法

HTTP1.0定义了三种请求方式：GET、POST和HEAD。

HTTP1.1新增了六种请求方式：OPTIONS、PUT、PATCH、DELETE、TRACE和CONNECT。

- **GET**，请求指定的页面信息，并返回实体主体。
- **HEAD**，类似于GET，只不过返回的响应中没有具体的内容，用于获取报头。
- **POST**，向指定资源提交数据进行处理请求。数据被包含在请求体中。POST请求可能会导致新的资源的建立和/或已有资源的修改。
- **PUT**，从客户端向服务器传送的数据取代指定的文档的内容。
- **DELETE**，请求服务器删除指定的页面。
- **CONNECT**，HTTP1.1预留给能够将连接改为管道方式的代理服务器。
- **OPTIONS**，允许客户端查看服务器的性能。
- **TRACE**，回显服务器收到的请求，主要用于测试或诊断。
- **PATCH**，是对PUT的补充，用来对已知资源进行局部更新。
  
## HTTP响应头信息

- **Allow**，服务器支持哪些请求方法。
- **Content-Encoding**，文档的编码方法。Servlet通过查看Accept-Encoding头（request.getHeader("Accept-Encoding")）检查浏览器支持的方法。
- **Content-Length**，内容长度。只有当浏览器使用持久HTTP连接时才需要这个数据。可以在输出文档修ByteArrayOutputStream，完成后将其长度值放入Content-Length头，最后通过byteArrayStream.writeTo(response.getOutputStream())发送内容。
- **Content-Type**，表示后面的文档属于MIME类型。
- **Date**，当前的GMT时间。
- **Expires**，应该在什么时候认为文档已经过期。
- **Last-Modified**，文档的最后改动时间。客户可用通过If-Modified-Since请求头提供一个日期，该请求将被视为一个条件GET，只有改动时间迟于指定时间的文档才会返回，否则返回一个304（Not Modified）状态。
- **Location**：表示客户应当到哪里去提取文档。Location通常不是直接设置的，而是通过HttpServletResponse的sendRedirect方法，该方法同时设置状态代码为302。
- **Refresh**，表示浏览器应该在多少时间之后刷新文档，以秒计。
- **Server**，服务器名字。Servlet一般不设置这个值，而是Web服务器自己设置。
- **Set-Cookie**，设置和页面关联的Cookie。
- **WWW-Authenticate**，表示客户应该在Authorization头中提供什么类型的授权信息。在包含401（Unauthorized）状态行的应答中这个头是必需的。例如，response.setHeader("WWW-Authenticate", "BASIC realm=＼"executives＼"")。
Servlet一般不进行这方面的处理，而是让Web服务器的专门机制来控制受密码保护页面的访问（例如.htaccess）。


## HTTP状态码

HTTP状态码由三个十进制数字组成，第一个十进制数字定义了状态码的类型。

**响应分为5类：**
- 1xx，信息，服务器收到请求，需要请求者继续执行操作。
- 2xx，成功，操作被成功接收和处理。
- 3xx，重定向，需要进一步的操作以完成请求。
- 4xx，客户端错误，请求包含语法错误或无法完成请求。
- 5xx，服务器错误，服务器在处理请求的过程中发生了错误。

**HTTP状态码列表：**

状态码 | 英文名称 | 描述
--- | --- | ---
100 | Continue | 继续。客户端应继续其请求。
101 | Switching Protocols | 切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议。
**200** | **OK** | **请求成功。一般用于GET与POST请求。**
201 | Created | 已创建。成功请求并创建了新的资源。
202 |Accepted | 已接受。已经接受请求，但未处理完成。
203 | Non-Authoritative Information | 非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本。
204 | No Content | 无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档。
205 | Reset Content | 重置内容。服务器处理成功，用户终端应重置文档视图。可通过此返回码清楚浏览器的表单域。
206 | Partial Content | 部分内容。服务器成功处理了部分GET请求。
300 | Multiple Choices | 多种选择。请求的资源可包括多个位置，响应可返回一个资源特征与地址的列表用于用户终端选择。
**301** | **Moved Permanently** | **永久移动。请求的资源已被永久的移动到新的URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替。**
302 | Found | 临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI。
303 | See Other | 查看其他地址。与301类似。使用GET和POST请求查看。
304 | Not Modified | 未修改。所请求得资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过得资源，通过提供一个头信息指出客户端希望只返回在指定日期之后得资源。

## HTTP Content-Type

用于定义网络文件的类型和网页的编码，决定浏览器将以什么形式、什么编码读取这个文件。

**常见媒体格式类型：**

- text/html：HTML
- text/plain：纯文本
- text/xml：XML
- image/gif：gif图片
- image/jpeg：jpg图片
- image/png：png图片

**以application开头的媒体格式类型：**

- application/xhtml+xml：XHTML
- application/xml：XML数据
- application/atom+xml：Atom XML聚合
- application/json：JSON数据
- application/pdf：pdf
- application/msword：Word文档
- application/octet-stream：二进制流数据（如常见的文件下载）
- application/x-www-form-urlencoded：被编码为key/value格式的form表单数据

**上传文件时使用的媒体格式：**

- multipart/form-data：表单中上传文件时使用的格式。

---

# 事务处理

## 本地事务（Local Transaction）

仅操作单一事务资源的、不需要全局事务管理器进行协调的事务。

本地事务是最基础的一种事务解决方案，只适用于单个服务使用单个数据源的场景。从应用角度看，它是直接依赖于数据源本身提供的事务能力来工作的，在程序代码岑概念，最多只能对事务接口座一层标准化的包装（如JDBC接口），并不能深入参与到事务的运作过程当中，事务的开启、总之、提交、回滚、嵌套、设置隔离级别，乃至事务传播方式，全部都要依赖底层数据源的支持才能工作。

### 实现原子性和持久性

崩溃情形：

- **未提交事务，写入后崩溃** —— 程序还没有执行完事务内包含的操作，但数据库已将部分数据变动写入磁盘，此时出现崩溃，一旦重启之后，数据库须得知崩溃前发生过不完整的数据变更，将已修改数据恢复成没有改过的情形，以保证原子性。

- **已提交事务，写入前崩溃** —— 程序已执行完事务内包含的操作，但数据库未将全部数据变动写入磁盘，此时出现崩溃，一旦重启之后，数据库须得知崩溃前发生过不完成的数据变更，将未修改的数据重新写入，以保证持久性。

写入中间状态与崩溃都无法避免，为保证原子性和持久性，只能在崩溃后采取恢复的补救措施，这种数据恢复操作被称为“**崩溃恢复**”（Crash Recovery）。

**提交日志**（Commit Logging）的事务实现方式：仅进行顺序追加的文件写入的形式先记录到磁盘中，**在日志记录全部都安全写入磁盘**，数据库在日志中看到代表事务成功提交的提交记录后，才会根据日志上的信息对真正的数据进行修改，修改完成后，再在日志中假日一条结束记录表示事务已完成持久化。

> **额外知识：Shadow Paging**
> 通过日志实现事务的原子性和持久性是当今的主流方案，但非唯一选择。除日志外，还有一种成为“[Shadow Paging](https://en.wikipedia.org/wiki/Shadow_paging)”的事务实现机制。
> Shadow Paging的大体思路是对数据的变动会写道硬盘的数据中，但并不是直接就地修改原先的数据，而是先将数据复制一份副本，保留原数据，修改副本数据。当事务成功提交，所有数据的修改都成功持久化之后，最后一步时区修改数据的引用指针，修改指针的操作将被认为是原子操作，现代磁盘的写操作可以认为在硬件上保证了不会出现“改了半个值”的现象。Shadow Paging实现事务要比Commit Logging更加简单，但涉及隔离性与并发锁时，Shadow Paging实现的事务并发能力就相对有限，因此在高性能的数据库中应用不多。

Commit Logging存在一个先天缺陷：所有对数据的真实修改都发生在事务提交之后，即使在此之前磁盘I/O有足够空闲，这对提升数据库的性能十分不利。对此**ARIES理论**提出了“**Write-Ahead Logging**”的日志改进方案，所谓提前写入，就是允许在事务提交之前，提前写入变动数据的意思。

Write-Ahead Logging先将何时写入变动数据，按照事务提交时点为界，划分为FORCE和STEAL两类情况。

- **FORCE**：当事务提交后，要求变动数据必须同时完成写入则称为FORCE，如果不强制变动数据必须同时完成写入则称为NO-FORCE。

  绝大部分数据库采用的都是NO-FORCE策略，因为只要有了日志，变动数据随时可以持久化，从优化磁盘I/O性能考虑，没有必要强制数据写入立即进行。
  
- **STEAL**：在事务提交前，允许变动数据提前写入则称为STEAL，不允许则称为NO-STEAL。

  从优化磁盘I/O性能考虑，允许数据提前写入，有利于利用空闲I/O资源，也有利于节省数据库缓存区的内存。
  
Commit Logging允许NO-FORCE，但不允许STEAL。因为假如事务提交前据有部分变动数据写入磁盘，那一旦事务要回滚，这些提前写入的数据就都成了错误。

Write-Ahead Logging允许NO-FORCE和STEAL。它给出的解决办法是增加了另一种被称为**Undo Log的**日志类型，当变动数据写入磁盘前，必须先记录Undo Log，注明修改了哪个位置的数据和改动前后的值，等等。需要回滚时根据Undo Log对提前写入的数据变动进行擦除。

Write-Ahead Logging在崩溃恢复时的三个阶段：

- **分析阶段**（Analysis）：该阶段从最后一次检查点开始扫描日志，找出所有没有**End Record**的事务，组成待恢复的事故我要集合，这个集合至少会包括Transaction Table和Dirty Page Table两个组成部分。
- **重做阶段**（Redo）：该阶段依据分析阶段产生的待恢复的事务集合来重演历史（Repeat History），具体操作为：找出所有包含Commit Record的日志，将这些日志修改的数据写入磁盘，写入完成后在日志中增加一条End Record，然后移除出待恢复事务集合。
- **回滚阶段**（Undo）：该阶段处理经过分析、重做阶段后剩余的恢复事务集合，此时剩下的都是需要回滚的事务，它们被称为Loser，根据Undo Log中的信息，将已经提前写入磁盘的信息重新改写回去，以达到回滚这些Loser事务的目的。

### 实现隔离性

隔离性保证了每个事务各自读、写的数据相互独立，不会彼此影响。隔离性与并发密切相关。

实现隔离性的方法是**加锁同步**。现代数据库提供的三种锁：

- **写锁**（Write Lock，也叫作排他锁，eXclusive Lock，简称X-Lock）：如果数据有加写锁，就只有持有写锁的事务才能对数据进行写入操作，事务持有写锁时，其他事务不能写入数据，也不能施加读锁。
- **读锁**（Read Lock，也叫做共享锁，Shared Lock，简称S-Lock）：多个事务可以同时对同一个数据添加多个读锁，数据被加上读锁后就不能再被加上写锁，所以其他事务不能对该数据进行写入，但依然可以读取。对于持有读锁的事务，如果该数据只有本事务加了读锁，允许直接将其升级为写锁，然后写入数据。
- **范围锁**（Range Lock）：对于某个范围直接加排他锁，在这个范围内的数据不能被写入。

  “范围不能被写入”与“一批数据不能被写入”是有区别的，即范围锁不等于一组排他锁的集合。加了范围锁后，不仅无法修改范围内已有的数据，也不能在该范围内增删任何数据，后者是一组排他锁的集合无法做到的。
  
隔离程度越高，并发访问时的吞吐量就越低。

**加锁实现的隔离等级：**

- **可串行化**（Serializable）
    
  最高级别的隔离性，对事务所有读、写的数据全都加上读锁、写锁和范围锁即可做到可串行化。

- **可重复读**（Repeatable Read）

  对事务所涉及的数据加读锁和写锁，且一致持有至事务结束，但不再加范围锁。比可串行化弱化的地方在于**幻读问题**（Phantom Reads），指事务在执行过程中，两个完全相同的范围查询得到了不同的结果集。
  
  具体的数据库实现并不一定会遵照理论去实现。如MySQL/InnoDB的默认隔离级别是**可重复读**，但它在只读事务中可以完全避免幻读问题，读写事务中，MySQL依旧会出现幻读问题。
  
- **已提交读**（Commited Read）

  对事务涉及的数据加的写锁会一直持续到事务结束，但加的读锁在查询操作完成后就马上释放。比**可重复读**弱化的地方在于**不可重复读问题**（Non-Repeatable Reads），指在事务执行过程中，对同一行数据的两次查询得到了不同的结果。
  
- **未提交读**（Uncommitted Read）

  对事务涉及的数据只加写锁，会一直持续到事务结束，但完全不加读锁。比**已提交读**弱化的地方在于**脏读问题**（Dirty Reads），指在事务执行过程中，一个事务读取到另一个事务未提交的数据。
  
**MVCC**

  “多版本并发控制”（Multi-Version Concurrency Control，MVCC）的基本思路是对数据库的任何修改都不会直接覆盖之前的数据，而是产生一个新版副本与旧版本共存，以此达到读取时完全不加锁的目的。
  
  - 插入数据时：CREATE_VERSION记录插入数据的事务ID，DELETE_VERSION为空。
  - 删除数据时：DELETE_VERSION记录删除数据的事务ID，CREATE_VERSION为空。
  - 修改数据时：原有数据的DELETE_VERSION记录修改数据的事务ID，CREATE_VERSION为空；新数据的CREATE_VERSION记录修改数据的事务ID，DELETE_VERSION为空。
 
在MVCC的实现下，将根据隔离级别来决定其他读事务应读取哪个版本的数据。

- **可重复读**：总是读取CREATE_VERSION小于或等于当前事务ID的记录，如果数据仍有多个版本，则取事务ID最大的。
- **已提交读**：总是读取CREATE_VERSION最大的的记录。

**可串行化**本来的语义就是要阻塞其他事务的读取操作，**未提交读**直接修改原始数据即可，无须版本字段，因此两者都没有必要用到MVCC。

## 全局事务（Global Transaction）

以下讨论的全局事务被限定未一种适用与单个服务使用多个数据源场景的事务解决方案（理论上真正的全局事务并没有“单个服务”的约束，它本来就是DTP模型中的概念），以下讨论的内容是一种在分布式环境中仍追求强一致性的事务处理方案，今天它几乎只实际应用于但服务多数据源的场合中。

###  XA

XA（eXtended Architecture）是一套为解决分布式事务的一致性问题的处理事务架构，核心内容时定义了全局的事务管理器（Transaction Manager）和局部的资源管理器（Resource Manager）之间的通信接口。
  
Java中专门定义了**JSR 907 Java Transaction API**（JTA），基于XA模式实现了全局事务处理标准。JTA最主要的两个接口是：

  - 事务管理器的接口`：javax.transaction.TransactionManager`，提供给容器事务使用的，还提供了另外一套`javax.transaction.UserTransaction`接口，用于通过程序代码手动开启、提交和回滚事务。
  - 满足XA规范的资源定义接口：`javax.transaction.XAResource`，任何资源（JDBC、JMS等等）如果想要支持JTA，只要实现XAResource接口中的方法即可。
  
XA将事务提交拆分为两阶段过程（**两段式提交**）：

- **准备阶段**：又叫投票阶段，在此阶段，协调者询问事务的所有参数者是否准备好提交，参与者如果已经准备好提交则回复Prepared，否则回复Non-Prepared。对于数据库来说，准备操作是在重做日志中记录全部事务提交操作所要做的内容，与**本地事务**中真正提交的区别只是暂不写入最后一条Commit Record而已。
- **提交阶段**：又叫作执行阶段，协调者如果在上一阶段收到所有事务参与者回复的Prepared消息，则先自己在本地持久化事务状态为Commit，在此操作完成后向所有参与者发送Commit指令，所有参与者立即执行提交操作；如果任意一个参与者回复了Non-Prepared消息或超时未回复，协调者将自己的是无状态持久化未Abort后向所有参与者发送Abort指令，参与者立即执行回滚操作。

两阶段提交的缺点：

- **单点问题**：若协调者宕机且一直没有恢复则参与者必须一直等待。
- **性能问题**：所有参与者被绑定为一个统一调度的整体，期间要有两次远程服务调用，三次数据持久化，性能通常很差。
- **一致性风险**：当网络稳定性和宕机恢复能力的假设不成立时，仍可能出现一致性问题。

**三段式提交**

三段式提交是在两段式提交的基础上发展而来，是为缓解协调者的单点问题和准备阶段的性能问题，但一致性风险有增无减，性能依旧很差。

## 共享事务（Shared Transaction）

多个服务共用一个数据源的事务场景。

## 分布式事务（Distributed Transaction）

多个服务同时访问多个数据源的事务场景。

### CAP理论

在分布式系统中，涉及共享数据问题时，以下三个特性最多只能同时满足其中两个：

- **一致性**（Consistency）：代表数据在任何时刻、任何分布式节点中所看到的都是符合预期的。
- **可用性**（Availability）：代表系统不间断地提供服务的能力。
- **分区容忍性**（Partition Tolerance）：代表分布式环境中部分节点因网络原因彼此失联后，即与其他节点形成网络分区时，系统仍能正确地提供服务的能力。

---

# 透明多级缓存（Transparent Multilevel Cache）

## 客户端缓存（Client Cache）

HTTP协议的无状态性决定了它必须依靠客户端缓存来解决网络传输效率上的缺陷。

**状态缓存**，指不经过服务器，客户端直接根据缓存信息对目标网站的状态判断。

**强制缓存**，假设在某个时间点到来以前，资源的内容和状态一定不会被改变，因此客户端无须警告任何请求，在该点前一直持有和使用该资源的本地缓存副本。

HTTP协议中设有两类Header实现强制缓存：

- **Expires**：HTTP协议中开始提供的Header，后面跟随一个截止时间参数，但它有受限于客户端的本地时间、无法处理涉及到用户身份的私有资源、无法描述“不缓存”的语义。
- **Cache-Control**：HTTP/1.1协议中定义的强制缓存Header，和Expires同时存在时以Cache-Control为准。

**协商缓存**，在一致性上会有比强制性缓存更好的表现，依靠一组成对出现的请求、响应Header来实现的：Last-Modified和If-Modified-Since、Etag和If-None-Match。

## 负载均衡（Load Balancing）

调度后方的多台机器，以统一的接口对外提供服务，承担此职责的技术组件被称为“负载均衡”。

本节“负载均衡”只聚焦于网络请求进入数据中心入口之后的其他级次的负载均衡。

### 数据链路层负载均衡

数据链路层负载均衡所做的工作，是修改请求的数据帧中的MAC目标地址，让用户原本是发送给负载均衡器的请求的数据帧，被二层交换机根据新的MAC目标地址转发到服务器集群中对应的服务器的网卡上，这样真实服务器就获得了一个原本目标不是它的数据帧。

使用这种负载均衡模式时，需将真实物理服务器集群所有及其的虚拟IP地址（Virtual IP Address，VIP）配置成与负载均衡器的虚拟IP一样。

数据链路层负载均衡具备三角传输的特性。

### 网络层负载均衡

网络层负载均衡通过修改IP分组数据包的Headers中的IP来实现数据包的转发。

具体有两种常见的修改方式：

- **IP隧道转发模式**：保持原有数据包不变，新创建一个数据包，把原有数据包的Headers和Payload整体作为新数据包的Payload，在新数据包的Headers中写入真实服务器的IP作为目标地址发送出去。真实服务器收到后进行拆包还原原有数据包。这种传输就是“IP隧道”（IP Tunnel）传输。
- **NAT模式**：修改目标地址为真实服务器IP，源地址为据衡器自己的IP，称作Source NAT（SNAT）。从真实服务器的视角来看，所有的流量都来自于负载均衡，一些根据目标IP进行控制的业务逻辑就无法进行。

IP隧道的网络层负载均衡转发模式具备三角传输的特性。

### 应用层负载均衡

前述四层负载均衡工作模式都属于“转发”，即直接将TCP报文的底层数据格式（IP数据包或以太网帧）转发到真实服务器上，客户端到真实服务器之间维持着同一条TCP通道。

四层之后的负载均衡无法再进行转发，只能进行代理，真实服务器、负载均衡器、客户端三者之间由两条独立的TCP通道来维持通信。

**代理分类**：

- 正向代理，在客户端设置的，代表客户端和服务器通信的代理服务，客户端可知，对服务器来说是透明的。
- 反向代理，设置在服务器侧，代表服务器与客户端通信的代理服务，对客户端来说时透明的。
- 透明代理，对客户端和服务器双方都透明的代理服务。

七层负载均衡属于反向代理的一种。

七层代理可以实现的功能：

- 所有CDN可以做的缓存方面的工作，如，静态资源资源缓存、协议升级、访问控制。
- 更智能化的路由。如，Session路由、URL路由、用户身份路由。
- 抵御某些安全攻击。
- 微服务架构系统中的链路治理措施都需要在七层负载均衡中进行。如，服务降级、熔断、异常注入。

### 均衡策略与实现

常见均衡策略：

- **轮循均衡**（Round Robin）：每一次请求轮流分配给内部服务器。适用于所有服务器都有相同的软硬件配置并且平均服务请求相对均衡的情况。
- **权重轮循均衡**（Weighted Round Robin）：依据服务器处理能力给每个服务器分配不同的权值，并分配相应权值数的请求。
- **随机均衡**（Random）：随机分配给请求内部的多个服务器。
- **权重随机均衡**（Weighted Random）：类似权重轮循均衡。
- **一致性哈希均衡**（Consistency Hash）：根据请求中某些数据（MAC、IP等）作为特征值来计算落在的节点上，算法一般会保证同一个特征值落在相同的服务器上。
- **响应速度均衡**（Response Time）：负载均衡设备对内部服务器发出一个探测请求，将请求分配给最快响应的服务器。
- **最少连接数均衡**（Least Connection）：将请求分配给连接数最少的服务器。

负载均衡器分为“软件均衡器”和“硬件均衡器”两类。软件均衡器又分为操作系统内核的均衡器，如Linux Virtual Server，以及应用程序均衡器，如Nginx、HAProxy、KeepAlived等。

## 服务端缓存

### 缓存属性

- **吞吐量**：使用OPS（Operation per Second）来衡量，反映了对缓存进行并发读写操作的效率。
- **命中率**：从缓存中返回结果次数与总请求次数的比值，反映了引入缓存的价值高低。
- **拓展功能**，除基本读写功能外的额外管理功能，如最大容量、失效时间、失效事件、命中率统计。
- **分布式支持**：进程内缓存还是分布式缓存，前者只为节点本身提供服务，无网络访问操作，速度快但数据不能在服务节点间共享，后者则相反。

#### 吞吐量

缓存的吞吐量在并发的场景中才有意义，不考虑并发的吞吐量是常量时间复杂度。

HashMap不是线程安全的容器，需要使用Collections.synchronizedMap进行包装，这相当于给Map的所有访问方法自动加全局锁；或者改用ConcurrrentHashMap来实现，这相当于给Map的访问分段枷锁。

无论采用怎样的实现方法，线程安全措施都会带来一定的吞吐量损失。

### 命中率与淘汰策略

基础的淘汰策略实现方案：

- **FIFO**（First In First Out）：优先淘汰最早进入缓存的数据。
- **LRU**（Least Recently Used）：优先淘汰最久未被访问的数据。
- **LFU**（Least Frequently Used）:优先淘汰最不经常使用的数据。

### 拓展功能

- **加载器**，许多缓存都有CacheLoader之类的设计，可以让缓存从只能被动存储外部放入的数据，变为主动通过加载器去加载指定Key值的数据。
- **淘汰策略**
- **失效策略**
- **事件通知**
- **并发级别**
- **容量控制**
- **统计信息**
- **持久化**

### 分布式缓存

- **复制式缓存**,缓存中所有数据在分布式集群的每个节点里面都存在有一份副本，从本地读取数据；当数据发生变化时，就必须遵循赋值协议，将变更同步到集群的每个节点中，变更数据的代价高昂。
- **集中式缓存**，目前分布式缓存的主流形式，读、写都需要网络访问，好处是不会随着节点数量的增加而产生额外的负担，代价是读、写都不能达到进程内缓存的高性能。

## 架构安全性

### 认证（Authentication）

#### 认证的标准

J2EE添加了一系列用于认证的API，主要包括：

- 标准方面，添加了四种内置的、不可扩展的认证方案，即Client-Cert、Basic、Digest和Form。涵盖了主流的三种层面的认证，通信信道（如SSL/TLS传输安全层）、协议（如HTTP协议）和内容（如Web内容）。
- 实现方面，添加了与认证和授权相关的一套程序接口，譬如HttpServletRequest::isUserInRole()、HttpServletRequest::getUserPrinciple()等方法。

#### HTTP认证

**认证方案**，指生成用户身份凭证的方法，这个概念最初源于HTTP协议的认证框架。

RFC 7235定义了了HTTP协议的通用认证框架，要求所有支持HTTP协议的服务器在未授权用户视图访问服务都安保护区域的资源时，应返回401 Unauthorized的状态码，同时应在响应报文头里附带以下两个分别代表网页认证和代理认证的Header之一，告知客户端应该采取何种方式产生能代表访问者身份的凭证信息：

- WWW-Authenticate：<认证方案> realm=<保护区域的描述信息>
- Proxy-Authenticate:<认证方案> realm=<保护区域的描述信息>

接收到该响应后，客户端必须遵循服务都安指定的认证方案，在请求资源的报文头中加入身份凭证信息，由服务端核实通过后才会允许该请求正常返回，否则将返回403 Forbidden错误。客户端的请求头报文中应包含以下Header项之一：

- Authorization:<认证方案><凭证内容>
- Proxy-Authorization:<认证方案><凭证内容>

这种认证方案把“要产生身份凭证”与“具体如何产生身份凭证”的实现分离开来，客户端通过生物信息、用户密码、数字证书等来生成凭证属于如何生成凭证的具体实现。

以HTTP Basic认证为例：

1. 请求资源 **GET /admin** 后，浏览器收到来自服务端的如下响应：

> HTTP/1.1 401 Unauthorized
> Date: Mon, 24 Feb 2020 16:50:53 GMT
> WWW-Authenticate: Basic realm="example from icyfenix.cn"

2. 浏览器弹出HTTP Basic认证对话框，向最终用户索取用户名和密码。

3. 用户在对话框中输入密码信息，譬如输入用户名**icyfenix**，密码**123456**，浏览器会将字符串**icyfenix:123456**编码为**aWN5ZmVuaXg6MTIzNDU2**，然后发送给服务端，HTTP 请求如下所示：

> GET /admin HTTP/1.1
> Authorization: Basic aWN5ZmVuaXg6MTIzNDU2

4.服务端接收请求，检查是否合法，是则返回**/admin**的资源，否则返回403 Forbidden错误。

IETF定义的常见认证方案：

- **Basic**，HTTP基础认证，使用Basic64编码明文发送用户名密码。
- **Digest**，HTTP摘要认证，Basic认证的改良版本，把用户名和密码加盐（一个被称为Nonce的变化之作为盐值）后再通过MD5/SHA等哈希算法去摘要发送出去。
- **Bearer**：基于OAuth 2规范来完成认证，OAuth 2是一个同时涉及认证和授权的协议。
- **HOBA**：一种基于自签名证书的认证方案。

HTTP认证框架的认证方案允许自行拓展，并不一定要求由RFC规范来定义，只要用户代理（User Agent，通常是浏览器）能够识别这种私有的认证方案即可。

#### Web认证

2019年3月，W3C组织批准了由FIDO领导起草的世界首份Web内容认证的标准“WebAuthn”。WebAuthn彻底抛弃了传统的密码登录方式，改为直接采用生物识别或者实体密钥来作为身份凭证。

WebAuthn规范涵盖了“注册”和“认证”两个流程，注册流程如下：

1. 用户进入系统的注册界面，这个页面的格式、内容和用户注册时需要填写的信息均不包含在WebAuthn规范内。
2. 当用户填写完信息，提交注册信息，服务端先暂存用户提交的数据，生成一个随机字符串（规范中称为Chanllenge）和用户的ID（规范中称作凭证ID），返回给客户端。
3. 客户端的WebAuthn API接收到Challenge和UserID，把这些信息发送给验证器（Authenticator）。
4. 验证器提示用户进行认证，验证的结果是生成一个密钥对（公钥和私钥），由验证器存储私钥、用户信息以及当前的域名。然后使用私钥对Challenge进行签名，并将签名结果、UserID和公钥一起返回客户端。
5. 浏览器将验证器返回的结果转发给服务器。
6. 服务器核验信息，检查UserID与之前发送的是否一致，并用公钥解密后得到的结果与之前发送的Challenge相比较，一致则表明注册通过，由服务端存储改UserID对应的公钥。

WebAuthn采用非对称加密的公钥、私钥替代传统的密码，这是非常理想的认证方案。

### 授权（Authorization）

授权通常伴随着认证、审计、账号一同出现，并称为AAAA（Authentication、Authorization、Audit、Account）。授权行为在程序中的应用非常广泛，给某个类或某个方法设置范围控制符在本质上也是一种授权行为。

授权的结果用于对程序功能或者资源的访问控制，成理论的权限控制模型有很多，如自主访问控制（Discretionary Access Control，DAC）、强制访问控制（Mandatory Access Control）、基于属性的访问控制（Attribute-Based Access Control），还有最为常用的基于角色的访问控制（Role-Based Access Control）。

#### RBAC

所有的访问控制模型，实质上都是在解决同一个问题：“**谁（User）**拥有什么**权限**（AUthority）去**操作**（Operation）哪些**资源**（Resource）”。

解决方案：

- 将权限绑定到用户对象
- 将权限绑定到角色对象

RBAC的主要元素的关系示意图：


```
flowchart LR
    User --> Role --> Permission --> Resource
    
```

某用户隶属于某角色拥有许可操作某资源。

RBAC允许不同角色之间定义关联与约束。

RBAC允许不通角色之间定义互斥关系。

Spring Security的设计里，用户和角色都可以拥有权限。

#### OAuth 2

OAuth 2是面向于解决第三方应用的认证授权协议。

OAuth 2致力于解决的问题：

- **密码泄露**
- **访问范围**
- **授权回收**

OAuth 2给出了多种解决办法，这些办法的共同特征是以令牌（Token）代替密码作为授权的凭证。

OAuth 2的几个关键术语：

- **资源拥有者**：拥有授权权限的人。
- **资源服务器**：能够提供第三方应所需资源的服务器。
- **第三方应用**：请求访问受保护资源的应用。
- **授权服务器**：能够根据资源拥有者医院提供授权的服务器。
- **操作代理**：用户用来访问服务器的工具，一般指浏览器，在一个应用作为另一个客户的情形中指HttpClient、RPCClient或者其他访问路径。

##### 授权码模式

##### 隐式授权模式

##### 密码模式

##### 客户端模式

### 凭证（Credentials）

#### Cookie-Session

RFC 6265定义的HTTP的状态管理机制，在HTTP协议中增加了Set-Cookie指令，该指令的含义是以键值对的形式向客户端发送一组信息，此信息将在此后一段时间内的每次HTTP请求中，以名为Cookie的Header附带着重新发回客户端，以便服务端区分来自不同客户端的请求。

系统通常会把状态信息保存在服务端，在Cookie里传输无字面意义、不重复的字符串，习惯上以sessionid或jsessionid为名。

Cookie-Session方案在安全性上有一定的先天优势：状态信息都存储于服务器。Cookie-Session的另一优点是服务端有主动的状态管理能力，可随时修改、清楚任意上下文信息。

Session-Cookie适合单节点的单体服务环境，在分布式多节点环境下，设计者必须在以下三种方案中选择一种：

- 牺牲集群的一致性，让均衡器采用亲和式的负载均衡算法，譬如根据用户IP或者Session来分配节点，如果节点崩溃则用户状态便完全丢失。
- 牺牲集群可用性，节点间采用复制式的Session，每一个节点中的Session变动都会发送到组播地址的其他服务器上。
- 牺牲集群的分区容忍性，普通服务节点不再保留状态，将上下文计中放在一个所有服务节点都能访问到的数据节点中进行存储。

#### JWT

Cookie-Session机制在分布式环境下会遇到CAP不可兼得的问题，而在多方系统中，就更不可能谈Session层面的数据共享了，因为即便服务端间能共享数据，客户端的Cookie也没法跨域。JWT方案将状态信息存储在客户端，每次随着请求发回到服务器去，同时确保信息不被中间人篡改。

JWT定义于[RFC 7519](https://datatracker.ietf.org/doc/html/rfc7519)标准之中，是目前广泛使用的一种令牌格式，尤其经常与OAuth2配合应用于分布式的、涉及多方的应用系统中。

最常见的使用方式是附在名为Authorization的Header发送给服务端，前缀在[RFC 6750](https://datatracker.ietf.org/doc/html/rfc6750)中被规定为Bearer。

**JWT的结构**：

JWT结构总体上可划分为三个部分，每个部分间用点号.分隔开。

第一部分是**令牌头**（Header），内容如下：

```
{
    "alg":"HS256",
    "typ":"JWT"
}
```

令牌的第二部分是**负载**（Payload），如下：

```
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}
```

[RFC 7519](https://datatracker.ietf.org/doc/html/rfc7519)中推荐了七项声明名称：

- iss（Issuer）：签发人
- exp（Expiration Time）：令牌过期时间
- sub（Subject）：主题
- aud（Audience）：令牌受众
- nbf（Not Before）：令牌生效时间
- iat（Issued At）：令牌签发时间
- jti（JWT ID）：令牌编号

令牌的第三部分是**签名**，如下：

```
HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload),secret)
```

签名是使用在对象头中公开的特定签名算法，通过特定的密钥对前面那两部分内容进行加密计算。
签名的作用在于确保负载的信息是可信的，没有被篡改的，也没有在传输过程中给丢失任何信息。
JWT默认签名算法采用HMAC SHA256的带密钥的哈希摘要算法，加密和验证过程均只能由中心化的授权服务提供，这种方式一般只适合于单体应用，在分布式应用中，通常会采用非对称的加密算法来进行签名。

缺点：

- **令牌难以主动失效**
- **相对更容易遭受重放攻击**
- **只能携带相当有效的数据**，HTTP协议没有强制约束Header的最大长度，但各种服务器、浏览器都会有自己的约束。
- **必须考虑令牌在客户端如何存储**
- **无状态也不总是好的**，如不利于统计在线用户。

### 保密（Confidentiality）

#### 保密的手段：

- 以摘要代替明文
- 先加盐值再做哈希是用对弱密码的常用方法
- 将盐值变为动态值能有效防止冒认
- 给服务加入动态令牌，在网关或其他流量公共位置建立校验逻辑，可以做到防止重放攻击
- 启用 HTTPS 可以防御链路上的恶意嗅探，也能在通信层面解决了重放攻击的问题
- 使用存储数字证书的物理设备、生物识别、双重验证、网络隔离

#### 客户端加密

客户端加密不是为了防御服务端被攻破而批量泄漏密码的风险，并不是为了增加传输过程的安全。

#### 密码存储与验证


# 分布式的基石

## 从类库到服务

> 微服务架构也会使用到类库，但构成软件系统组件的主要方式是将其拆分为一个一个服务。

### 服务发现

类库封装被大规模使用，令计算机实现了通过位于不同模块的方法调用来组装复用指令序列，是软件发展规模的一次飞跃。服务化的普及，令软件系统得以通过分布于网络中不同及其的互相协作来复用功能，是软件发展规模的第二次飞跃。

#### 服务发现的意义

所有的远程服务调用都是使用**全限定名**（Fully Qualified Domain Name）、**端口号**与**服务标识**所构成的三元组来确定一个远程服务的精确坐标的。全限定名达标了网络中某台主机的精确位置，端口代表了主机上某一个提供了TCP/UDP网络服务的程序，服务标识则代表了该程序所提供的某个具体的方法入口。前两者对于所有的远程服务来说都一致，后者则与具体的应用层协议有关，不同协议具有不同形式的标识。如REST远程服务标识是URL地址；RMI远程服务标识是Stub类中的方法；SOAP远程服务标识是WSDL中定义方法。

如何在基础设施和网络协议层面，对应用尽可能无感知、方便地实现服务发现是目前服务发现的一个主要发展方向。

#### 可用与可靠

服务发现的过程：

- **服务的注册**：当服务启动的时候应通过某些形式将自己的坐标信息通知到服务注册中心，这个过程可能有应用程序本身来完成，称为自注册模式，如Spring Cloud的@EnableEurekaClient注解；也可能有容器编排框架或第三方注册工具来完成，称为第三方注册模式，如Kubernetes和Registrator。
- **服务的维护**：尽管服务发现框架通常都有提供下线机制，但并没有什么办法保证每次服务都能优雅地下线而不是由于宕机、断网等原因突然失联。
- **服务的发现**：