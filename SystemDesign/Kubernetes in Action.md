# Chapter 1. Introducing Kubernetes

## 1.1 Understanding the need for a system like Kubernetes

### 1.1.3 Moving to continuous delivery: DevOps and NoOps

The pratice which makes the developer, QA, and operations teams collaborate throughout the whole process is called DevOps.

**UNDERSTANDING THE BENEFITS**

Having the developers more involved in running the application in production leads to them having a better understanding of both the users' needs and issues and the problems faced by the ops team while maintaining the app.

**LEFTING DEVELOPERS AND SYSADMINS DO WHAT THE DO BEST**

Ideally, you want the developers to deploy applicatons themselves without knowing anything about the hardware infrastructure and without dealing with the ops team. This is referred to as *NoOps*. Obviously, you still need someone to take care of the hardware infrastructure, but ideally, without having to deal with peculiarities of each application running on it.

Kubernetes enables us to achieve all of this. By abstracting away the actual hardware and exposing it as a single platform for deploying and running apps, it allows developers to configure and deploy their applications without any help from the sysadmins and allow sysadmins to focus on keeping the underlying infrastructure up and running without dealing with the actual applications.

## 1.2 Introducing container technologies

### 1.2.1 Understanding what containers are

**ISOLATING COMPONENTS WITH LINUX CONTAINER TECHNOLOGIES**

Two mechanisms make isolating processes possible. The first one, *Linux Namespaces*, make sure each process sees its own personal view of system (file, processes, network interfaces, hostname, and so on). The Second one is *Linux Control Groups (cgroups)*, which limit the amount of resources the process can consume (CPU, memory, network bandwidth, and so on).

**ISOLATING PROCESSES WITH LINUX NAMESPACE**

The following kinds of namespaces in Linux exist：

- Mount（mnt）
- Process ID（pid）
- Network（net）
- Inter-process communication（ipd）
- UTS
- User ID（user）

Each namespace kind is used to isolate a certain group of resources. For example, the UTS namespace determines what hostname and domain name the process running inside that namespace sees.

Likewise, what Network namespace a process belongs to determines which network interfaces the application running inside the process sees. Each container uses its own Network space, and therefore each container sees its own set of network interfaces.

**LIMIT RESOURCES AVAILABLE TO A PROCESS**

The other half of container isolation deals with limiting the amount of system resources a container can consume. This is achieved with cgroups, a Linux kernel feature that limits the resource usage of a process (or a group of processes). A process can't use more than the configured amount of CPU, memory, network bandwidth, and so on. This way, processes cannot hog resources reserved for other processes, which is similar to when each process runs on a separate machine.

## 1.3 Introducing Kunernetes

### 1.3.2 Looking at Kubernetes from the top of a mountain

**UNDERSTANING THE CORE OF WHAT KUBERNETES DOES**

A Kubernetes sysrem may be composed of a master node and any number of worker nodes. When the developer submits a list of apps to the master, Kubernetes deploys them to the cluster of worker nodes. What node a component lands on doesn't matter——neither to the developer nor to the system administrator.

### 1.3.3 Understanding the architecture of a Kubernetes cluster

At the hardware level, a Kubernetes cluster is composed of many nodes, which can be split into two types:

- The *master* node, which hosts the *Kubernetes Control Plane* that controls and manages the whole Kubernetes system
- The **worker** nodes that run the actual applications you deploy

**THE CONTROL PLANE**

The Control Plane is what controls the cluster and makes it function. It consists if multiple components that can run on a single master node or be split across multiple nodes and replicated to ensure high availability. These components are:

- The *Kubernetes API Server*, which you and the other Control Plane components communicate with
- The *Scheduler*, which schedules your apps (assigns a worker node to each deployable component of your application)
- The *Control Manager*, which perfoms cluster-level functions, such as replicating components, keeping track of worker nodes, handling node failures, and so on
- *etcd*, a reliable distributed data store that persistently stores the cluster configuration.

**THE NODES**

The worker nodes are the machines that run your containerized applications. It's done by the following components:

- Docker, rkt, or another container runtime, which runs your containers
- The Kubelet, whic talks to the API server and manages containers on its node
- The Kubernetes Service Proxy (kube-proxy), which load-blances network traffic between application components

### 1.3.4 Running an application in Kubernetes

为了在Kubernetes中运行应用，首先需要将应用打包为一个或多个容器镜像，再将那些镜像推送到镜像仓库，然后将应用的描述发布到Kubernetes API服务器。

该描述包括注入容器镜像或者包含应用程序组件的容器镜像、这些镜像如何相互关联，一级那些组件需要同时运行在同一个节点上和哪些组件不需要同时运行等信息。此外，该描述还包括哪些组件为内部或外部客户提供服务且应该通过单个IP地址暴露，并使其他组件可以发现。

**UNSERSTANDING HOW THE DESCRIPTION RESULT IN A RUNNING CONTAINER**

When the API server processes your app's description, the Scheduler schedules the specified groups of containers onto the available worker nodes based on computational resources required bu each group and the allocated resources on each nodee at that moment. The Kubelet on those nodes then instructs the Container Runtime to pull the required container images and run the containers.

**保持容器运行**

一旦应用程序运行起来，Kubernetes就会不断地确认应用程序的部署状态始终与你提供的描述相匹配。

**扩展副本数量**

当应用程序运行时，可以决定要增加或减少复本量，而Kubernetes将分别增加附加的或停止多余的副本。甚至可以把决定副本数量的工作交给Kubernetes，它可以根据实时指标（CPU负载、内存消耗、每秒查询等）自动调整副本数。

**命中移动目标**

为了让客户能够轻松地找到提供特定服务的容器，可以告诉Kubernetes哪些容器提供相同的服务，而Kubernetes将通过一个静态IP地址暴露所有容器，并将该地址暴露给集群中运行的所有应用程序。这是通过环境变量完成的，但是客户端也可以通过良好的DNS查找服务IP。kube-proxy将确保到服务的连接是可跨容器实现负载均衡。服务的IP地址保持不变，因此客户端始终可以连接到它的容器，即使它们在集群中移动。

### 1.3.5 Understanding the benefits of using Kubernetes

- **SIMPLIFYING APPLICATION DEPLOYMENT**
- **更好地利用硬件**
- **健康检查和自修复**
- **自动扩容**
- **简化应用部署**

# Chapter 2. First step with Kubernetes and Docker

## 2.1 Creating, running, and sharing a container image

## 2.2 Setting up a Kubernetes cluster

After having your app packaged inside a container image and made available through Docker Hub,you can deploy it in a Kubernetes cluster instead of running it in Docker directly.

A long list of methods exists for installing a Kubernetes cluster. These methods are described in the documentation at [https://kubernetes.io](https://kubernetes.io/). In this chapter, we'll cover two simple options for getting your hands on running Kubernetes cluster.

## 2.3 Running your first app on Kubernetes

Usually, you'd prepare a JSON or YAML manifest, containing a description of all the components you want to deploy. But we'll use a simple one-line command to get something running.

### 2.3.1 Deploying your app

```
kubectl run eureka --images=janwee/eureka-server --port=7100
```

