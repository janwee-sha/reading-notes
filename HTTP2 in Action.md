# 第1章 万维网与HTTP

## 1.1 万维网的原理

因特网是使用IP（Internet Protocol，因特网协议）连接在一起实现消息传递的计算机构成的网络。

HTTP是万维网浏览器或网页浏览器请求网页的协议。万维网有三项核心技术，用于传输数据的HTTP，用于标识唯一资源的URL，用于构造网页的HTML。

浏览器请求网页时的典型交互过程：

1. 浏览器根据DNS（Domain Name System，域名系统）服务器返回的真实地址请求网页。

2. 浏览器请求计算机建立对这个IP地址的标准Web端口（80）或标准安全Web端口（443）的TCP连接。

*IP用于直接通过因特网传输数据，而TCP增加了稳定性和重传机制以确保连接可靠。*

3. 当浏览器连接到Web服务器之后会请求网站。
4. 服务器根据请求URL响应相关内容。
5. Web浏览器负责处理返回的响应。
6. 浏览器在屏幕上渲染页面。
7. 浏览器在有需要的情况下会在页面完全加载后，继续加载动态内容。

网络数据传输的分层：

- 应用层（超文本传输协议HTTP）
- 表现层（文件格式，如ASCII、UTF-8、JPG、PNG等）
- 会话层（安全套接层SSL/安全传输层协议TLS）
- 传输层（传输控制协议TCP）
- 网络层（因特网协议IP）
- 数据链路层（如以太网）
- 物理层（如网线、WIFI、移动设备等）

## 1.3 HTTP的语法和结构

### 1.3.1 HTTP/0.9

HTTP/0.9的仅有的语法如下：


```
GET /page.html[回车符]
```


HTTP/0.9不包含HTTP头字或任何其他媒体的概念。

### 1.3.2 HTTP/1.0

HTTP/1.0新增了一些关键特性：

- 更多的请求方法。相对HTTP/0.9，新增了`HEAD`和`POST`。
- 为所有消息添加HTTP版本号字段。可选字段，为了向后兼容，默认使用HTTP/0.9。
- HTTP首部。与请求和响应一起发送，以提供与正在执行的请求或发送的响应相关的更多信息。
- 一个三位整数的响应状态码，用来表示响应状态。

GET方法与HTTP/0.9中的基本相同，但新增的首部允许客户端发送条件GET，当之前发送过同样的请求后，只有在资源发生变化时，才请求资源内容；否则告诉客户端继续使用旧的副本。此外，可以使用GET方法获取更多的资源，而不仅仅是超文本文档。

HEAD方法允许客户端获取资源的所有元信息（例如HTTP头）而无须下载资源本身。

POST方法允许客户端发送数据到Web服务器。

GET方法允许将数据包含到URL尾部指定的查询参数中发送，通常放在？字符之后。查询参数在最早的URI规范中定义，它们的作用是提供额外的参数来指明URI，而不是用他们向Web服务器上传数据。

HTTP/1.0语法如下：

```
GET /page.html HTTP/1.0[回车符]
Header1:Value1[回车符]
Header2:Value2[回车符]
[回车符]
```

HTTP/1.0中定义了一些标准首部：

- Accept：接受的响应格式（HTML、XHTML和XML等）
- Accept-Encoding：接受的编码格式（gzip、deflate和brotli等）
- Accept-Language：倾向使用的语言（en-GB英式英语、en-US美式英语，或者其他形式的英语）
- User-Agent：正在使用的浏览器
- Connection：值可以是keep-alive，告知服务器保持连接
- Host：请求的主机地址

HTTP响应状态码

一个经典的来自HTTP/1.0服务器的响应如下：

```
HTTP/1.0 200 OK
Date: Fri, 20 May 2022 13:14:00 GMT
Content-Type: text/html
Server: Apache

<!doctype html>
...
```

响应的第一行包含HTTP版本、三位数的HTTP状态码以及该状态码的文本描述。

HTTP/1.0的响应状态码：


| 分类 | 值 | 描述 | 详情 |
| --- | --- | --- | --- |
|1xx（信息） |N/A |N/A|HTTP/1.0未定义任何1xx的状态码，但定义了此分类  |
|2xx（成功）  |200<br>201<br>202<br>204 |OK<br>Created<br>Accepted<br>No content|成功请求的标准响应码<br>一般是POST请求的响应码<br>请求正在处理中，还未处理完成<br>已接受请求，正在处理中，但响应没有消息体  |
|3xx（重定向）  |300<br><br>301<br>302<br>304  |Mutiple Choice<br><br>Moved permanently<br>Moved temporarily<br>Not modified  |此状态码不直接使用，说明3xx分类资源在一个或多个地方<br>可用，且此响应提供了它所在的更多信息<br>Location响应首部提供了资源的新地址<br>Location响应首部提供了资源的新地址<br>表示条件响应，不需要发送正文  |
|4xx（客户端错误）  |400<br>401<br>403<br>404  |Bad request<br>Unauthorized<br>Forbidden<br>Not found  |服务端无法理解请求<br>未授权访问<br>表示已通过授权流程，但是没有访问此资源的权限<br>资源不存在  |
|5xx（服务端错误）  |500<br>501<br>502<br>503  |Internal server error<br>Not implemented<br>Bad gateway<br>Service unavailable  |服务端错误，请求无法完成<br>服务端不识别请求（比如未实现的HTTP方法）<br>当服务器作为网关或代理时，收到了上游服务的错误<br>由于负载过高或维护，服务端无法完成请求  |


### 1.3.3 HTTP/1.1

强制添加HTTP首部和持久连接是HTTP/1.1语法的连个显著变化。

#### 强制添加HTTP首部

请求行不再是一个包含绝对路径的URL，而是一个只包含相对路径的URL。我们在请求首部增加Host来实现该功能。

Host首部在HTTP/1.0是可选的，但是在HTTP/1.1中它是必选项。

```
GET /books/1 HTTP/1.1
Host: localhost:7200
```



#### 持久连接

通过指定Connnection首部值为keep-alive，客户端可以要求服务器保持连接打开，以支持发送更多的请求。

HTTP/1.1不仅将持久连接添加到文档中，还将其作为默认行为。如果服务器确实想要关闭连接，必须在响应中显式包含`Connection:close`HTT首部。

#### 管道

HTTP/1.1还增加了管道的概念，因此应该可以通过同一个持久连接发送多个请求并按顺序获取响应。但是由于某些原因，管道化并没有流行起来，客户端和服务器对管道化的支持都很差。

#### 其他新功能

- 除了GET、POST、HEAD外，HTTP/1.1增加了PUT、OPTION、CONNECT、TRACE以及DELETE方法。
- 更好的缓存方法。这些方法允许服务器指示客户端将资源（如css文件）存储在浏览器的缓存中，以便在以后需要时重复使用。在HTTP/1.1中引入的`Cache-Control`首部比HTTP/1.0中的`Expires`首部的选项更多。

\-  HTTP cookies，允许HTTP维护状态。 
\- 引入字符集，在HTTP响应中新增语言选项。
\- 支持代理。
\- 支持权限认证。
\-  新的状态码。
\- 尾随首部。

## 1.4 HTTPS简介

HTTP最初是一个纯文本协议。HTTP消息以未加密的方式通过互联网发送，因此在消息被路由到目的地的过程中，任何以法国都可以读取到消息。

HTTPS是HTTP的安全版本，使用TLS（Transport Layer Security，传输层加密，Netscape发明的，不是正式的互联网标准）协议对传输中的消息进行加密，TLS的前身是SSL（Secure Sockets Layer，安全套接字层，SSL标准化后的协议）。

HTTPS对HTTP消息添加了三个重要概念：

- 加密——传输过程中第三方无法读取消息。
- 完整性校验——消息在传输过程中未被更改，因为整个加密消息已经过数字加密，并且改签名在解密之前已通过加密验证。
- 身份验证——服务器不是伪装的。

HTTPS使用数字证书加密信息。数字整数由浏览器信任的各种CA（Certificate Authorities，证书颁发机构）发布并进行数字签名。

HTTPS是基于HTTP构建的，几乎可以与HTTP协议无缝衔接。默认在不同的端口上服务（443端口）。

对于使用HTTPS的Web服务器，你需要一个能够支持HTTPS加密和解密的客户端。

# 第2章 通向HTTP/2之路

## 2.2 解决HTTP/1.1性能问题的方案

HTTP/1.1不是一个高效的协议，因为它为等待响应会阻塞发送。

### 2.2.1 使用多个HTTP连接。

大多数浏览器可以为每个域名打开6个连接。甚至许多网站从自愈提供静态资源，从而可以为每个新域名打开另外6个连接。这样可以提高并发数，域名发散还有其他优势，比如减小HTTP请求首部。

使用多个HTTP连接也有缺点。客户端和服务器都有额外的开销：打开TCP连接需要时间，维护连接需要更多的内存和CPU资源。

多个TCP连接还受到TCP慢启动的限制。

另外，使用多个独立的连接也可能会导致带宽问题。如所有带宽都用掉了，就会导致TCP超时，和其他连接上的重传。

### 2.2.2 发送更少的请求

减少不必要的请求、以更少的HTTP请求获取同样的资源。

## 2.3 HTTP/1.1的其他问题

尽管HTTP消息体可以包含二进制数据，但请求和首部需要是文本的格式。文本格式对人类很友好，但对于机器并不友好。

HTTP/1.1还存在纯文本协议的安全和隐私问题，以及缺少状态的问题。

## 2.5 从HTTP/1.1到HTTP/2

### 2.5.1 SPDY

SPDY是2009年由Google开发的新的二进制协议，基于HTTP构建，没有从根本上改变协议。它引入了一些关键的概念来解决HTTP/1.1的问题：

- 流多路利用。
- 请求优先级。
- HTTP首部压缩。

### 2.5.2 HTTP/2

HTTP/2基于SPDY发布。

