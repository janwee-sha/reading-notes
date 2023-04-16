> The following is a reference to [Marko Luksa's Kubernetes in Action](http://www.broadview.com.cn/book/5227) and <https://kubernetes.io/>

# 1. Introducing Kubernetes

## 1.1 The Need for Kubernetes

### 1.1.3 Continuous delivery: DevOps and NoOps

**Devops**: The pratice which makes the developer, QA, and operations teams collaborate throughout the whole process.

## 1.2 Container technologies

### 1.2.1 What containers are

**容器实现隔离机制**

运行在同一操作系统上的容器之间的隔离实现机制有两个：

*   **Linux命名空间**。它使每个进程只看到它自己的系统视图（文件、进程、网络接口、主机名等）。
*   **Linux控制组（cgroups）**。它限制了进程能使用的资源量（CPU、内存、网络带宽等）。

## 1.3 Introducing Kunernetes

### 1.3.3 Understanding the architecture of a Kubernetes cluster

在硬件级别，一个Kubernetes集群由很多节点组成，分成两种类型：

*   主节点，它承载着Kubernetes控制和管理整个集群系统的控制面板。
*   工作节点，它们运行用户实际部署的应用。

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/images/Kubernetes.in.Action.Figure.1.9.png)

**控制面板**

控制面板用于控制集群并使它工作。它包含多个组件，组件可以运行在单个主节点上或者通过副本分别部署在多个主节点以确保高可用性。组件包括：

*   **Kubernetes API服务器**，你和其他控制面板组件都要和它通信。
*   **Scheduler**，它调度你的应用（为应用的每个可部署组件分配一个工作节点）。
*   **Control Manager**，它执行集群级别的功能，如复制组件、持续跟踪工作节点、处理节点失败等。
*   **etcd**，一个可靠的分布式数据存储，它能持久化存储集群配置。

**工作节点**

工作节点是运行容器化应用的机器。运行、监控和管理应用服务的任务是由以下组件完成的：

*   Docker、rkt或其他的容器类型
*   Kubelet，它与API服务器通信，并管理它所在节点的容器
*   Kubernetes Service Proxy（kube-proxy），它负责组件之间的负载均衡网络流量

### 1.3.4 Running an application in Kubernetes

在Kubernetes中运行应用的步骤：

1.  将应用打包为一个或多个容器镜像；
2.  再将那些镜像推送到镜像仓库；
3.  然后将应用的描述发布到Kubernetes API服务器。

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

*   **简化应用程序部署**
*   **更好地利用硬件**
*   **健康检查和自修复**
*   **自动扩容**

# Chapter 2. First step with Kubernetes and Docker

## 2.2 Setting up a Kubernetes cluster

一个适当的Kubernetes安装需要包含多个物理或虚拟机，并需要正确地设置网络，以便在Kubernetes集群内运行的所有容器都可以在相同的扁平网络环境内相互连通。

A long list of methods exists for installing a Kubernetes cluster. These methods are described in the documentation at [https://kubernetes.io](https://kubernetes.io/). In this chapter, we'll cover two simple options for getting your hands on running Kubernetes cluster.

安装Kubernetes集群的方法有许多。这些方法在 <https://kubernetes.io> 的文档中有详细的描述。 Kubernetes可以在本地的开发机器、自己组织的机器或是虚拟机提供商（Google Computer Engine、Amazon EC2、Microsoft Azure等）上运行，或者使用托管的Kubernetes集群，如Google Kubernetes Engine。

### 2.2.1 Running a local single-node Kubernetes cluster with Minikube

使用Minikube试运行Kubernetes集群最简单、最快捷的途径。Minikube是一个构建单节点集群的工具，对于测试Kubernetes和本地开发应用都非常有用。

**安装Minikube**

Minikube是一个需要下载并放到路径中的二进制文件。它适用于 OSX、Linux和Windows系统。可以参照 [Github上的Minikube代码仓库](https://github.com/kubernetes/minikube) 文档中的说明来安装它。

**使用Minikube启动一个Kubernetes集群**

使用下面的命令启动Kubernetes集群：

    minikube start

## 安装Kubernetes客户端（kubectl）

执行 `minikube start` 命令会默认配置使用minikube内的`kubectl` 来访问Kubernetes集群的控制面板。

但是如果 `kubectl` 不是安装在本地的情况下，minikube中包含的kubectl需要像下面这样使用：

    minikube kubectl -- <kubectl commands>

要在本地安装kubectl CLI客户端，需要下载它并放在路径中。kubectl命令行工具的安装和使用可参考[Kubernetes官网中关于kubectl的文档](https://kubernetes.io/zh-cn/docs/reference/kubectl/)。

**使用kubectl查看集群是否正常工作**

    kubectl cluster-info

**列出集群节点**

可以使用kubectl命令列出集群中的所有节点：

    kubectl get nodes

**查看对象的更多信息**

要查看关于对象的更详细的信息，可以使用 `kubectl describe` 命令，如：

    kubectl describe node <node name> 

## 2.3 Running your first app on Kubernetes

### 2.3.1 Deploying your app

参考 <https://kubernetes.io/zh-cn/docs/concepts/workloads/controllers/replicationcontroller/> 创建**ReplicationController**类型的pod。

**ReplicationController**确保在任何时候都有特定数量的Pod副本处于运行状态

**介绍pod**

Kubernets并不直接处理单个容器，相反，它使用多个共存容器的理念。这组容器就叫做pod。

一个pod是一组紧密相关的容器，它们总是一起运行在同一个工作节点上，以及同一个Linux命名空间中。每个pod就像一个独立的逻辑机器，拥有自己的IP、主机名、进程等，运行一个独立的应用程序。应用程序可以是单个进程，运行在单个容器中，也可以是一个主应用进程或者其他支持进程，每个进程都在自己的容器中运行。一个pod的所有容器都运行在同一个逻辑机器上，而其他pod中的容器，即使运行在同一个工作节点上，也会出现在不同的节点上。

每个pod都有自己的IP、并包含一个或多个容器，每个容器都运行一个应用程序。pod分布在不同的工作节点上。

**图：容器、pod及物理工作节点之间的关系**

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/images/Kubernetes.in.Action.Figure.2.5.png)

#### 列出pod

不能列出单个容器，因为它们不是独立的Kubernetes对象，但是可以列出pod。如下所示：

    $ kubectl get pods
      NAME           READY  STATUS  RESTARTS AGE
      kubia-4jfyf   0/1     Pending 0           1m

要查看有关pod的更多信息，还可以使用`kubectl describe pod`命令，就像之前查看工作节点一样。

#### 幕后发生的事情

当运行 `kubectl` 命令时，它通过向Kubernetes API服务器发送一个REST HTTP请求，在集群中创建一个新的ReplicationController对象。然后，ReplicationController创建一个新的pod，调度器将其调度到一个工作节点上。Kubelet看到pod被调度到节点上，就告知Docker从镜像中心拉去指定的镜像。下载镜像后，Docker创建并运行容器。

展示另外两个节点是为了显示上下文。它们没有在这个过程中扮演任何角色，因为pod没有调度到他们上面。

**定义 术语调度（scheduling）的意思是将pod分配给一个节点。pod会立即运行，而不是将要运行。**

**图 在Kubernetes中运行luksa/kubia容器**

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/images/Kubernetes.in.Action.Figure.2.6.png)

### 2.3.2 Accessing your web application

每个pod都有自己的IP地址，但是这个地址是集群内部的，不能从集群外部访问。要让pod能够从外部访问，需要通过服务对象公开它，要创建一个特殊的**LoadBalancer**类型的服务。因为如果你创建一个常规服务（一个ClusterIP服务），比如pod，它也只能从集群内部访问。通过创建LoadBalancer类型的服务，将创建一个外部的负载均衡，可以通过负载均衡的公共IP访问pod。

**创建一个服务对象**

要创建服务，需要告知Kubernetes对外暴露之前创建的ReplicationController：

    $ kubectl expose rc kubia --type=LoadBalancer --name kubia-http

> rc，replicationcontroller的缩写。大多数资源类型都有这样的缩写（例如，pods的缩写是po，service的缩写是svc，等等）。

**列出服务**

expose命令的输出中提到一个名为kubia-http的服务。服务是类似于pod和node的对象，因此可以通过运行kubectl get services命令查看新创建的服务对象，如下面的代码所示：

    $ kubectl get svc
    NAME         TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
    kubernetes   ClusterIP      10.96.0.1        <none>        443/TCP          18h
    kubia-http   LoadBalancer   10.105.159.126   <pending>     7400:32513/TCP   2m42s

`CLUSTER-IP` 栏值为 `<pending>` 表示该服务还没有外部IP地址，因为Kubernetes运行的云基础设施创建负载均衡需要一段实践。负载均衡启动后，就会显示服务的外部IP地址。

> Minikube不支持LoadBalancer类型的服务，因此服务不会有外部IP。但可以通过外部端口访问服务。

**使用外部IP访问服务**

使用Minikube的时候，可以运行 `minikube service <service name>` 获取服务的IP和端口。

应用会将pod名称作为它的主机名。

### 2.3.3 The logical part of your system

**ReplicationController、pod和服务是如何组合在一起的**

正如前面解释过的，没有直接创建和使用容器。相反，Kubernetes的基本构件是pod。但是，你并没有真正地创建出任何pod，至少不是直接创建。通过运行 `kubectl run` 命令，创建了一个ReplicationController，它用于创建pod实例。为了使该pod能够从集群外部访问，需要让Kubernetes将该ReplicationController管理的所有pod由一个服务对外暴露。

**pod和它的容器**

Kubernetes系统中最重要的组件是pod。一个pod可以包含任意数量的容器。

**为什么需要服务**

pod的存在是短暂的，一个pod可能在任何时候消失，或许因为它所在节点发生故障，或者被人为删除，或者因为pod被从一个健康的节点剔除了。当其中任何一种情况发生时，消失的pod将被ReplicationController替换为新的pod，此时pod的IP地址也会发生改变。这就是需要服务的地方——解决不断变化的pod IP地址的问题，以及在一个固定的IP和端口上对外暴露多个pod。

当一个服务被创建时，它会得到一个静态的IP，在服务的生命周期中这个IP不会发生改变。客户端应该通过固定IP地址连接到服务，而不是直接连接pod。服务会确保其中一个pod接收连接，而不关心pod当前运行在哪里。

### 2.3.4 Horizontally scaling the application

使用Kubernetes的一个主要好处是可以简单地扩展部署。

列出正在运行的ReplicationController清单：

    $ kubectl get replicationcontrollers
    NAME    DESIRED   CURRENT   READY   AGE
    kubia   3         3         3       69m

**调整期望的副本数**

可以运行如下命令改变期望ReplicationController创建的副本数：

    kubectl scale rc kubia --replicas=<number>

使用Kubernetes给应用扩容变得非常简单。一旦应用在生产中运行并且需要扩容，可以使用一个命令添加额外的实例，而不必手动安装和运行其他副本。

应用本身需要支持水平伸缩。Kubernetes并不会让你的应用变得可扩展，它只是让应用的扩容和缩容变得简单。

### 2.3.5 Examining what nodes your app is running on

列出pod的IP、节点等附加信息：

    kubectl get pods -o wide

### 2.3.6 Kubernetes dashboard

使用GKE时，可以通过 `kubernetes cluster-info` 命令找到dashboard的URL：

    kubectl cluster-info | grep dashboard

使用Minikube的Kubernetes集群的dashboard可以运行以下命令打开：

    minikube dashboard

# 3 Pods: running containers in Kubernetes

## 3.1 Pods

### 3.1.1 Why we need pods

**为何多个容器比单个容器中包含多个进程要好**

一个由多个进程组成的应用程序，无论是通过ipc（进程间通信）还是本地存储文件进行通信，都要求它们运行在同一台机器上。

容器被设计为每个容器只运行一个进程（除非进程本身产生子进程）。如果在单个容器中运行多个不相关的进程，那么保持所有进程运行、管理它们的日志等将会是我们的责任。

我们需要让每个进程运行于自己的容器中，这是Docker和Kubernetes期望使用的方式。

### 3.1.2 Understanding pods

由于不能将多个进程聚集在一个单独的容器中，我们需要另一种更高级的结构来将容器绑定在一起，并将它们作为一个单元进行管理，这就是pod背后的根本原理。

**同一个pod中容器之间的部分隔离**

Kubernetes通过配置Docker来让一个pod内的所有容器共享相同的Linux命名空间，而不是每个容器都有自己的一组命名空间。

由于一个pod中的所有容器都在相同的network和UTS命名空间下运行，所以它们都共享相同的主机名和网络接口。同时，这些容器也都在相同的IPC命名空间下运行，因此能够通过IPC进行通信。在最新的Kubernetes和Docker版本中，它们也能共享相同的PID命名空间，但该特性默认是未激活的。

由于大多数容器的文件系统来自容器镜像，因此默认情况下，每个容器的文件系统与其他容器完全隔离。但我们可以使用名为Volume的Kubernetes来共享文件目录。

**容器如何共享相同的IP和端口空间**

由于一个pod中的容器运行于相同的Network命名空间中，因此它们共享相同的IP和端口空间。在同一个pod中的容器运行得多个进程需要注意不能绑定相同的端口号，否则会导致端口冲突。此外，一个pod中的所有容器也都具有相同的loopback网络接口，因此容器可以通过localhost与同一pod中的其他容器进行通信。

**The flat intro-pod network（平坦Pod间网络）**

Kubernetes集群中的所有pod都在同一个共享网络地址空间中（如下图所示），这意味着每个pod都可以通过其他pod的IP地址来实现互相访问。换句话说，这也表示它们之间没有NAT（网络地址转换）网关。当两个pod彼此间发送网络数据包时，它们都会将对象的实际IP地址看作数据包中的源IP。

![image](https://github.com/janwee-sha/reading-notes/blob/main/SystemDesign/images/Kubernetes.in.Action.Figure.3.2.png)

Pod间的通信非常简单。不论两个Pod处于单一还是不同的工作节点上，且不论实际节点间的网络拓扑结构如何，这些Pod内的容器都能像在无NAT的平坦网络中一样相互通信，就像局域网（LAN）上的计算机一样。

### 3.1.3 Organizing containers across pods properly

1.  **将多层应用分散到多个pod中**。提高基础架构的利用率。
2.  **基于扩容考虑而分割到多个pod中。**

## 3.2 Creating pods from YAML or JSON descriptors

---

- `kubectl get pod <pod_name> -o [yaml|json]`: examining a YAML or JSON descriptor of an existing pod.

YAML描述的主要组成部分：

- Kubernetes API版本
- YAML描述的资源类型
- metadata, 包括名称、命名空间、标签等
- spec，包含pod内容的实际说明，如pod的容器、卷及其他数据
- status，包含运行中的pod的当前信息，如pod所处的条件、每个容器的描述和状态，以及内部的IP及其他信息

---

- `kubectl explain <api_object>`：查看Kubernetes API对象的解释性信息

如：

```
$ kubectl explain pods
```

```
$ kubectl explain pod.spec
```

---

- `kubectl create -f <yaml_or_json_file>`：从YAML或JSON文件中创建任何资源（不只是Pod）。

---

- `kubectl logs <pod_name>`: 获取Pod的日志

---

- `kubectl port-forward <pod_name> <host_port>:<forward_port>`: 将本地网络端口转发到pod中的端口。

## 3.3 使用标签组织Pod

**介绍标签**

标签不仅可以组织pod，还可以组织所有其他的Kubernetes资源。

初始状态下每个pod都标有两个标签：

- app。指定pod术语那个应用、组件或微服务。
- rel。显示pod中运行的应用版本是stable、beta还是canary。

**创建pod时指定标签**

如：
```
...
metadata:
  labels:
    app: redis
    env: dev
...
```

- `kubectl get po --show-labels`：列出Pod时列出标签。
- `kubectl get po -L <label-key1>,<label-key2>,<label-key3>`: 列出Pod并附加展现标签。

**修改Pod的标签**

- `kubectl label <object_type> <object_name> <label-key>=<label-value> [--overwrite]`：修改已有标签键时需要加上`--overwrite`选项。

## 3.4 通过标签选择器列出Pod子集

- `kubectl get po -l <label-key>=<label-value>`：列出匹配指定标签键值的Pod
- `kubectl get po -l <label-key>!=<label-value>`：列出指定标签键的值不等于给定值的Pod
- `kubectl get po -l <label-key> in <label-value1>,<label-value2>`：列出指定标签键的值在给定值集合中的Pod
- `kubectl get po -l <label-key> notin <label-value1>,<label-value2>`：列出指定标签键的值不在给定值集合中的Pod
- `kubectl get po -l <label-key>`：列出匹配存在标签键的Pod
- `kubectl get po -l '!<label-key>'`：列出匹配不存在标签键的Pod

## 3.5 使用标签和选择器来约束pod调度

节点选择器要求Kubernetes只将pod部署到包含给定标签的节点上：

```
spec:
  nodeSelector:
    gpu: "true"
```

也可以使用每个节点的唯一标签 `kubernetes.io/hostname` 将pod调度到某个确定的节点。

## 3.7 使用命名空间对资源进行分组

- `kubectl get ns`, 列出集群中的所有命名空间。
- `kubectl get po --namespace <namespace>`：列出指定命名空间的Pod。

YAML定义命名空间：

```
apiVersion: v1
kind: NameSpace
metadata:
  name: custom-namespace
```

- `kubectl create -f <some-yaml-file> -n <a-namespace>`, 在指定命名空间中创建对象。

命名空间之间实际上并不提供对正在运行得对象的任何隔离，如网络隔离。

## 3.8 停止和移除pod

- `kubectl delete po <pod_name>`：按名称删除Pod
- `kubectl delete po -l <label-key>=<label-value>`：按标签删除Pod
- `kubectl delete ns <namespace>`：删除命名空间来删除pod（及命名空间中的其他资源）
- `kubectl delete all --all`：删除命名空间中的几乎所有资源，第一个`all`指定所有资源类型，第二个`all`指定资源类型的所有实例。

# 4. 副本机制和其他资源控制器：部署托管的pod

## 4.1 保持pod的健康

### 存活探针

Kubernetes可以通过**存活探针**（liveness probe）检查容器是否正在运行。

Kubernets探测容器的3种机制：

- HTTP GET探针对容器的IP地址（需要在应用中指定HTTP端点）执行HTTP GET请求。
- TCP套接字探针尝试与容器指定端口建立TCP连接。
- Exex探针在容器内执行任意命令，并检查命令的退出状态码。

### 基于HTTP的存活探针

如，指定探测`8080:/`HTTP端点的存活探针：
```
spec:
  containers:
  - image: luksa/kubia-unhealthy
    name: kubia
    livenessProbe:
      httpGet:
        path: /
        port: 8080
```

重启容器后的pod描述：

```
...
Containers:
  kubia:
  ...
  State:          Running
      Started:      Wed, 22 Mar 2023 06:10:35 +0000
    Last State:     Terminated
      Reason:       Error
      Exit Code:    137
      Started:      Wed, 22 Mar 2023 06:08:45 +0000
      Finished:     Wed, 22 Mar 2023 06:10:34 +0000
    Ready:          True
    Restart Count:  6
...
Events:
  Type     Reason     Age                  From               Message
  ----     ------     ----                 ----               -------
  Warning  Unhealthy  108s (x16 over 10m)  kubelet            Liveness probe failed: HTTP probe failed with statuscode: 500
...
```

### 配置存活探针的附加属性

定义探针时可以自定义延迟（delay）、超时（timeout）、周期（period）等附加参数。

```
livenessProbe:
  initalDelaySeconds: <seconds>
  timeoutSeconds: <seconds>
  periodSeconds: <seconds>
```

## 4.2 ReplicationController

ReplicationController是一种Kubernetes资源，可确保它的pod始终保持运行状态。

ReplictaionController的工作时确保pod的数量始与其标签选择器匹配。

ReplictaionController的3个主要部分：

- label selector
- replica count
- pod template

### 创建ReplicationController

ReplicationController的YAML定义：redis-rc.yaml
```
apiVersion: v1
kind: ReplicationController
metadata:
  name: redis
spec:
  replicas: 3
  selector:
    app: redis
  template:
    metadata:
      labels:
        app: redis
    spec:
      containers:
      - image: redis:latest
        name: redis
        ports:
        - containerPort: 6379
```

- `kubectl get rc`：列出ReplicationController
- `kubectl edit rc <RC_NAME>`:修改模板
- `kubectl scale rc <RC_NAME> --replicas=<ReplicaNumber>`
- `kubectl delete rc <RC_NAME> [--cascaed=[true|false]]`:删除pod并指定是否删除管理的pod。

## 4.3 ReplicaSet

ReplicaSet是跟ReplicationController类似的资源，是后者的替代者。

### ReplicaSet vs. ReplicationController

- ReplicaSet的pod选择器表达能力更强
- ReplicaSet标签选择器除了支持匹配是否包含标签外，还支持匹配不包含某个标签，或包含特定标签键的pod

## 4.4 DaemonSet

DaemonSet在每个节点上只运行一个pod副本，而副本则将它们随机地分布在整个集群中。

如果节点下线，DaemonSet不会在其他地方重新创建pod。但是，当新节点被添加到集群中时，DaemonSet会立即部署一个新的pod实例。

## 10 StatefulSet：部署有状态的多副本应用

## 10.2 了解StatefulSet

### 提供稳定的网络标识

一个StatefulSet创建的每个pod都有一个从零开始的顺序索引，这个会体现在pod的名称和主机上，同样还会体现在pod对应的固定存储上。

一个Stateful通常需要对应创建一个用来记录每个pod网络标记的headless Service。通过这个Service，每个pod将拥有独立的DNS记录，这样集群里它的伙伴或者客户端可以通过主机名方便地找到它。

当一个Stateful管理的一个pod实例消失后，Stateful会保证重启一个新的pod实例替换它，且新实例拥有与之前pod完全一致的名称和主机名。

扩容一个Statefulset会使用下一个未使用的顺序索引值创建一个新的pod实例。缩容一个Statefulset会删除最高索引值的实例。

### 为每个有状态的实例提供稳定的专属存储

有状态的pod的存储必须是持久的，且与pod解耦。

像Statefulset创建pod一样，Statefulset也需要创建持久卷声明。

**持久卷的扩缩容**

扩容Statefulset时，会创建两个或更多的API对象（一个pod和与之关联的一个或多个持久卷声明）。缩容时，只会删除pod，遗留下之前创建的声明。

## 10.3 使用Statefulset

