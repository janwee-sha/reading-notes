> The following is a reference to [Marko Luksa's Kubernetes in Action](http://www.broadview.com.cn/book/5227)

# 1. Introducing Kubernetes

## 1.1 The Need for Kubernetes

### 1.1.3 Continuous delivery: DevOps and NoOps

**Devops**: The pratice which makes the developer, QA, and operations teams collaborate throughout the whole process.

## 1.2 Container technologies

### 1.2.1 What containers are

**容器实现隔离机制**

运行在同一操作系统上的容器之间的隔离实现机制有两个：

- **Linux命名空间**。它使每个进程只看到它自己的系统视图（文件、进程、网络接口、主机名等）。
- **Linux控制组（cgroups）**。它限制了进程能使用的资源量（CPU、内存、网络带宽等）。

## 1.3 Introducing Kunernetes

### 1.3.3 Understanding the architecture of a Kubernetes cluster

在硬件级别，一个Kubernetes集群由很多节点组成，这些人被分成两种类型：

- 主节点，它承载着Kubernetes控制和管理整个集群系统的控制面板。
- 工作节点，它们运行用户实际部署的应用。

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/Kubernetes.in.Action.Figure.1.9.png)

**控制面板**

控制面板用于控制集群并使它工作。它包含多个组件，组件可以运行在单个主节点上或者通过副本分别部署在多个主节点以确保高可用性。组件包括：

- **Kubernetes API服务器**，你和其他控制面板组件都要和它通信。
- **Scheduler**，它调度你的应用（为应用的每个可部署组件分配一个工作节点）。
- **Control Manager**，它执行集群级别的功能，如复制组件、持续跟踪工作节点、处理节点失败等。
- **etcd**，一个可靠的分布式数据存储，它能持久化存储集群配置。

**工作节点**

工作节点是运行容器化应用的机器。运行、监控和管理应用服务的任务是由以下组件完成的：

- Docker、rkt或其他的容器类型
- Kubelet，它与API服务器通信，并管理它所在节点的容器
- Kubernetes Service Proxy（kube-proxy），它负责组件之间的负载均衡网络流量

### 1.3.4 Running an application in Kubernetes

在Kubernetes中运行应用的步骤：

1. 将应用打包为一个或多个容器镜像；
2. 再将那些镜像推送到镜像仓库；
3. 然后将应用的描述发布到Kubernetes API服务器。

应用的描述包括注入容器镜像或者包含应用程序组件的容器镜像、这些镜像如何相互关联，以及哪些组件需要同时运行在同一个节点上和哪些组件不需要同时运行等信息。此外，该描述还包括哪些组件为内部或外部客户提供服务且应该通过单个IP地址暴露，并使其他组件可以发现。

**描述信息怎样成为一个运行的容器**

当API服务器处理应用的描述时，调度器（Scheduler）调度指定组的容器到可用的工作节点上，调度是基于每组所需的计算资源，以及调度时每个节点未分配的资源。然后，那些节点上的Kubelet指示容器运行时拉去所需的镜像并运行容器。

**保持容器运行**

一旦应用程序运行起来，Kubernetes就会不断地确认应用程序的部署状态始终与你提供的描述相匹配。

若整个工作节点死亡或无法访问，Kubernetes将为故障节点上运行的所有容器选择新节点，并在新选择的节点上运行它们。

**扩展副本数量**

当应用程序运行时，可以决定要增加或减少副本量，而Kubernetes将分别增加附加的或停止多余的副本。甚至可以把决定副本数量的工作交给Kubernetes，它可以根据实时指标（CPU负载、内存消耗、每秒查询等）自动调整副本数。

**命中移动目标**

为了让客户能够轻松地找到提供特定服务的容器，可以告诉Kubernetes哪些容器提供相同的服务，而Kubernetes将通过一个静态IP地址暴露所有容器，并将该地址暴露给集群中运行的所有应用程序。这是通过环境变量完成的，但是客户端也可以通过良好的DNS查找服务IP。kube-proxy将确保到服务的连接是可跨容器实现负载均衡。服务的IP地址保持不变，因此客户端始终可以连接到它的容器，即使它们在集群中移动。

### 1.3.5 The benefits of using Kubernetes

- **简化应用程序部署**
- **更好地利用硬件**
- **健康检查和自修复**
- **自动扩容**

# Chapter 2. First step with Kubernetes and Docker

## 2.1 Creating, running, and sharing a container image

### 2.1.1 Installing Docker and running a Hello World container

**运行Hello World容器**

使用Docker运行一个容器：

```
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

### 2.1.2 创建一个简单的Node.js应用

构建一个简单的Node.js Web应用，代码如下：

```
const http = require('http');
const os = require('os');

console.log("Kubia server starting...");

var handler = function (request, response) {
	console.log("Receiving request from" + request.connection.remoteAddress);
	response.writeHead(200);
	response.end("You've hit " + os.hostname() + "\n");
};

var www = http.createServer(handler);
www.listen(7400);
```

### 2.1.3 为镜像创建Dockerfile

构建应用容器镜像的Dockerfile：

```
FROM node
ADD app.js /app.js
ENTRYPOINT ["node", "app.js"]
```

From行定义了镜像的起始内容（构建所基于的基础镜像）。这个例子中使用的是node镜像的tag 7版本。第二行把app.js文件从本地文件夹添加到镜像的根目录，保持app.js这个文件名。最后一行定义了当镜像被运行时需要被执行的命令，这个例子中，命令是`node app.js`。

### 2.1.4 构建容器镜像

现在有了Dockerfile和app.js文件，这是用来构建镜像的所有文件。运行下面的Docker命令来构建镜像：

```
docker build -t kubia .
```

镜像构建的过程如下图所示：

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/Kubernetes.in.Action.Figure.2.2.png)

**镜像是如何构建的**

构建过程不是由Docker客户端进行的，而是将整个目录的文件上传到Docker守护进程并在那里进行的。Docker客户端和守护进程不要求在同一台机器上。若你在一台非Linux操作系统中使用Docker，客户端就运行在你的宿主操作系统上，但是守护进程运行在一个虚拟机内。

**镜像分层**

镜像不是一个大的二进制块，而是由多层组成的，不同镜像可能会共享分层，这会让存储和传输变得更加高效。

构建镜像时，Dockerfile中的每一条单独的指令都会创建一个新层。

**使用Dockerfile构建镜像对比手动构建镜像**

除了使用Dockerfile构建容器镜像，也可以通过运行已有镜像容器来手动构建镜像，在容器中运行命令，退出容器，然后把最终的状态作为新镜像。区别是前者是自动化且可重复的。

### 2.1.5 运行容器镜像

以下的命令可以用来运行镜像：

```
docker run --name kubia -p 7400:7400 -d kubia
```

> `-d` 标志：将容器和命令行分离，即在后台运行命令。

**访问应用**

访问你的应用：

```
curl localhost:7400
```

### 2.1.6 探索运行容器的内部

**在已有的容器内部运行shell**

镜像基于的Node.js镜像包含了bash shell，所以可以像这样在容器内运行shell：

```
docker exec -it kubia bash
```

> `-i` 标志，开启标准输入流。
> `-t` 标志，分配一个伪终端（TTY）。

**容器内的进程运行在主机操作系统上**

运行在容器中的进程是运行在主机操作系统上的。容器使用独立的PID Linux命名空间并且有着独立的序列号，完全独立于进程树。

**容器的文件系统也是独立的**

正如拥有独立的进程树一样每个容器也拥有独立的文件系统。在容器内列出根目录的内容，指挥展示容器内的文件，包括镜像内的所有文件，再加上容器运行时创建的任何文件。

### 2.1.7 停止和删除容器

停止容器：

```
docker stop [OPTIONS] CONTAINER [CONTAINER...]
```

删除容器：

```
docker rm [OPTIONS] CONTAINER [CONTAINER...]
```

### 2.1.8 向镜像仓库推送镜像

**使用附加标签标注镜像**

在推送之前，需要重新根据Docker Hub的规则标注镜像。Docker Hub允许像以你的Docker Hub ID开头的镜像仓库推送镜像。可以在http://hub.docker.com上注册Docker Hub ID。

获得自己的ID后，就可以重命名镜像（用自己的Docker Hub ID替换 janwee）：

```
docker tag kubia janwee/kubia
```

这不会重命名标签，而是给同一个镜像创建一个额外的标签。如下所示：

```
$ docker images | head
REPOSITORY     TAG       IMAGE ID       CREATED             SIZE
janwee/kubia   latest    2e7625696c2a   About an hour ago   998MB
kubia          latest    2e7625696c2a   About an hour ago   998MB
```

可以看到，kubia 和 janwee/kubia 指向同一个镜像ID，所以实际上是同一个镜像的两个标签。

**向Docker Hub推送镜像**

在向Docker Hub推送镜像之前，现需要使用 `docker login` 命令和自己的用户ID登录。然后执行如下命令推送：

```
docker push janwee/kubia
```

## 2.2 Setting up a Kubernetes cluster

After having your app packaged inside a container image and made available through Docker Hub,you can deploy it in a Kubernetes cluster instead of running it in Docker directly.

A long list of methods exists for installing a Kubernetes cluster. These methods are described in the documentation at [https://kubernetes.io](https://kubernetes.io/). In this chapter, we'll cover two simple options for getting your hands on running Kubernetes cluster.

安装Kubernetes集群的方法有许多。Kubernetes可以在本地的开发机器、自己组织的机器或是虚拟机提供商（Google Computer Engine、Amazon EC2、Microsoft Azure等）上运行，或者使用托管的Kubernetes集群，如Google Kubernetes Engine。

### 2.2.1 用Minikube运行一个本地单节点Kubernetes集群

使用Minikube试运行Kubernetes集群最简单、最快捷的途径。Minikube是一个构建单节点集群的工具，对于测试Kubernetes和本地开发应用都非常有用。

//TODO to read

### 2.2.2 使用Google Kubernetes Engine托管Kubernetes集群

如果你想探索一个完善的多节点Kubernetesiqun，可以使用托管的Google Kubernetes Engine（GKE）集群。这样，无须手动设置所有的集群节点和网络。

#### 通过列出集群节点查看集群是否在运行

```
kubectl get nodes
```

`kubectl get`命令可以列出各种Kubernetes对象。经常会使用到它，但它通常只会显示对象最基本的信息。

#### 查看对象的更多信息

要查看关于对象的更详细的信息，可以使用`kubectl describe`命令，它显示更多信息。


## 2.3 Running your first app on Kubernetes

Usually, you'd prepare a JSON or YAML manifest, containing a description of all the components you want to deploy. But we'll use a simple one-line command to get something running.

### 2.3.1 Deploying your app

部署应用最简单的方式是使用kubectl run命令，该命令可以创建所有必要的组件而无需JSON或YAML文件。这样的话，我们不需要深入了解每个组件对象的结构。试着运行已经创建、推送到Docker Hub的镜像。下面是再Kubernetes中运行的代码：

```
kubectl run eureka --images=janwee/eureka-server --port=7100 --generator=run/v1
```

`-- images=janwee/eureka-server`显示的是制定要运行的容器镜像，`--port=7100`选项告诉Kubernetes应用正在监听7100端口。最后一个标志`--generator`让Kubernets创建一个ReplicationController，而不是Deployment。

#### 介绍pod

Kubernets并不直接处理单个容器，相反，它使用多个共存容器的理念。这组容器就叫做pod。

一个pod是一组紧密相关的容器，它们总是一起运行在同一个工作节点上，以及同一个Linux命名空间中。每个pod就像一个独立的逻辑机器，拥有自己的IP、主机名、进程等，运行一个独立的应用程序。应用程序可以是单个进程，运行在单个容器中，也可以是一个主应用进程或者其他支持进程，每个进程都在自己的容器中运行。一个pod的所有容器都运行在同一个逻辑机器上，而其他pod中的容器，即使运行在同一个工作节点上，也会出现在不同的节点上。

每个pod都有自己的IP、并包含一个或多个容器，每个容器都运行一个应用程序。pod分布在不同的工作节点上。

**图：容器、pod及物理工作节点之间的关系**

//TODO 插入图片

#### 列出pod

不能列出单个容器，因为它们不是独立的Kubernetes对象，但是可以列出pod。如下所示：

```
$ kubectl get pods
  NAME           READY  STATUS  RESTARTS AGE
  kubia-4jfyf   0/1     Pending 0           1m
```

要查看有关pod的更多信息，还可以使用`kubectl describe pod`命令，就像之前查看工作节点一样。

#### 幕后发生的事情

当运行`kubectl`命令时，它通过向Kubernetes API服务器发送一个REST HTTP请求，在集群中创建一个新的ReplicationController对象。然后，ReplicationController创建一个新的pod，调度器将其调度到一个工作节点上。Kubelet看到pod被调度到节点上，就告知Docker从镜像中心拉去指定的镜像，因为本地没有该镜像。下载镜像后，Docker创建并运行容器。

展示另外两个节点是为了显示上下文。它们没有在这个过程中扮演任何角色，因为pod没有调度到他们上面。

**定义 术语调度（scheduling）的意思是将pod分配给一个节点。pod会立即运行，而不是将要运行。**

**图 在Kubernetes中运行luksa/kubia容器**

// TODO 插入图片

### 2.3.2 访问Web应用

如何访问正在运行的pod？我们提到过每个pod都有自己的IP地址，但是这个地址是集群内部的，不能从集群外部访问。要让pod能够从外部访问，需要通过服务对象公开它，要创建一个特殊的LoadBalancer类型的服务。因为如果你创建一个常规服务（一个ClusterIP服务），比如pod，它也只能从集群内部访问。通过创建LoadBalancer类型的服务，将创建一个外部的负载均衡，可以通过负载均衡的公共IP访问pod。

**创建一个服务对象**

要创建服务，需要告知Kubernetes对外暴露之前创建的ReplicationController：

```
$ kubectl expose rc kubia --type=LoadBalancer --name kubia-http
```

> 注意 这里用的是replicationcontroller的缩写rc。大多数资源类型都有这样的缩写，所以不必输入全ing（例如，pods的缩写是po，service的缩写是svc，等等）。

#### 列出服务

expose命令的输出中提到一个名为kubia-http的服务。服务是类似于pod和node的对象，因此可以通过运行kubectl get services命令查看新创建的服务对象，如下面的代码所示：

```
$ kubectl get svc
```

### 2.3.3 系统的逻辑部分

**ReplicationController、pod和服务是如何组合在一起的**

正如前面解释过的，没有直接创建和使用容器。相反，Kubernetes的基本构件是pod。但是，你并没有真正地创建出任何pod，至少不是直接创建。通过运行kubectl run命令，

